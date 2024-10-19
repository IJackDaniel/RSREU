from math import cos, sqrt, acos
eps = 1e-4

# Заданная функция:
def f(n):
    f = (0.9 * n * n) - cos(2 * n) - 1
    return f

# Вспомогательная функция для реализации метода итераций:
def fi(n):
    return acos(-1 + 0.9 * n * n) / 2



# Метод итераций:
def iteration_method(a, b):
    x = (a+b)/2
    while True:
        if x > 0:
            x1 = fi(x)
            print(x, x1)
            if abs(x1 - x) <= eps:
                break
            x = x1
        else:
            x1 = -fi(x)
            print(x, x1)
            if abs(x1 - x) <= eps:
                break
            x = x1

    return x1


# Первый этап: отделение корней
x0 = float(input("Введите левую границу промежутка для поиска корней: "))
hx = float(input("Введите шаг поиска: "))
xn = float(input("Введите правую границу промежутка для поиска корней: "))

a = x0
b = x0 + hx

while (not(f(a) * f(b) < 0)) and (b < xn + hx / 2):
    a = a + hx
    b = b + hx
print(f'Левая граница промежутка: {round(a, 3)}, правая граница промежутка: {round(b, 3)}')

if b > (xn + hx / 2):
    print("На данном промежутке нет корней")
    exit(0)

print(iteration_method(a, b))
