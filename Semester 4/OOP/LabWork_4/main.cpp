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
        cout << "\n����:" << endl;
        cout << "1. ������� ������ �����������" << endl;
        cout << "2. �������� ���������� ����������" << endl;
        cout << "3. ������� ����������" << endl;
        cout << "4. ���������� ��������� �������" << endl;
        cout << "5. �������� ���������� �� ���������" << endl;
        cout << "0. �����" << endl;

        int choice;
        cout << "�������� ��������: ";
        cin >> choice;

        switch (choice) {
            case 1:
                staff.printAll();
                break;
            case 2: {
                cout << "�������� ��� ����������:" << endl;
                cout << "1. �������" << endl;
                cout << "2. �������" << endl;
                cout << "3. ��������� ����" << endl;
                cout << "4. ������" << endl;
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
                            cout << "������: ��� �������� ���������� ���� ����� ���� �� ���� ������ � ���� �������" << endl;
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
                        cout << "�������� �����" << endl;
                        break;
                }
                if (newEmp) {
                    cout << "��������� ��������: " << newEmp->getName() << endl;
                }
                break;
            }
            case 3: {
                int index;
                cout << "������� ����� ���������� ��� ��������: ";
                cin >> index;
                if (staff.remove(index)) {
                    cout << "��������� ������" << endl;
                } else {
                    cout << "�������� �����" << endl;
                }
                break;
            }
            case 4:
                cout << "��������� �������: " << staff.calculateTotalPayments() << endl;
                break;
            case 5:
                staff.printSalaryStatsByPosition();
                break;
            case 0:
                return 0;
            default:
                cout << "�������� �����" << endl;
        }
    }

    return 0;
}