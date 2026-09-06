#pragma once

#include "OpenCLContext.h"
#include <string>

void printDeviceInfo(cl_device_id device);

int convertToString(const char *filename, std::string& s);

OpenCLContext Init(cl_device_type deviceType);

void Clean(OpenCLContext& opencl);