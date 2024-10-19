def f(x0, a0, b0, c0, d0):
    return a0 * (x0 ** 3) + b0 * (x0 ** 2) + c0 * x0 + d0


a, b, c, d = list(map(int, input().split()))
l = -1e10 - 1  # минимальный предел числа
r = 1e10 + 1  # максимальный предел числа
eps = 1e-10  # точность вычисления
x = None

while abs(r - l) >= eps:
    x = (l + r) / 2
    y1 = f(x, a, b, c, d)
    y2 = f(l, a, b, c, d)
    if y1 * y2 <= 0:
        r = x
    else:
        l = x
print(x)
