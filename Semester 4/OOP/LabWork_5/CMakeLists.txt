cmake_minimum_required(VERSION 3.10.0)
project(LabWork_5 VERSION 0.1.0 LANGUAGES C CXX)

add_executable(LabWork_5 
main.cpp
DynamicArray.h
Vec2.h
)

include(CTest)
enable_testing()

