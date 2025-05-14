#ifndef RAND_GEN_HPP
#define RAND_GEN_HPP

#include <iostream>
#include <string>
#include <vector>
#include <memory>
#include <cstdlib>
#include <ctime>
#include <iomanip>
#include <limits> 
#include <unordered_map>
#include <algorithm>  // ��� std::shuffle
#include <random>     // ��� std::mt19937 � std::random_device

using namespace std;

#include "Worker.hpp"
#include "Engineer.hpp"
#include "WorkshopManager.hpp"
#include "Storekeeper.hpp"

// ��������� ��������� ������
class RandomGenerator {
    public:
        static string randomName();
    
        static Worker* createRandomWorker();
    
        static Engineer* createRandomEngineer(const vector<Worker*>& workers);
    
        static WorkshopManager* createRandomWorkshopManager(const vector<Engineer*>& engineers, Employee* storekeeper);
    
        static Storekeeper* createRandomStorekeeper();
    };

#endif
