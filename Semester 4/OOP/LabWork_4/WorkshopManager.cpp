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
#include "Engineer.cpp"

// Начальник цеха
class WorkshopManager : public Employee {
    vector<Engineer*> subordinates;
    Employee* storekeeper;
public:
    WorkshopManager(const string& name, int age, double baseSalary, Employee* storekeeper)
        : Employee(name, age, baseSalary), storekeeper(storekeeper) {}

    void addSubordinate(Engineer* engineer) { subordinates.push_back(engineer); }

    double calculateProductivity() const {
        if (subordinates.empty()) return 1.0;
        double sum = 0.0;
        for (const auto engineer : subordinates) {
            sum += engineer->calculateProductivity();
        }
        return sum / subordinates.size();
    }

    double calculateSalary() const override {
        double productivity = calculateProductivity();
        double bonus = 0.0;
        if (productivity > 1.3) {
            bonus = baseSalary * 0.5; // 50% премия
        } else if (productivity > 1.1) {
            bonus = baseSalary * 0.25; // 25% премия
        }
        return baseSalary + bonus;
    }

    string getPosition() const override { return "Начальник цеха"; }

    void printInfo() const override {
        Employee::printInfo();
        cout << "Подчиненные инженеры:" << endl;
        for (const auto engineer : subordinates) {
            cout << "  " << engineer->getName() << endl;
        }
        cout << "Закрепленный завхоз: " << storekeeper->getName() << endl;
    }

    const vector<Engineer*>& getSubordinates() const { return subordinates; }
    Employee* getStorekeeper() const { return storekeeper; }
};