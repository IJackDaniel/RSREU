#pragma once

#include <CL/cl.h>

struct OpenCLContext
{
    cl_platform_id platform = nullptr;
    cl_device_id device = nullptr;
    cl_context context = nullptr;
    cl_command_queue commandQueue = nullptr;
};