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

# Метод итераций:
def iteration_method(a, b):
    x = (a + b) / 2
    n = 0
    while True:
        if x > 0:
            x1 = fi(x)
        else:
            x1 = -fi(x)
        r = abs(x1 - x)
        print(round(x1, 3))
        x = x1
        n += 1
        if r < eps:
            break
    print("Количество итераций:", n)
    return f'Искомое значение: {round(x, 3)}'

# Метод половинного деления:
def half_division_method(a, b):
    n = 0
    while abs(b - a) >= eps:
        z = (a + b) / 2
        if f(z) * f(a) < 0:
            b = z
        else:
            a = z
        print(round(z, 3), round(f(z), 3))
        n += 1
    x = (a + b) / 2
    print("Количество итераций:", n)
    return f'Искомое значение: {round(x, 3)}'

# Метод Ньютона:
def newton_method(a, b):
    error = False
    n = 0
    if f(a) * f2(a) > 0:
        x = a
    elif f(b) * f2(b):
        x = b
    else:
        error = True
        print("Ошибка!")
    if not(error):
        while True:
            h = f(x) / f1(x)
            x = x - h
            print(round(x, 3), round(f(x), 3))
            n += 1
            if abs(h) < eps:
                break
        print("Количество итераций:", n)
    return f'Искомое значение: {round(x, 3)}'

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
    x = a
    n = 0
    while x < (b + hx / 20):
        if f(x) * f(a + hx) > 0:
            x = x + hx
        else:
            if abs(f(x)) < eps:
                print("Искомое значение:", round(x, 3))
                break
            else:
                x += eps / 100
                if n % 500 == 0:
                    print(round(x, 3), round(f(x), 3))
                n += 1
    return f'Количество итераций: {n}'


# Первый этап: отделение корней
x0 = float(input("Введите начальную границу поиска: "))
hx = float(input("Введите шаг поиска: "))
xn = float(input("Введите конечную границу поиска: "))

a = x0
b = a + hx

while not(f(a) * f(b) < 0) and (b < (xn + hx / 20)):
    a += hx
    b += hx

if b > (xn + hx / 20):
    print("Границы не найдены")
    exit(0)
else:
    print(f'Левая граница отрезка: {round(a, 1)}, правая граница отрезка: {round(b, 1)}')

# Второй этап: уточнение корня с заданной погрешностью
print("Выберите нужный метод уточнения корней:"
      "\n1 - Итерационный метод"
      "\n2 - Метод половинного деления"
      "\n3 - Метод касательных (метод Ньютона)"
      "\n4 - Метод хорд"
      "\n5 - Прямое вычисление")
choice = int(input("Выбор: "))

print("Результат: ")
match choice:
    case 1:
        print(iteration_method(a, b))
    case 2:
        print(half_division_method(a, b))
    case 3:
        print(newton_method(a, b))
    case 4:
        print(chord_method(a, b))
    case 5:
        print(direct_calculation(a, b))
    case _:
        print("Ошибка ввода!")