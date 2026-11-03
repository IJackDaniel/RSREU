#include <CL/cl.h>

#include <algorithm>
#include <chrono>
#include <fstream>
#include <iomanip>
#include <iostream>
#include <random>
#include <sstream>
#include <string>
#include <vector>

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
    cl_int status;
    cl_uint platformCount = 0;
    status = clGetPlatformIDs(0, nullptr, &platformCount);

    if (status != CL_SUCCESS || platformCount == 0)
    {
        std::cerr << "No OpenCL platforms found.\n";
        return 1;
    }

    std::vector<cl_platform_id> platforms(platformCount);
    clGetPlatformIDs(platformCount, platforms.data(), nullptr);
    cl_platform_id platform = platforms[0];

    cl_device_id device = nullptr;

    // Сначала пробуем использовать GPU
    status = clGetDeviceIDs(
        platform, CL_DEVICE_TYPE_GPU, 1, &device, nullptr
    );

    // Если GPU недоступен, пробуем CPU
    if (status != CL_SUCCESS)
    {
        status = clGetDeviceIDs(
            platform, CL_DEVICE_TYPE_CPU, 1, &device, nullptr
        );
    }

    if (status != CL_SUCCESS)
    {
        std::cerr << "No suitable OpenCL device found.\n";
        return 1;
    }

    // Выводим название выбранного устройства
    char deviceName[256];

    clGetDeviceInfo(
        device,
        CL_DEVICE_NAME,
        sizeof(deviceName),
        deviceName,
        nullptr
    );

    std::cout << "OpenCL device: " << deviceName << "\n\n";

    cl_context context = clCreateContext(nullptr, 1, &device, nullptr, nullptr, &status);
    if (status != CL_SUCCESS)
    {
        std::cerr << "Failed to create context.\n";
        return 1;
    }

    cl_command_queue queue = clCreateCommandQueue(
        context, device, CL_QUEUE_PROFILING_ENABLE, &status);
    if (status != CL_SUCCESS)
    {
        std::cerr << "Failed to create command queue.\n";
        clReleaseContext(context);
        return 1;
    }

    std::string source;
    try
    {
        source = loadKernelSource("BitonicSort_Kernels.cl");
    }
    catch (const std::exception& ex)
    {
        std::cerr << ex.what() << '\n';
        clReleaseCommandQueue(queue);
        clReleaseContext(context);
        return 1;
    }

    const char* sourcePtr = source.c_str();
    size_t sourceLength = source.size();
    cl_program program = clCreateProgramWithSource(
        context, 1, &sourcePtr, &sourceLength, &status);
    status = clBuildProgram(program, 1, &device, nullptr, nullptr, nullptr);
    if (status != CL_SUCCESS)
    {
        size_t logSize = 0;
        clGetProgramBuildInfo(program, device, CL_PROGRAM_BUILD_LOG,
                              0, nullptr, &logSize);
        std::vector<char> log(logSize + 1, '\0');
        clGetProgramBuildInfo(program, device, CL_PROGRAM_BUILD_LOG,
                              logSize, log.data(), nullptr);
        std::cerr << "OpenCL build error:\n" << log.data() << '\n';
        clReleaseProgram(program);
        clReleaseCommandQueue(queue);
        clReleaseContext(context);
        return 1;
    }

    cl_kernel kernel = clCreateKernel(program, "bitonicSort", &status);
    if (status != CL_SUCCESS)
    {
        std::cerr << "Failed to create kernel.\n";
        clReleaseProgram(program);
        clReleaseCommandQueue(queue);
        clReleaseContext(context);
        return 1;
    }
    
    // Каждый эксперимент повторяем 20 раз
    const int repetitions = 20;
    // Генератор псевдослучайных чисел
    std::mt19937 generator(12345);
    std::uniform_int_distribution<cl_uint> distribution(0, 100000);

    std::cout << std::fixed << std::setprecision(6);
    std::cout << "N | CPU ms | Kernel ms | Total OpenCL ms | Check\n";

    for (size_t n : {64, 256, 1024, 2048, 4096})
    {
        std::vector<cl_uint> original(n);
        for (cl_uint& value : original)
        {
            value = distribution(generator);
        }

        std::vector<cl_uint> reference = original;
        double cpuTime = 0.0;
        for (int repeat = 0; repeat < repetitions; ++repeat)
        {
            reference = original;
            auto start = std::chrono::steady_clock::now();
            std::sort(reference.begin(), reference.end());
            auto end = std::chrono::steady_clock::now();
            cpuTime += std::chrono::duration<double, std::milli>(end - start).count();
        }
        cpuTime /= repetitions;

        cl_mem dataBuffer = clCreateBuffer(
            context, CL_MEM_READ_WRITE, n * sizeof(cl_uint), nullptr, &status);
        if (status != CL_SUCCESS)
        {
            std::cerr << "Failed to create buffer.\n";
            break;
        }

        int levels = 0;
        for (size_t size = n; size > 1; size >>= 1)
        {
            ++levels;
        }

        const cl_uint direction = 1; // По возрастанию
        const size_t globalSize = n / 2;
        double kernelTime = 0.0;
        double totalTime = 0.0;
        bool correct = true;

        for (int repeat = 0; repeat < repetitions; ++repeat)
        {
            std::vector<cl_uint> result(n);
            auto totalStart = std::chrono::steady_clock::now();

            status = clEnqueueWriteBuffer(queue, dataBuffer, CL_TRUE, 0,
                                          n * sizeof(cl_uint), original.data(),
                                          0, nullptr, nullptr);
            if (status != CL_SUCCESS)
            {
                correct = false;
                std::cerr << "Buffer write error: " << status << '\n';
                break;
            }

            for (cl_uint stage = 0; stage < static_cast<cl_uint>(levels); ++stage)
            {
                for (cl_uint pass = 0; pass <= stage; ++pass)
                {
                    status = clSetKernelArg(kernel, 0, sizeof(cl_mem), &dataBuffer);
                    status |= clSetKernelArg(kernel, 1, sizeof(cl_uint), &stage);
                    status |= clSetKernelArg(kernel, 2, sizeof(cl_uint), &pass);
                    status |= clSetKernelArg(kernel, 3, sizeof(cl_uint), &direction);
                    if (status != CL_SUCCESS)
                    {
                        std::cerr << "Kernel argument error: " << status << '\n';
                        correct = false;
                        break;
                    }

                    cl_event event;
                    status = clEnqueueNDRangeKernel(queue, kernel, 1, nullptr,
                                                    &globalSize, nullptr, 0, nullptr, &event);
                    if (status != CL_SUCCESS)
                    {
                        std::cerr << "Kernel launch error: " << status << '\n';
                        correct = false;
                        break;
                    }

                    clWaitForEvents(1, &event);
                    cl_ulong startNs = 0;
                    cl_ulong endNs = 0;
                    clGetEventProfilingInfo(event, CL_PROFILING_COMMAND_START,
                                            sizeof(cl_ulong), &startNs, nullptr);
                    clGetEventProfilingInfo(event, CL_PROFILING_COMMAND_END,
                                            sizeof(cl_ulong), &endNs, nullptr);
                    kernelTime += static_cast<double>(endNs - startNs) / 1000000.0;
                    clReleaseEvent(event);
                }
                if (!correct) break;
            }
            if (!correct) break;

            status = clEnqueueReadBuffer(queue, dataBuffer, CL_TRUE, 0,
                                         n * sizeof(cl_uint), result.data(),
                                         0, nullptr, nullptr);
            if (status != CL_SUCCESS)
            {
                std::cerr << "Buffer read error: " << status << '\n';
                correct = false;
                break;
            }

            auto totalEnd = std::chrono::steady_clock::now();
            totalTime += std::chrono::duration<double, std::milli>(
                totalEnd - totalStart).count();
            correct = std::is_sorted(result.begin(), result.end()) && result == reference;
            if (!correct) break;
        }

        if (correct)
        {
            kernelTime /= repetitions;
            totalTime /= repetitions;
        }

        std::cout << n << " | " << cpuTime << " | " << kernelTime
                  << " | " << totalTime << " | " << (correct ? "OK" : "ERROR") << '\n';
        clReleaseMemObject(dataBuffer);
        if (!correct) break;
    }

    clReleaseKernel(kernel);
    clReleaseProgram(program);
    clReleaseCommandQueue(queue);
    clReleaseContext(context);
    return 0;
}
