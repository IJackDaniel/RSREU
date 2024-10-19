# Функция для создания рекурсии для подсчёта значения по известной формуле
def a(n):
    # Изначально заданные значения
    if n == 1:
        return 2
    elif n == 2:
        return 3
    # Неизвестные заранее значения
    else:
        return a(n-2) ** 2 + a(n-1)


# Ввод необходимого числа элементов
num = int(input("Введите необходимое число элементов: "))

# Цикл с заголовком для подсчёта первых 8 элементов рекурсии
for i in range(1, num+1):
    value = a(i)
    print(f" {i} - {value}")
