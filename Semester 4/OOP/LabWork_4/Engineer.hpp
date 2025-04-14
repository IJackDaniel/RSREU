#ifndef ENGINEER_HPP
#define ENGINEER_HPP

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

#include "Worker.hpp"

// Инженер
class Engineer : public Employee {
    vector<Worker*> subordinates;
public:
    Engineer(const string& name, int age, double baseSalary);

    void addSubordinate(Worker* worker);

    double calculateProductivity() const;

    double calculateSalary() const override;

    string getPosition() const override;

    void printInfo() const override;

    const vector<Worker*>& getSubordinates() const;
};

#endif