def f(x):
    return x ** 5 - x - 0.2


def f_prime(x):
    return 5 * x ** 4 - 1


def newton_method(initial_guess, tolerance, max_iterations):
    x_n = initial_guess
    for i in range(max_iterations):
        f_x = f(x_n)
        f_prime_x = f_prime(x_n)

        if abs(f_prime_x) < 1e-10:  # Проверка на нулевую производную
            print("Производная близка к нулю. Метод не может быть применен.")
            return None

        x_next = x_n - f_x / f_prime_x  # Формула Ньютона

        if abs(x_next - x_n) < tolerance:
            print(f"Корень уточнен после {i + 1} итераций: {x_next}")
            return x_next

        x_n = x_next

    print(f"Достигнуто максимальное количество итераций. Последнее значение: {x_n}")
    return x_n


# Начальное приближение (близкое к корню -0.942086)
initial_guess = -1.0  # Можно также попробовать -0.9
# Точность
tolerance = 1e-6
# Максимальное количество итераций
max_iterations = 1000

# Вызов метода Ньютона
root = newton_method(initial_guess, tolerance, max_iterations)