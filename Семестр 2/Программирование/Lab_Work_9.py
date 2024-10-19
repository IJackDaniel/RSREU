# Меню
def menu():
    print()
    print("Меню".center(40, "-"))
    print("1. Показать всю базу данных")
    print("2. Добавить N записей/Изменить запись")
    print("3. Удаление записи по ключу")
    print("4. Информация по ключу")
    print("5. Вывод с условием")
    print("6. Завершение работы с базой данных")
    print("-".center(40, "-"))
    print()
    choice = int(input("Выберите пункт меню: "))
    return choice


# Объявление БД (Фамилия: Имя, должность, стаж работы, зарплата)
database = {
    "Баранов": ["Андрей", "Директор", "14", "95000"],
    "Кузнецов": ["Артём", "Заместитель директора", "5", "70000"],
    "Улиткин": ["Кирилл", "Менеджер", "3", "45000"],
    "Кабанов": ["Святослав", "Охранник", "16", "25000"],
    "Великанова": ["Елизавета", "Уборщица", "1", "12500"],
    "Краснова": ["Юлия", "Юрист", "11", "60000"],
}

# Цикл работы программы
num = 0
while num != 6:

    num = menu()

    while num not in range(1, 7):
        num = int(input("Неправильный выбор пункта меню. Повторите ввод: "))

    match num:
        # Вывод всей БД
        case 1:
            print("Фамилия".center(15), "Имя".center(15), "Должность".center(25),
                  "Стаж работы".center(15), "Зарплата".center(15))
            print("-" * 85)
            for key, data in database.items():
                print(f"{key:<15s}", f"{data[0]:<15s}", f"{data[1]:<25s}", data[2].center(15), data[3].center(15))

        # Новая запись/Изменение старой записи
        case 2:
            n = int(input("Ввод количества новых/изменённый записей: "))
            for p in range(n):
                new_key = input("Ввод фамилии: ")
                new_name = input("Введите имя: ")
                new_job = input("Введите должность: ")
                new_years, new_salary = input("Введите стаж работы и зарплату: ").split()
                database[new_key] = [new_name, new_job, new_years, new_salary]
                print("Изменения успешно внесены!")
                print()

        # Удаление записи по ключу
        case 3:
            del_key = input("Введите фамилию для удаления из БД: ")
            if del_key in database:
                del database[del_key]
                print(f"Запись о пользователе '{del_key}' была удалена.")
            else:
                print("Записи с такой фамилией нет!")

        # Вывод всей информации по ключу
        case 4:
            find = ""
            while find != "н":
                need_key = input("Введите интересующую фамилию: ")
                if need_key in database:
                    print(f"""Имя: {database[need_key][0]}
Должность: {database[need_key][1]}
Стаж работы: {database[need_key][2]}
Зарплата: {database[need_key][3]}\n""")
                else:
                    print("Записи с такой фамилией нет!\n")
                find = input("Продолжить поиск? д/н: ")

        # Вывод по условию
        case 5:
            print("Введите параметр для сортировки:")
            sort_param = input("Стаж работы / Зарплата: ")
            task = input("Больше/Меньше: ")
            value_type = input("Среднее значение/Введённое число: ")

            param = ["Стаж работы", "Зарплата"]
            sort_param_num = param.index(sort_param) + 2  # +2 т.к. перед есть ещё параметры

            if value_type == "Среднее значение":
                sm = 0
                cnt = 0
                for key, data in database.items():
                    sm += int(database[key][sort_param_num])
                    cnt += 1
                value = sm/cnt
            else:
                value = int(input("Значение: "))

            print("Фамилия".center(15), "Имя".center(15), "Должность".center(25),
                  "Стаж работы".center(15), "Зарплата".center(15))
            print("-" * 85)

            if task == "Больше":
                for key, data in database.items():
                    if int(data[sort_param_num]) > value:
                        print(f"{key:<15s}", f"{data[0]:<15s}", f"{data[1]:<25s}", data[2].center(15), data[3].center(15))
            else:
                for key, data in database.items():
                    if int(data[sort_param_num]) < value:
                        print(f"{key:<15s}", f"{data[0]:<15s}", f"{data[1]:<25s}", data[2].center(15), data[3].center(15))

        case _:
            print()
            print("Работа программы завершена".center(40, "-"))
