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
#include "Part.cpp"

// Рабочий
class Worker : public Employee {
    vector<Part> parts;
public:
    Worker(const string& name, int age, double baseSalary)
        : Employee(name, age, baseSalary) {}

    void addPart(const Part& part) { parts.push_back(part); }

    double calculateProductivity() const {
        if (parts.empty()) return 1.0;
        double sum = 0.0;
        for (const auto& part : parts) {
            sum += part.getCompletionRatio();
        }
        return sum / parts.size();
    }

    double calculateSalary() const override {
        return baseSalary * calculateProductivity();
    }

    string getPosition() const override { return "Рабочий"; }

    void printInfo() const override {
        Employee::printInfo();
        cout << "Детали:" << endl;
        for (const auto& part : parts) {
            cout << "  " << part.getName() << ": норма=" << part.getNorm() 
                 << ", факт=" << part.getActual() 
                 << ", коэф.=" << part.getCompletionRatio() << endl;
        }
    }

    const vector<Part>& getParts() const { return parts; }
};