#include <iostream>
#include <string>
#include <vector>
#include <memory>
#include <cstdlib>
#include <ctime>
#include <iomanip>
#include <limits> 
#include <unordered_map>

#include "Worker.hpp"
#include "Engineer.hpp"
#include "WorkshopManager.hpp"
#include "Staff.hpp"
#include "Storekeeper.hpp"
#include "RandomGenerator.hpp"

int main() {
    setlocale(LC_ALL, "Russian");
    srand(time(nullptr));
    Staff staff;

    vector<Storekeeper*> storekeepers;
    vector<Worker*> workers;
    vector<Engineer*> engineers;
    vector<WorkshopManager*> managers;

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
                    case 1: {
                        Worker* worker = RandomGenerator::createRandomWorker();
                        workers.push_back(worker);
                        staff.add(worker);
                        newEmp = worker;
                        break;
                    }
                    case 2: {
                        Engineer* engineer = RandomGenerator::createRandomEngineer(workers);
                        engineers.push_back(engineer);
                        staff.add(engineer);
                        newEmp = engineer;
                        break;
                    }
                    case 3: {
                        if (storekeepers.empty() || engineers.empty()) {
                            cout << "Ошибка: для создания начальника цеха нужен хотя бы один завхоз и один инженер" << endl;
                            break;
                        }
                        WorkshopManager* manager = RandomGenerator::createRandomWorkshopManager(
                            engineers, storekeepers[rand() % storekeepers.size()]);
                        managers.push_back(manager);
                        staff.add(manager);
                        newEmp = manager;
                        break;
                    }
                    case 4: {
                        Storekeeper* storekeeper = RandomGenerator::createRandomStorekeeper();
                        storekeepers.push_back(storekeeper);
                        staff.add(storekeeper);
                        newEmp = storekeeper;
                        break;
                    }
                    default:
                        cout << "Неверный выбор" << endl;
                        break;
                }
                if (newEmp) {
                    cout << "Сотрудник добавлен: " << newEmp->getName() << endl;
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