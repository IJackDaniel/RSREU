from math import cos, sin, acos
eps = 1e-4

# Заданная функция:
def f(n):
    f = (0.9 * n * n) - cos(2 * n) - 1
    return f

# Вспомогательная функция для реализации метода итераций:
def fi(n):
    fi = acos(-1 + 0.9 * n * n) / 2
    return fi

# Первая производная заданной функции:
def f1(n):
    f1 = 1.8 * n + 2 * sin(2 * n)
    return f1

# Вторая производная заданной функции:
def f2(n):
    f2 = 1.8 + 4 * cos(2 * n)
    return f2


############################################################
# Метод хорд:
def chord_method(a, b):
    z = f(a)
    t = f(b)
    x = a
    s = 0
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
        print(round(x, 3), round(y, 3))
        s += 1
        if abs(n - x) < eps:
            break
    print("Количество итераций:", s)
    return f'Искомое значение: {round(x, 3)}'

# Прямое вычисление:
def direct_calculation(a, b):
    n = 0
    while a < (b + hx / 20):
        if f(a) * f(a + hx) > 0:
            a = a + hx
        else:
            if abs(f(a)) < eps:
                print("Искомое значение:", round(a, 3))
                break
            else:
                a += eps / 100
                if n % 500 == 0:
                    print(round(a, 3), round(f(a), 3))
                n += 1
    return f'Количество итераций: {n}'



############################################################
x0 = float(input("Введите начальную границу поиска: "))
hx = float(input("Введите шаг поиска: "))
xn = float(input("Введите конечную границу поиска: "))

a = x0
b = a + hx