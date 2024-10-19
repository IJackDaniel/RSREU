# Основной файл лабораторной работы номер 16
from IntPy import *


def menu():
    print("Меню".center(80, "-"))
    print("1. Удалить все повторяющиеся цифры")
    print("2. Вывести все делители числа по возрастанию")
    print("3. Определить цифры, которые встречаются в записи двух чисел")
    print("4. Поменять местами первую и последнюю цифры в числе")
    print("5. Удалить из записи первого числа цифры, входящие в запись второго")
    print("6. Выход из программы")
    print("".center(80, "-"))
    choice = int(input("Введите номер операции: "))
    return choice


choice = 0
while choice != 6:
    choice = menu()

    match choice:
        case 1:
            num = int(input("Введите число: "))
            result = IntDelRepeats.delRepeats(num)
            print(f"Число без повторяющихся цифр: {result}")
        case 2:
            num = int(input("Введите число: "))
            result = AllDel.allDel(num)
            print(f"Вот все делители числа {num}\n{result}")
        case 3:
            num1 = int(input("Введите первое число: "))
            num2 = int(input("Введите второе число: "))
            result = RepNums.repNums(num1, num2)
            print(f"Вот цифры, встречающиеся сразу в двух числах:\n{result}")
        case 4:
            num = int(input("Введите число: "))
            result = ReversStartEnd.rev(num)
            print(f"Результат: {result}")
        case 5:
            num1 = int(input("Введите первое число: "))
            num2 = int(input("Введите второе число: "))
            result = Substractor.substractor(num1, num2)
            print(f"Результат: {result}")
