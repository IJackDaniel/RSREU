#ifndef STAFF_HPP
#define STAFF_HPP

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

#include "Employee.hpp"

// ����������� �������� - ��������
class Staff {
    struct Node {
        Employee* employee;
        Node* next;
        Node(Employee* emp, Node* next = nullptr) : employee(emp), next(next) {}
        ~Node() { delete employee; }
    };

    Node* head;
    int size;

public:
    Staff();
    ~Staff();

    void add(Employee* emp);


    bool remove(int index);

    void clear();

    int getSize() const;

    Employee* get(int index) const;

    // ��������� �������
    double calculateTotalPayments() const;

    // ����������� � ������������ �������� �� ���������
    void printSalaryStatsByPosition() const;

    // ����� ���������� � ���� �����������
    void printAll() const;
};

#endif
