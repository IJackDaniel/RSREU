#ifndef WORKSHOPMANAGER_HPP
#define WORKSHOPMANAGER_HPP

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

#include "Engineer.hpp"

class WorkshopManager : public Employee {
    vector<Engineer*> subordinates;
    Employee* storekeeper;
public:
    WorkshopManager(const string& name, int age, double baseSalary, Employee* storekeeper);

    void addSubordinate(Engineer* engineer);

    double calculateProductivity() const;

    double calculateSalary() const override;

    string getPosition() const override;

    void printInfo() const override;

    const vector<Engineer*>& getSubordinates() const;
    Employee* getStorekeeper() const;
};

#endif
