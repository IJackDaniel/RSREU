# Программа для табулирования функции
# Входные данные:
# x0(float) - точка начала табулирования
# h(float) - шаг табулирования
# xn(float) - точка конца табулирования
# ОДЗ:
# x0, xn ∈ R
# x0 <= xn
# h > 0
# Выходные данные:
# y(float) - значения функции на всём пути табулирования функции

# Ввод параметров x0, h, xn
x0 = float(input("Введите значение х0: "))
h = float(input("Введите значение h: "))
xn = float(input("Введите значение хn: "))
# x - параметр, содержащий сведения о местоположении точки табулирования на оси x
x = x0

# Проверка по ОДЗ
if x0 <= xn and h > 0:
    # Вывод верхней части таблицы
    print("┌" + "─" * 16 + "┬" + "─" * 16 + "┐")

    # Цикл для табулирования функции
    while x0 <= x <= xn:
        # Определение значения параметра y
        if x <= -5:
            y = 4
        elif x < -2:
            y = (x+3) ** 2
        else:
            y = x+3
        # Вывод значения x и y
        print(f"│ x = {x:<10.5f} │ y = {y:<10.5f} │")
        # Шаг табулирования
        x += h
        # Условие для правильного построения таблицы
        if x0 <= x <= xn + h/2:
            print("├" + "─" * 16 + "┼" + "─" * 16 + "┤")

    # Вывод нижней части таблицы
    print("└" + "─" * 16 + "┴" + "─" * 16 + "┘")
else:
    print("Введённые значения не проходят по ОДЗ")
