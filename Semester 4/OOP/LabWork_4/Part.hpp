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

// Класс для детали
class Part {
    string name;
    double norm;        // Норма выработки
    double actual;      // Фактическая выработка
public:
    Part(const string& name, double norm, double actual);

    double getNorm() const;
    double getActual() const;
    double getCompletionRatio() const;
    const string& getName() const;
};

#endif
