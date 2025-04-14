#include <iostream>
#include <string>
#include <vector>
#include <memory>
#include <cstdlib>
#include <ctime>
#include <iomanip>
#include <limits> 
#include <unordered_map>

#include "Worker.cpp"
#include "Engineer.cpp"
#include "WorkshopManager.cpp"
#include "Staff.cpp"
#include "Storekeeper.cpp"
#include "RandomGenerator.cpp"

int main() {
    srand(time(nullptr));
    Staff staff;

    // Создаем несколько завхозов
    vector<Storekeeper*> storekeepers;
    for (int i = 0; i < 2; ++i) {
        storekeepers.push_back(RandomGenerator::createRandomStorekeeper());
    }

    // Создаем рабочих
    vector<Worker*> workers;
    for (int i = 0; i < 10; ++i) {
        workers.push_back(RandomGenerator::createRandomWorker());
    }

    // Создаем инженеров
    vector<Engineer*> engineers;
    for (int i = 0; i < 5; ++i) {
        engineers.push_back(RandomGenerator::createRandomEngineer(workers));
    }

    // Создаем начальников цехов
    vector<WorkshopManager*> managers;
    for (int i = 0; i < 3; ++i) {
        managers.push_back(RandomGenerator::createRandomWorkshopManager(engineers, storekeepers[rand() % storekeepers.size()]));
    }

    // Добавляем всех в персонал
    for (auto worker : workers) staff.add(worker);
    for (auto engineer : engineers) staff.add(engineer);
    for (auto manager : managers) staff.add(manager);
    for (auto storekeeper : storekeepers) staff.add(storekeeper);

    // Основное меню
    while (true) {
        cout << "\nМеню:" << endl;
        cout << "1. Вывести список сотрудников" << endl;
        cout << "2. Добавить случайного сотрудника" << endl;
        cout << "3. Удалить сотрудника" << endl;
        cout << "4. Рассчитать суммарные выплаты" << endl;
        cout << "5. Показать статистику по зарплатам" << endl;
        cout << "0. Выход" << endl;

        int choice;
        cout << "Выберите действие: ";
        cin >> choice;

        switch (choice) {
            case 1:
                staff.printAll();
                break;
            case 2: {
                cout << "Выберите тип сотрудника:" << endl;
                cout << "1. Рабочий" << endl;
                cout << "2. Инженер" << endl;
                cout << "3. Начальник цеха" << endl;
                cout << "4. Завхоз" << endl;
                int type;
                cin >> type;

                Employee* newEmp = nullptr;
                switch (type) {
                    case 1:
                        newEmp = RandomGenerator::createRandomWorker();
                        break;
                    case 2:
                        newEmp = RandomGenerator::createRandomEngineer(workers);
                        break;
                    case 3:
                        newEmp = RandomGenerator::createRandomWorkshopManager(engineers, storekeepers[rand() % storekeepers.size()]);
                        break;
                    case 4:
                        newEmp = RandomGenerator::createRandomStorekeeper();
                        storekeepers.push_back(dynamic_cast<Storekeeper*>(newEmp));
                        break;
                    default:
                        cout << "Неверный выбор" << endl;
                        break;
                }
                if (newEmp) {
                    staff.add(newEmp);
                    cout << "Сотрудник добавлен" << endl;
                }
                break;
            }
            case 3: {
                int index;
                cout << "Введите номер сотрудника для удаления: ";
                cin >> index;
                if (staff.remove(index)) {
                    cout << "Сотрудник удален" << endl;
                } else {
                    cout << "Неверный номер" << endl;
                }
                break;
            }
            case 4:
                cout << "Суммарные выплаты: " << staff.calculateTotalPayments() << endl;
                break;
            case 5:
                staff.printSalaryStatsByPosition();
                break;
            case 0:
                return 0;
            default:
                cout << "Неверный выбор" << endl;
        }
    }

    return 0;
}