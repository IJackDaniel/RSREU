from math import cos


def f(n):
    return (0.9 * n * n) - cos(2*n) - 1


def direct_calculation(a, b, hx):
    eps = 1e-4

    while True:
        a = a + hx
        b = b - hx
        fa = abs(f(a))
        fb = abs(f(b))
        if fa <= eps:
            return a
        if fb <= eps:
            return b
        if (abs(b - a)) <= eps:
            break