#include <iostream>
#include <string>
#include <vector>
#include <memory>
#include <cstdlib>
#include <ctime>
#include <iomanip>
#include <limits> 
#include <unordered_map>
#include <algorithm>
#include <random> // Добавляем для std::shuffle

using namespace std;

#include "RandomGenerator.hpp"
#include "Worker.hpp"
#include "Engineer.hpp"
#include "WorkshopManager.hpp"
#include "Storekeeper.hpp"

// Генерация случайных данных
string RandomGenerator::randomName() {
    // Имена и фамилии в кодировке Windows-1251
    static vector<string> names = {"Иван", "Петр", "Алексей", "Дмитрий", "Николай", 
                                    "Олег", "Сергей", "Андрей", "Василий", "Михаил"};
    static vector<string> surnames = {"Иванов", "Петров", "Сидоров", "Смирнов", "Кузнецов",
                                    "Соколов", "Попов", "Лебедев", "Козловский", "Новак"};
    return surnames[rand() % surnames.size()] + " " + names[rand() % names.size()];
}

Worker* RandomGenerator::createRandomWorker() {
    string name = randomName();
    int age = 20 + rand() % 30;
    double baseSalary = 30000 + rand() % 20000;

    Worker* worker = new Worker(name, age, baseSalary);

    // Добавляем случайные детали
    int partsCount = 1 + rand() % 5;
    for (int i = 0; i < partsCount; ++i) {
        string partName = "Деталь-" + to_string(i+1);
        double norm = 100 + rand() % 900;
        double actual = norm * (0.5 + (rand() % 100) / 100.0);
        worker->addPart(Part(partName, norm, actual));
    }

    return worker;
}

// Исправляем метод создания случайного инженера, чтобы избежать дублирования рабочих
Engineer* RandomGenerator::createRandomEngineer(const vector<Worker*>& workers) {
    if (workers.empty()) return nullptr;

    string name = randomName();
    int age = 30 + rand() % 20;
    double baseSalary = 50000 + rand() % 30000;

    Engineer* engineer = new Engineer(name, age, baseSalary);

    // Создаем вектор индексов для выбора уникальных рабочих
    vector<int> indices;
    for (int i = 0; i < workers.size(); ++i) {
        indices.push_back(i);
    }
    
    // Перемешиваем индексы для случайного выбора
    // Заменяем устаревший random_shuffle на современный std::shuffle
    std::random_device rd;
    std::mt19937 g(rd());
    std::shuffle(indices.begin(), indices.end(), g);
    
    // Определяем количество подчиненных (не больше, чем доступно рабочих)
    int subCount = 1 + rand() % min(4, static_cast<int>(workers.size()));
    
    // Добавляем уникальных подчиненных
    for (int i = 0; i < subCount; ++i) {
        engineer->addSubordinate(workers[indices[i]]);
    }

    return engineer;
}

WorkshopManager* RandomGenerator::createRandomWorkshopManager(const vector<Engineer*>& engineers, Employee* storekeeper) {
    if (engineers.empty()) return nullptr;

    string name = randomName();
    int age = 40 + rand() % 20;
    double baseSalary = 80000 + rand() % 40000;

    WorkshopManager* manager = new WorkshopManager(name, age, baseSalary, storekeeper);

    // Добавляем случайных подчиненных
    int subCount = 1 + rand() % 3;
    for (int i = 0; i < subCount && i < engineers.size(); ++i) {
        manager->addSubordinate(engineers[rand() % engineers.size()]);
    }

    return manager;
}

Storekeeper* RandomGenerator::createRandomStorekeeper() {
    string name = randomName();
    int age = 25 + rand() % 30;
    double baseSalary = 40000 + rand() % 30000;
    return new Storekeeper(name, age, baseSalary);
}
