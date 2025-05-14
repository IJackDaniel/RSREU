#ifndef PART_HPP
#define PART_HPP

#include <iostream>
#include <string>
#include <vector>
#include <memory>
#include <cstdlib>
#include <ctime>
#include <iomanip>
#include <limits> 
#include <unordered_map>

using namespace std;

// ����� ��� ������
class Part {
    string name;
    double norm;        // ����� ���������
    double actual;      // ����������� ���������
public:
    Part(const string& name, double norm, double actual);

    double getNorm() const;
    double getActual() const;
    double getCompletionRatio() const;
    const string& getName() const;
};

#endif
