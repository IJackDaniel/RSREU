#include "FloatMultiply.h"
#include "OpenCL.h"

#include <iostream>
#include <cstdlib>
#include <string>

void FloatMultiply(const OpenCLContext& opencl)
{
    cl_int status;

    const char *filename = "FloatMultiply_Kernel.cl";
    std::string sourceStr;

    status = convertToString(filename, sourceStr);

    if (status != CL_SUCCESS) {
        std::cout << "File reading error" << std::endl;
        return;
    }

    const char *source = sourceStr.c_str();
    size_t sourceSize[] = {sourceStr.size()};

    cl_program program = clCreateProgramWithSource(
        opencl.context,
        1,
        &source,
        sourceSize,
        NULL
    );

    status = clBuildProgram(
        program,
        1,
        &opencl.device,
        NULL,
        NULL,
        NULL
    );

    if (status != CL_SUCCESS) {
        std::cout << "OpenCL error: " << status << std::endl;
    }

    const int size = 10;

    float A[size] = {
        1.5f, 2.3f, 3.0f, 4.0f, 5.0f,
        6.0f, 7.0f, 8.0f, 9.0f, 10.0f
    };

    float B[size] = {
        10.0f, 9.0f, 8.0f, 7.0f, 6.0f,
        5.0f, 4.0f, 3.0f, 2.0f, 1.0f
    };

    float *output = (float*) malloc(size * sizeof(float));

    cl_mem bufferA = clCreateBuffer(
        opencl.context,
        CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
        size * sizeof(float),
        A,
        NULL
    );

    cl_mem bufferB = clCreateBuffer(
        opencl.context,
        CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
        size * sizeof(float),
        B,
        NULL
    );

    cl_mem outputBuffer = clCreateBuffer(
        opencl.context,
        CL_MEM_WRITE_ONLY,
        size * sizeof(float),
        NULL,
        NULL
    );

    cl_kernel kernel = clCreateKernel(
        program,
        "floatMultiply",
        NULL
    );

    status = clSetKernelArg(
        kernel,
        0,
        sizeof(cl_mem),
        (void*)&bufferA
    );

    status = clSetKernelArg(
        kernel,
        1,
        sizeof(cl_mem),
        (void*)&bufferB
    );

    status = clSetKernelArg(
        kernel,
        2,
        sizeof(cl_mem),
        (void*)&outputBuffer
    );

    size_t global_work_size[1] = {size};

    status = clEnqueueNDRangeKernel(
        opencl.commandQueue,
        kernel,
        1,
        NULL,
        global_work_size,
        NULL,
        0,
        NULL,
        NULL
    );

    status = clEnqueueReadBuffer(
        opencl.commandQueue,
        outputBuffer,
        CL_TRUE,
        0,
        size * sizeof(float),
        output,
        0,
        NULL,
        NULL
    );

    std::cout << "A: ";
    for (int i = 0; i < size; i++)
    {
        std::cout << A[i] << " ";
    }
    std::cout << std::endl;

    std::cout << "B: ";
    for (int i = 0; i < size; i++)
    {
        std::cout << B[i] << " ";
    }
    std::cout << std::endl;

    std::cout << "Result: ";
    for (int i = 0; i < size; i++)
    {
        std::cout << output[i] << " ";
    }
    std::cout << std::endl;

    status = clReleaseKernel(kernel);
    status = clReleaseProgram(program);
    status = clReleaseMemObject(bufferA);
    status = clReleaseMemObject(bufferB);
    status = clReleaseMemObject(outputBuffer);

    if (output != NULL)
    {
        free(output);
        output = NULL;
    }
}