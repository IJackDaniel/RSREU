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

// Композитная сущность - Персонал
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
    Staff() : head(nullptr), size(0) {}
    ~Staff() {
        clear();
    }

    void add(Employee* emp) {
        head = new Node(emp, head);
        size++;
    }

    bool remove(int index) {
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

    void clear() {
        while (head) {
            Node* temp = head;
            head = head->next;
            delete temp;
        }
        size = 0;
    }

    int getSize() const { return size; }

    Employee* get(int index) const {
        if (index < 0 || index >= size) return nullptr;
        Node* current = head;
        for (int i = 0; i < index; ++i) {
            current = current->next;
        }
        return current->employee;
    }

    // Суммарные выплаты
    double calculateTotalPayments() const {
        double total = 0.0;
        Node* current = head;
        while (current) {
            total += current->employee->calculateSalary();
            current = current->next;
        }
        return total;
    }

    // Минимальная и максимальная зарплата по должности
    void printSalaryStatsByPosition() const {
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

        cout << "Статистика по зарплатам:" << endl;
        for (const auto& [position, stat] : stats) {
            cout << position << ": мин=" << stat.min << ", макс=" << stat.max 
                 << ", количество=" << stat.count << endl;
        }
    }

    // Вывод информации о всех сотрудниках
    void printAll() const {
        cout << "Список сотрудников (" << size << "):" << endl;
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
};