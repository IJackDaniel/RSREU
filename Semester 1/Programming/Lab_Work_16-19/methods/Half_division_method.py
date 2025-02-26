from math import cos


def f(n):
    return (0.9 * n * n) - cos(2*n) - 1


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
