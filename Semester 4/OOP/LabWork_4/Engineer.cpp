#include <iostream>
#include <string>
#include <vector>
#include <memory>
#include <cstdlib>
#include <ctime>
#include <iomanip>
#include <limits> 
#include <unordered_map>

#include "Employee.cpp"
#include "Worker.cpp"

// Инженер
class Engineer : public Employee {
    vector<Worker*> subordinates;
public:
    Engineer(const string& name, int age, double baseSalary)
        : Employee(name, age, baseSalary) {}

    void addSubordinate(Worker* worker) { subordinates.push_back(worker); }

    double calculateProductivity() const {
        if (subordinates.empty()) return 1.0;
        double sum = 0.0;
        for (const auto worker : subordinates) {
            sum += worker->calculateProductivity();
        }
        return sum / subordinates.size();
    }

    double calculateSalary() const override {
        double productivity = calculateProductivity();
        double salary = baseSalary * productivity;
        if (productivity > 1.0) {
            salary *= 1.1; // 10% надбавка за перевыполнение
        }
        return salary;
    }

    string getPosition() const override { return "Инженер"; }

    void printInfo() const override {
        Employee::printInfo();
        cout << "Подчиненные рабочие:" << endl;
        for (const auto worker : subordinates) {
            cout << "  " << worker->getName() << endl;
        }
    }

    const vector<Worker*>& getSubordinates() const { return subordinates; }
};