# Импорт необходимых нам функций из библиотеки math
from math import sin, cos

# Ввод входных параметров:
# x0, hx, xn - табуляция функции
# x, y - координаты центра окружности
# r - радиус окружности
print("Ввод диапазона табулирования:")
x0 = float(input("Начало табулирования x0: "))
hx = float(input("Шаг табулирования hx: "))
xn = float(input("Конец табулирования xn: "))

print("\nВвод параметров функции окружности:")
print("Центр окружности:")
x = float(input("x: "))
y = float(input("y: "))
r = float(input("Радиус окружности r: "))

# Проверка по ОДЗ
if x0 <= xn and hx > 0 and r > 0:
    # Определение количества итераций цикла
    N = int((xn - x0) / hx)+1
    x1 = x0
    # Цикл с заголовком, для проверки каждого значения из заданного диапазона
    for i in range(N):
        y1 = sin(x1) + cos(x1)
        # Условие принадлежности точки к окружности
        if (x1 - x)**2 + (y1 - y)**2 <= r**2:
            result = "Принадлежит"
        else:
            result = "Не принадлежит"

        # Вывод результата
        print(f"Точка ({x1:9.5f};{y1:9.5f}) - {result}")

        # Шаг табуляции
        x1 += hx
# Вывод ошибки в случае невыполнения условия ОДЗ
else:
    print("Ошибка ввода параметров")
