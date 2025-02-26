from math import sqrt


c = float(input())
l = 1.0  # минимальный предел числа
r = 1e10 + 1  # максимальный предел числа
eps = 1e-6  # точность вычисления
flag = True  # флаг для запуска и остановки цикла

while flag:
    m = (r + l) / 2
    num = m**2 + sqrt(m)
    if abs(num - c) < eps:
        print(m)
        flag = False
    elif num > c:
        r = m
    else:
        l = m
