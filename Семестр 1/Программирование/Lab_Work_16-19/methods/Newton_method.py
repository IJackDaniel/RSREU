from math import cos, sin


def f(n):
    return (0.9 * n * n) - cos(2*n) - 1


def f_pr1(n):
    return 1.8 * n + 2 * sin(2 * n)


def f_pr2(n):
    return 1.8 + 4 * cos(2 * n)


def newton_method(a, b, hx):
    eps = 1e-4

    error = False
    if f(a) * f_pr2(a) > 0:
        x = a
    elif f(b) * f_pr2(b) > 0:
        x = b
    else:
        error = True
        return 'Ошибка'

    if not (error):
        while True:
            hx = f(x) / f_pr1(x)
            x = x - hx
            if abs(hx) < eps:
                break
        return x
