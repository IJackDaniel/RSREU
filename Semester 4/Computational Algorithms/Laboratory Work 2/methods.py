from math import cos, acos, sin


def f(n):
    return (0.9 * n * n) - cos(2*n) - 1


def f_pr1(n):
    return 1.8 * n + 2 * sin(2 * n)


def f_pr2(n):
    return 1.8 + 4 * cos(2 * n)


# Вспомогательная функция для реализации метода итераций:
def fi(n):
    return acos(-1 + 0.9 * n * n) / 2


def chord_method(a, b):
    eps = 1e-4

    z = f(a)
    t = f(b)
    x = a
    while True:
        n = x
        x = a - ((b - a) / (t - z)) * z
        y = f(x)
        if y * z < 0:
            b = x
            t = y
        else:
            a = x
            z = y
        if abs(n - x) < eps:
            break

    return x


def half_division_method(a, b):
    eps = 1e-4

    while abs(b - a) >= eps:
        z = (a + b) / 2
        if f(a) * f(z) < 0:
            b = z
        else:
            a = z
    x = (a + b) / 2
    return x


def iteration_method(a, b):
    eps = 1e-4
    x = (a+b)/2
    while True:
        if x > 0:
            x1 = fi(x)
            # print(x, x1)
            if abs(x1 - x) <= eps:
                break
            x = x1
        else:
            x1 = -fi(x)
            # print(x, x1)
            if abs(x1 - x) <= eps:
                break
            x = x1

    return x1


# a = 0
# b = 1
#
# print(chord_method(a, b))
# print(half_division_method(a, b))
# print(iteration_method(a, b))

