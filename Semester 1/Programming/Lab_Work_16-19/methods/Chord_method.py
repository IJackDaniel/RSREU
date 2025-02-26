from math import cos


def f(n):
    return (0.9 * n * n) - cos(2*n) - 1


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