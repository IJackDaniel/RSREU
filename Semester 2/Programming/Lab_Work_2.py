from random import uniform
from time import perf_counter_ns


# Определение размерности матрицы и выбор формата её заполнения
n, m = 5, 6
choice = int(input("Выбор формата ввода\n1 - автоматический\n2 - ручной\nВвод: "))
# Объявление матрицы
matrix = [0] * n
# Заполнение матрицы выбранным форматом
match choice:
    case 1:
        for i1 in range(n):
            s = [0] * m
            for j1 in range(m):
                s[j1] = round(uniform(-100, 100), 3)
            matrix[i1] = s
    case 2:
        for i2 in range(n):
            s = [0] * m
            for j2 in range(m):
                s[j2] = float(input(f"Элемент {j2} строки {i2} = "))
            matrix[i2] = s
    case _:
        print("Неправильный выбор формата заполнения матрицы")
        exit(1)

# Эхо-вывод матрицы
print("\nЭхо-вывод матрицы:")
for i in range(n):
    for j in range(m):
        print(matrix[i][j], end=" ")
    print()

# Программа без использования методов списка
t1_start = perf_counter_ns()
for r1 in range(100):
    lst1 = matrix.copy()
    result1 = []
    for i in range(n):
        # Определение максимального элемента в строке
        mx = lst1[i][0]
        for j in range(m):
            if lst1[i][j] > mx:
                mx = lst1[i][j]
        result1 += [mx]
t1_end = perf_counter_ns()
T1 = (t1_end-t1_start)/100

# Программа с использованием методов списка
t2_start = perf_counter_ns()
for r2 in range(100):
    lst2 = matrix.copy()
    result2 = []
    for i in range(n):
        # Определение максимального элемента в строке
        mx = max(lst2[i])
        result2 += [mx]
t2_end = perf_counter_ns()
T2 = (t2_end-t2_start)/100

# Вывод двух массивов для сравнения результатов
print("\nМассив A обработан без использования методов списков, а массив B с использованием методов.")
print(f"Массив A: {result1}")
print(f"Массив B: {result2}\n")
# Вывод времени выполнения каждого из решений
print(f"Время выполнения для массива A = {T1} нс")
print(f"Время выполнения для массива B = {T2} нс")
# Сравнение времени
if T1 > T2:
    print(f"Программа с использованием методов списков оказалась на {round((T1 - T2), 7)} нс быстрее")
elif T2 > T1:
    print(f"Программа без использования методов списков оказалась на {round((T2 - T1), 7)} нс быстрее")
else:
    print("Время выполнения двух программ примерно равно")
