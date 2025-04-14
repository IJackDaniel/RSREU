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

#include "WorkshopManager.hpp"
#include "Employee.hpp"
#include "Engineer.hpp"

WorkshopManager::WorkshopManager(const string& name, int age, double baseSalary, Employee* storekeeper)
    : Employee(name, age, baseSalary), storekeeper(storekeeper) {}

void WorkshopManager::addSubordinate(Engineer* engineer) { subordinates.push_back(engineer); }

double WorkshopManager::calculateProductivity() const {
    if (subordinates.empty()) return 1.0;
    double sum = 0.0;
    for (const auto engineer : subordinates) {
        sum += engineer->calculateProductivity();
    }
    return sum / subordinates.size();
}

double WorkshopManager::calculateSalary() const {
    double productivity = calculateProductivity();
    double bonus = 0.0;
    if (productivity > 1.3) {
        bonus = baseSalary * 0.5; // 50% премия
    } else if (productivity > 1.1) {
        bonus = baseSalary * 0.25; // 25% премия
    }
    return baseSalary + bonus;
}

string WorkshopManager::getPosition() const { return "��������� ����"; }

void WorkshopManager::printInfo() const {
    Employee::printInfo();
    cout << "���������� ��������:" << endl;
    for (const auto engineer : subordinates) {
        cout << "  " << engineer->getName() << endl;
    }
    cout << "����������� �����: " << storekeeper->getName() << endl;
}

const vector<Engineer*>& WorkshopManager::getSubordinates() const { return subordinates; }
Employee* WorkshopManager::getStorekeeper() const { return storekeeper; }