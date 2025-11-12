# Задача Монте-Карло: Оценка числа пи.
# Имеются две фигуры:
# * Квадрат со стороной 2R (Площадь равна 4R^2);
# * Круг с радиусом R, вписанный в квадрат (Площадь равна Pi * R^2).
#
# Можно сделать вывод, что вероятность попадания случайной точки в круг равна отношению площади круга
# к площади квадрата, а значит равна (Pi * R ^ 2) / (4 * R ^ 2) = Pi / 4

# Импорт библиотек
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.patches import Circle

# Количество точек
NUM_POINTS = 10000

# Функция моделирования определённого количества точек
def simulate_points(num_points):
    x = np.random.uniform(-1, 1, num_points)
    y = np.random.uniform(-1, 1, num_points)

    dist = np.sqrt(x ** 2 + y ** 2)
    inside_circle = dist <= 1

    return x, y, inside_circle

# Создаем график
plt.figure(figsize=(10, 8))
plt.rcParams.update({'font.size': 16})

# Генерируем точки
x, y, inside_circle = simulate_points(NUM_POINTS)

# Рисуем квадрат
plt.plot([-1, 1, 1, -1, -1], [-1, -1, 1, 1, -1], 'r-', linewidth=2)

# Рисуем круг
circle = Circle((0, 0), 1, fill=False, color='green', linewidth=2)
plt.gca().add_patch(circle)

# Рисуем точки
plt.scatter(x[inside_circle], y[inside_circle], color='blue', s=5, alpha=0.6, label='Точки внутри круга')
plt.scatter(x[~inside_circle], y[~inside_circle], color='red', s=5, alpha=0.6, label='Точки вне круга')

# Настраиваем график
plt.xlim(-1.1, 1.1)
plt.ylim(-1.1, 1.1)
plt.gca().set_aspect('equal')
plt.title(f'Метод Монте-Карло для оценки π (точек: {NUM_POINTS})')
plt.legend()

# Вычисляем результат
points_inside = np.sum(inside_circle)
hit_probability = points_inside / NUM_POINTS
estimated_pi = hit_probability * 4
target_probability = np.pi / 4

# Выводим результаты
result_text = (f"Точек в круге: {points_inside}\n"
               f"Всего точек: {NUM_POINTS}\n"
               f"Вероятность попадания: {hit_probability:.4f}\n"
               f"Стремится к π/4 = {target_probability:.4f}\n"
               f"Оценка π: {estimated_pi:.4f}\n"
               f"Точное значение π: {np.pi:.4f}\n"
               f"Погрешность: {abs(estimated_pi - np.pi):.4f}")

print(result_text)

# Показываем график
plt.tight_layout()
plt.show()