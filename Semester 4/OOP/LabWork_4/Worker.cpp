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
#include "Employee.hpp"
#include "Part.hpp"

Worker::Worker(const string& name, int age, double baseSalary)
    : Employee(name, age, baseSalary) {}

void Worker::addPart(const Part& part) { parts.push_back(part); }

double Worker::calculateProductivity() const {
    if (parts.empty()) return 1.0;
    double sum = 0.0;
    for (const auto& part : parts) {
        sum += part.getCompletionRatio();
    }
    return sum / parts.size();
}

double Worker::calculateSalary() const {
    return baseSalary * calculateProductivity();
}

string Worker::getPosition() const { return "Рабочий"; }

void Worker::printInfo() const {
    Employee::printInfo();
    cout << "Детали:" << endl;
    for (const auto& part : parts) {
        cout << "  " << part.getName() << ": Норма=" << part.getNorm() 
                << ", Факт=" << part.getActual() 
                << ", Коэфф.=" << part.getCompletionRatio() << endl;
    }
}

const vector<Part>& Worker::getParts() const { return parts; }
