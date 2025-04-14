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
#include "WorkshopManager.cpp"

// Завхоз (может быть любым работником)
class Storekeeper : public Employee {
    vector<WorkshopManager*> managers;
public:
    Storekeeper(const string& name, int age, double baseSalary)
        : Employee(name, age, baseSalary) {}

    double calculateSalary() const override {
        return baseSalary * 1.6; // 60% надбавка
    }

    string getPosition() const override { return "Завхоз"; }

    void addManager(WorkshopManager* manager) { managers.push_back(manager); }

    void printInfo() const override {
        Employee::printInfo();
        cout << "Обслуживаемые начальники цехов:" << endl;
        for (const auto manager : managers) {
            cout << "  " << manager->getName() << endl;
        }
    }

    const vector<WorkshopManager*>& getManagers() const { return managers; }
};