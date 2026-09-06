#include "OpenCL.h"

#include <iostream>
#include <cstdlib>
#include <fstream>

void printDeviceInfo(cl_device_id device) 
{
    cl_int status;

    char openclVersion[1024];
    status = clGetDeviceInfo(
        device, 
        CL_DEVICE_OPENCL_C_VERSION, 
        sizeof(openclVersion),
        openclVersion,
        nullptr
    );
    if (status != CL_SUCCESS) {
        std::cout << "OpenCL error: " << status << std::endl;
    }
    std::cout << "OpenCL version: " << openclVersion << std::endl;

    char deviceName[1024];
    status = clGetDeviceInfo(
        device, 
        CL_DEVICE_NAME, 
        sizeof(deviceName),
        deviceName,
        nullptr
    );
    if (status != CL_SUCCESS) {
        std::cout << "OpenCL error: " << status << std::endl;
    }
    std::cout << "Device name: " << deviceName << std::endl;

    cl_device_type deviceType;
    status = clGetDeviceInfo(
        device, 
        CL_DEVICE_TYPE, 
        sizeof(deviceType),
        &deviceType,
        nullptr
    );
    if (status != CL_SUCCESS) {
        std::cout << "OpenCL error: " << status << std::endl;
    }
    std::cout << "Device type: ";
    if (deviceType == CL_DEVICE_TYPE_CPU) 
    {
        std::cout << "CPU" << std::endl;
    } else if (deviceType == CL_DEVICE_TYPE_GPU) 
    {
        std::cout << "GPU" << std::endl;
    } else if (deviceType == CL_DEVICE_TYPE_ACCELERATOR) 
    {
        std::cout << "Accelerator" << std::endl;
    } else if (deviceType == CL_DEVICE_TYPE_CUSTOM) 
    {
        std::cout << "Custom" << std::endl;
    } 

    cl_uint deviceMaxComputeUnits;
    status = clGetDeviceInfo(
        device, 
        CL_DEVICE_MAX_COMPUTE_UNITS, 
        sizeof(deviceMaxComputeUnits),
        &deviceMaxComputeUnits,
        nullptr
    );
    if (status != CL_SUCCESS) {
        std::cout << "OpenCL error: " << status << std::endl;
    }
    std::cout << "Device max compute units: " << deviceMaxComputeUnits << std::endl;
}

int convertToString(const char *filename, std::string& s)
{
    size_t size;
    char* str;
    std::fstream f(filename, (std::fstream::in | std::fstream::binary));
    if(f.is_open())
    {
        size_t fileSize;
        f.seekg(0, std::fstream::end);
        size = fileSize = (size_t)f.tellg();
        f.seekg(0, std::fstream::beg);
        str = new char[size+1];
        if(!str)
        {
            f.close();
            return 0;
        }
        f.read(str, fileSize);
        f.close();
        str[size] = '\0';
        s = str;
        delete[] str;
        return 0;
    }
    return -1;
}

OpenCLContext Init(cl_device_type deviceType)
{
    OpenCLContext opencl;

    cl_uint numPlatforms = 0;
    cl_int status;

    status = clGetPlatformIDs(
        0, 
        NULL, 
        &numPlatforms
    );

    if (status != CL_SUCCESS) {
        std::cout << "OpenCL error: " << status << std::endl;
    }

    std::cout << "OpenCL platform found: " << numPlatforms << std::endl;

    // Выбор первой вычислительной платформы
    if (numPlatforms > 0)
    {
        cl_platform_id* platforms = (cl_platform_id*)malloc(
            numPlatforms * sizeof(cl_platform_id)
        );

        status = clGetPlatformIDs(
            numPlatforms,
            platforms,
            NULL
        );

        opencl.platform = platforms[0];

        free(platforms);
    }

    // Выбор GPU
    cl_uint numDevices = 0;

    status = clGetDeviceIDs(
        opencl.platform,
        deviceType,
        0,
        NULL,
        &numDevices
    );

    // Отладка для CPU. Если статус -1, значит Device Not Found
    // std::cout << "Get CPU devices status: " << status << std::endl;
    // std::cout << "CPU devices found: " << numDevices << std::endl;

    if (numDevices == 0)
    {
        std::cout << "OpenCL device not found" << std::endl;
        return opencl;
    }

    cl_device_id* devices = (cl_device_id*)malloc(
        numDevices * sizeof(cl_device_id)
    );

    status = clGetDeviceIDs(
        opencl.platform,
        deviceType,
        numDevices,
        devices,
        NULL
    );

    opencl.device = devices[0];

    free(devices);

    // Получение сведений об устройстве
    printDeviceInfo(opencl.device);

    opencl.context = clCreateContext(
        NULL,
        1,
        &opencl.device,
        NULL,
        NULL,
        NULL
    );

    opencl.commandQueue = clCreateCommandQueue(
        opencl.context,
        opencl.device,
        0,
        NULL
    );

    return opencl;
}

void Clean(OpenCLContext& opencl)
{
    clReleaseCommandQueue(opencl.commandQueue);
    clReleaseContext(opencl.context);
}