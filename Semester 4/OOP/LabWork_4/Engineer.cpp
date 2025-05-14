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

#include "Engineer.hpp"
#include "Employee.hpp"
#include "Worker.hpp"

Engineer::Engineer(const string& name, int age, double baseSalary)
    : Employee(name, age, baseSalary) {}

void Engineer::addSubordinate(Worker* worker) { subordinates.push_back(worker); }

double Engineer::calculateProductivity() const {
    if (subordinates.empty()) return 1.0;
    double sum = 0.0;
    for (const auto worker : subordinates) {
        sum += worker->calculateProductivity();
    }
    return sum / subordinates.size();
}

double Engineer::calculateSalary() const {
    double productivity = calculateProductivity();
    double salary = baseSalary * productivity;
    if (productivity > 1.0) {
        salary *= 1.1; // 10% надбавка за перевыполнение
    }
    return salary;
}

string Engineer::getPosition() const { return "Инженер"; }

void Engineer::printInfo() const {
    Employee::printInfo();
    if (subordinates.size() != 0) {
        cout << "Подчиненные рабочие:" << endl;
        for (const auto worker : subordinates) {
            cout << "  " << worker->getName() << endl;
        }
    }
}

const vector<Worker*>& Engineer::getSubordinates() const { return subordinates; }

void Engineer::updateSubordinates(const vector<Worker*>& newSubordinates) {
    subordinates = newSubordinates;
}
