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

#include "Staff.hpp"
#include "Employee.hpp"

// Композитная сущность - Персонал

Staff::Staff() : head(nullptr), size(0) {}
Staff::~Staff() {
    clear();
}

void Staff::add(Employee* emp) {
    head = new Node(emp, head);
    size++;
}

bool Staff::remove(int index) {
    if (index < 0 || index >= size) return false;

    Node** ptr = &head;
    for (int i = 0; i < index; ++i) {
        ptr = &((*ptr)->next);
    }

    Node* temp = *ptr;
    *ptr = temp->next;
    delete temp;
    size--;
    return true;
}

void Staff::clear() {
    while (head) {
        Node* temp = head;
        head = head->next;
        delete temp;
    }
    size = 0;
}

int Staff::getSize() const { return size; }

Employee* Staff::get(int index) const {
    if (index < 0 || index >= size) return nullptr;
    Node* current = head;
    for (int i = 0; i < index; ++i) {
        current = current->next;
    }
    return current->employee;
}

// Суммарные выплаты
double Staff::calculateTotalPayments() const {
    double total = 0.0;
    Node* current = head;
    while (current) {
        total += current->employee->calculateSalary();
        current = current->next;
    }
    return total;
}

// Минимальная и максимальная зарплата по должности
void Staff::printSalaryStatsByPosition() const {
    struct Stats {
        double min = numeric_limits<double>::max();
        double max = numeric_limits<double>::min();
        int count = 0;
    };

    unordered_map<string, Stats> stats;

    Node* current = head;
    while (current) {
        Employee* emp = current->employee;
        string position = emp->getPosition();
        double salary = emp->calculateSalary();

        stats[position].min = min(stats[position].min, salary);
        stats[position].max = max(stats[position].max, salary);
        stats[position].count++;
        current = current->next;
    }

    cout << "���������� �� ���������:" << endl;
    for (const auto& [position, stat] : stats) {
        cout << position << ": ���=" << stat.min << ", ����=" << stat.max 
                << ", ����������=" << stat.count << endl;
    }
}

// Вывод информации о всех сотрудниках
void Staff::printAll() const {
    cout << "������ ����������� (" << size << "):" << endl;
    Node* current = head;
    int index = 0;
    while (current) {
        cout << "[" << index << "] ";
        current->employee->printInfo();
        cout << endl;
        current = current->next;
        index++;
    }
}