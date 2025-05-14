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

#include "Storekeeper.hpp"
#include "Employee.hpp"
#include "WorkshopManager.hpp"

Storekeeper::Storekeeper(const string& name, int age, double baseSalary)
    : Employee(name, age, baseSalary) {}

double Storekeeper::calculateSalary() const {
    return baseSalary * 1.6; // 60% надбавка
}

string Storekeeper::getPosition() const { return "Завхоз"; }

void Storekeeper::addManager(WorkshopManager* manager) { managers.push_back(manager); }

void Storekeeper::printInfo() const {
    Employee::printInfo();
    cout << "Обслуживаемые начальники цехов:" << endl;
    for (const auto manager : managers) {
        cout << "  " << manager->getName() << endl;
    }
}

const vector<WorkshopManager*>& Storekeeper::getManagers() const { return managers; }
