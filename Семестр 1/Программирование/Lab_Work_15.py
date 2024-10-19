from math import sin, cos, pi


# Функция, определённая условием задачи
def f(x):
    f = (sin(x) + (cos(2*x))) / (2 + cos(x))
    return f


# Входные данные
a1, a2 = map(float, input("Начало интегрирования в формате (a1*pi)/a2: ").split())
b1, b2 = map(float, input("Конец интегрирования в формате (b1*pi)/b2: ").split())
n = int(input("Количество трапеций: "))

a = (a1*pi)/a2
b = (b1*pi)/b2
# Подсчёт значения высоты каждой из трапеции
h = (b-a)/n
# Определение "длины средней линии трапеции"
integral = (f(a) + f(b)) / 2

# Подсчёт конечной суммы значений функции на отрезке
for i in range(1, n):
    integral += f(a + i*h)

integral *= h

# Вывод результата
print(integral)
