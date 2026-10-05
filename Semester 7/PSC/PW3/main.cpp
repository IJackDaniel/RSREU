#include <CL/cl.h>

#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <iomanip>
#include <cmath>
#include <chrono>
#include <stdexcept>

std::string loadKernelSource(const std::string& filename)
{
    std::ifstream file(filename);

    if (!file.is_open())
    {
        throw std::runtime_error("Failed to open kernel file: " + filename);
    }

    std::stringstream buffer;
    buffer << file.rdbuf();

    return buffer.str();
}

int main()
{
    const cl_ulong numSteps = 500000000ULL;
    const double referencePi = 3.14159265358979323846;
    const double step = 1.0 / static_cast<double>(numSteps);

    std::cout << std::setprecision(15);

    // Последовательное вычисление на CPU
    double cpuSum = 0.0;

    auto cpuStart = std::chrono::high_resolution_clock::now();

    for (cl_ulong i = 0; i < numSteps; ++i)
    {
        double x = (static_cast<double>(i) + 0.5) * step;
        cpuSum += 4.0 / (1.0 + x * x);
    }

    double cpuPi = cpuSum * step;

    auto cpuEnd = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> cpuElapsed = cpuEnd - cpuStart;

    std::cout << "CPU result: " << cpuPi << '\n';
    std::cout << "CPU error:  " << std::fabs(cpuPi - referencePi) << '\n';
    std::cout << "CPU time:   " << cpuElapsed.count() << " s\n\n";

    cl_int status;

    // Получаем первую доступную OpenCL-платформу
    cl_uint platformCount = 0;
    status = clGetPlatformIDs(0, nullptr, &platformCount);

    if (status != CL_SUCCESS || platformCount == 0)
    {
        std::cerr << "No OpenCL platforms found.\n";
        return 1;
    }

    std::vector<cl_platform_id> platforms(platformCount);

    status = clGetPlatformIDs(
        platformCount,
        platforms.data(),
        nullptr
    );

    if (status != CL_SUCCESS)
    {
        std::cerr << "Failed to get OpenCL platforms.\n";
        return 1;
    }

    cl_platform_id platform = platforms[0];

    // Получаем первое доступное устройство
    cl_uint deviceCount = 0;

    status = clGetDeviceIDs(
        platform,
        CL_DEVICE_TYPE_ALL,
        0,
        nullptr,
        &deviceCount
    );

    if (status != CL_SUCCESS || deviceCount == 0)
    {
        std::cerr << "No OpenCL devices found.\n";
        return 1;
    }

    std::vector<cl_device_id> devices(deviceCount);

    status = clGetDeviceIDs(
        platform,
        CL_DEVICE_TYPE_ALL,
        deviceCount,
        devices.data(),
        nullptr
    );

    if (status != CL_SUCCESS)
    {
        std::cerr << "Failed to get OpenCL devices.\n";
        return 1;
    }

    cl_device_id device = devices[0];

    cl_context context = clCreateContext(
        nullptr,
        1,
        &device,
        nullptr,
        nullptr,
        &status
    );

    if (status != CL_SUCCESS)
    {
        std::cerr << "Failed to create OpenCL context.\n";
        return 1;
    }

    // Включаем profiling, чтобы измерить время работы kernel
    cl_command_queue queue = clCreateCommandQueue(
        context,
        device,
        CL_QUEUE_PROFILING_ENABLE,
        &status
    );

    if (status != CL_SUCCESS)
    {
        std::cerr << "Failed to create command queue.\n";
        return 1;
    }

    std::string source;

    try
    {
        source = loadKernelSource("pi.cl");
    }
    catch (const std::exception& ex)
    {
        std::cerr << ex.what() << '\n';
        return 1;
    }

    const char* sourcePtr = source.c_str();
    size_t sourceLength = source.size();

    cl_program program = clCreateProgramWithSource(
        context,
        1,
        &sourcePtr,
        &sourceLength,
        &status
    );

    if (status != CL_SUCCESS)
    {
        std::cerr << "Failed to create OpenCL program.\n";
        return 1;
    }

    status = clBuildProgram(
        program,
        1,
        &device,
        nullptr,
        nullptr,
        nullptr
    );

    if (status != CL_SUCCESS)
    {
        size_t logSize = 0;

        clGetProgramBuildInfo(
            program,
            device,
            CL_PROGRAM_BUILD_LOG,
            0,
            nullptr,
            &logSize
        );

        std::vector<char> buildLog(logSize);

        clGetProgramBuildInfo(
            program,
            device,
            CL_PROGRAM_BUILD_LOG,
            logSize,
            buildLog.data(),
            nullptr
        );

        std::cerr << "OpenCL build error:\n";
        std::cerr << buildLog.data() << '\n';

        return 1;
    }

    cl_kernel kernel = clCreateKernel(
        program,
        "calculatePi",
        &status
    );

    if (status != CL_SUCCESS)
    {
        std::cerr << "Failed to create kernel.\n";
        return 1;
    }

    const size_t localSize = 256;
    const size_t groupCount = 256;
    const size_t globalSize = localSize * groupCount;

    // По одному итоговому значению на work-group
    cl_mem groupSumsBuffer = clCreateBuffer(
        context,
        CL_MEM_WRITE_ONLY,
        sizeof(double) * groupCount,
        nullptr,
        &status
    );

    if (status != CL_SUCCESS)
    {
        std::cerr << "Failed to create output buffer.\n";
        return 1;
    }

    status  = clSetKernelArg(
        kernel,
        0,
        sizeof(cl_ulong),
        &numSteps
    );

    status |= clSetKernelArg(
        kernel,
        1,
        sizeof(double),
        &step
    );

    status |= clSetKernelArg(
        kernel,
        2,
        sizeof(cl_mem),
        &groupSumsBuffer
    );

    // localSums выделяется отдельно для каждой work-group
    status |= clSetKernelArg(
        kernel,
        3,
        sizeof(double) * localSize,
        nullptr
    );

    if (status != CL_SUCCESS)
    {
        std::cerr << "Failed to set kernel arguments.\n";
        return 1;
    }

    cl_event kernelEvent;

    status = clEnqueueNDRangeKernel(
        queue,
        kernel,
        1,
        nullptr,
        &globalSize,
        &localSize,
        0,
        nullptr,
        &kernelEvent
    );

    if (status != CL_SUCCESS)
    {
        std::cerr << "Failed to execute kernel. Error code: "
                  << status << '\n';
        return 1;
    }

    clFinish(queue);

    cl_ulong startTime;
    cl_ulong endTime;

    clGetEventProfilingInfo(
        kernelEvent,
        CL_PROFILING_COMMAND_START,
        sizeof(cl_ulong),
        &startTime,
        nullptr
    );

    clGetEventProfilingInfo(
        kernelEvent,
        CL_PROFILING_COMMAND_END,
        sizeof(cl_ulong),
        &endTime,
        nullptr
    );

    double openclTime =
        static_cast<double>(endTime - startTime) / 1e9;

    std::vector<double> groupSums(groupCount);

    status = clEnqueueReadBuffer(
        queue,
        groupSumsBuffer,
        CL_TRUE,
        0,
        sizeof(double) * groupCount,
        groupSums.data(),
        0,
        nullptr,
        nullptr
    );

    if (status != CL_SUCCESS)
    {
        std::cerr << "Failed to read results.\n";
        return 1;
    }

    // Складываем частичные суммы всех work-group
    double openclSum = 0.0;

    for (double value : groupSums)
    {
        openclSum += value;
    }

    double openclPi = openclSum * step;

    std::cout << "OpenCL result: " << openclPi << '\n';
    std::cout << "OpenCL error:  "
              << std::fabs(openclPi - referencePi)
              << '\n';
    std::cout << "OpenCL time:   "
              << openclTime
              << " s\n";

    clReleaseEvent(kernelEvent);
    clReleaseMemObject(groupSumsBuffer);
    clReleaseKernel(kernel);
    clReleaseProgram(program);
    clReleaseCommandQueue(queue);
    clReleaseContext(context);

    return 0;
}