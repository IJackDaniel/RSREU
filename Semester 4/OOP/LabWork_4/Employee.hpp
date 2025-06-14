#ifndef EMPLOYEE_HPP
#define EMPLOYEE_HPP

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

// ����������� ������� ����� ��� ���� �����������
class Employee {
protected:
    string name;
    int age;
    double baseSalary;
public:
    Employee(const string& name, int age, double baseSalary);

    virtual ~Employee() {}

    virtual double calculateSalary() const = 0;
    virtual string getPosition() const = 0;
    virtual void printInfo() const ;

    const string& getName() const;
    int getAge() const;
    double getBaseSalary() const;
};

#endif
