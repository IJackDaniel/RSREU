#include "OpenCL.h"
#include <iostream>
#include "HelloWorld.h"
#include "CharShift.h"
#include "FloatMultiply.h"

int main()
{
    std::cout << "Initialization and task 5 (device info)" << std::endl;
    OpenCLContext opencl = Init(CL_DEVICE_TYPE_GPU);

    if (opencl.context == nullptr || opencl.commandQueue == nullptr)
    {
        std::cout << "OpenCL initialization failed" << std::endl;
        return 1;
    }

    std::cout << "\nTask 1. Shift chars to K positions" << std::endl;
    CharShift(
        opencl,
        "abcde",
        2
    );

    std::cout << "\nTask 2. Repeat notebook" << std::endl;
    HelloWorld(opencl);

    std::cout << "\nTask 4. Multiply two float arrays" << std::endl;
    FloatMultiply(opencl);

    Clean(opencl);

    return 0;
}