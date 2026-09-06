#include "HelloWorld.h"
#include "OpenCL.h"

#include <iostream>
#include <cstring>
#include <cstdlib>
#include <string>

void HelloWorld(const OpenCLContext& opencl) 
{
    cl_int status;

    const char *filename = "HelloWorld_Kernel.cl";
    std::string sourceStr;

    status = convertToString(filename, sourceStr);

    if (status != CL_SUCCESS) {
        std::cout << "File reading error" << std::endl;
        return;
    }

    const char *source = sourceStr.c_str();
    size_t sourceSize[] = {strlen(source)};

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

    // std::cout << sourceStr << std::endl;

    // Инифиализация входного и выходного буферов
    const char* input = "GdkknVnqkc";
    size_t strlength = strlen(input);
    char *output = (char*) malloc(strlength + 1);

    /* Создаём аналогичные буферы 
    в памяти GPU и переносим в него 
    данные с Host машины */
    cl_mem inputBuffer = clCreateBuffer(
        opencl.context,
        CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
        (strlength + 1) * sizeof(char),
        (void*) input,
        NULL
    );

    cl_mem outputBuffer = clCreateBuffer(
        opencl.context,
        CL_MEM_WRITE_ONLY,
        (strlength + 1) * sizeof(char),
        NULL,
        NULL
    );

    cl_kernel kernel = clCreateKernel(
        program,
        "helloworld",
        NULL
    );

    status = clSetKernelArg(
        kernel,
        0,
        sizeof(cl_mem),
        (void*)&inputBuffer
    );

    status = clSetKernelArg(
        kernel,
        1,
        sizeof(cl_mem),
        (void*)&outputBuffer
    );

    size_t global_work_size[1] = {strlength};

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
        strlength * sizeof(char),
        output,
        0,
        NULL,
        NULL
    );

    output[strlength] = '\0';

    std::cout << output << std::endl;

    status = clReleaseKernel(kernel);
    status = clReleaseProgram(program); 
    status = clReleaseMemObject(inputBuffer);
    status = clReleaseMemObject(outputBuffer);

    if (output != NULL)
    {
        free(output);
        output = NULL;
    }
}