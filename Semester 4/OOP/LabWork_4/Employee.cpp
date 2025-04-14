#include <iostream>
#include <string>
#include <vector>
#include <memory>
#include <cstdlib>
#include <ctime>
#include <iomanip>
#include <limits> 
#include <unordered_map>

#include "Employee.hpp"

using namespace std;

Employee::Employee(const string& name, int age, double baseSalary)
    : name(name), age(age), baseSalary(baseSalary) {}

void Employee::printInfo() const {
    cout << "ФИО: " << name << ", Возраст: " << age 
            << ", Должность: " << getPosition() 
            << ", Зарплата: " << calculateSalary() << endl;
}

const string& Employee:: getName() const { return name; }
int Employee::getAge() const { return age; }
double Employee::getBaseSalary() const { return baseSalary; }
