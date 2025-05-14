#ifndef STOREKEEPER_HPP
#define STOREKEEPER_HPP

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

// Завхоз (может быть любым работником)
class Storekeeper : public Employee {
    vector<WorkshopManager*> managers;
public:
    Storekeeper(const string& name, int age, double baseSalary);

    double calculateSalary() const override;

    string getPosition() const override;

    void addManager(WorkshopManager* manager);

    void printInfo() const override;

    const vector<WorkshopManager*>& getManagers() const;
};

#endif
