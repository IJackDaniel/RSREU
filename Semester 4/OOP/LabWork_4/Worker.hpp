#ifndef WORKER_HPP
#define WORKER_HPP


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

#include "Employee.hpp"
#include "Part.hpp"

// Рабочий
class Worker : public Employee {
    vector<Part> parts;
public:
    Worker(const string& name, int age, double baseSalary);

    void addPart(const Part& part);

    double calculateProductivity() const;

    double calculateSalary() const override;

    string getPosition() const override;

    void printInfo() const override;

    const vector<Part>& getParts() const;
};

#endif