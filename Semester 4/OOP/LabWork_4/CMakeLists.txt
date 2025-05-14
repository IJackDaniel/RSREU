cmake_minimum_required(VERSION 3.10.0)
project(LabWork4 VERSION 0.1.0 LANGUAGES C CXX)

add_executable(LabWork4 main.cpp RandomGenerator.cpp Employee.cpp Engineer.cpp Part.cpp Staff.cpp Storekeeper.cpp Worker.cpp WorkshopManager.cpp)

include(CTest)
enable_testing()
