import numpy as np
import matplotlib.pyplot as plt
import random

# Коэффициенты для преобразований фрактала "Дракон"
transformations = [
    {
        'a': 0.824074, 'b': 0.281482, 'c': -0.212346, 'd': 0.864198,
        'e': -1.882290, 'f': -0.110607, 'prob': 0.787473
    },
    {
        'a': 0.088272, 'b': 0.520988, 'c': -0.463889, 'd': -0.377778,
        'e': 0.785360, 'f': 8.095795, 'prob': 0.212527
    }
]


def apply_transformation(x, y, transform):
    """Применяет аффинное преобразование к точке (x, y)."""
    new_x = transform['a'] * x + transform['b'] * y + transform['e']
    new_y = transform['c'] * x + transform['d'] * y + transform['f']
    return new_x, new_y


def choose_transformation():
    """Выбирает преобразование на основе вероятностей."""
    rand = random.random()
    cumulative_prob = 0
    for transform in transformations:
        cumulative_prob += transform['prob']
        if rand <= cumulative_prob:
            return transform
    return transformations[-1]  # fallback


def generate_fractal(num_points=50000):
    """Генерирует точки фрактала."""
    x, y = 0, 0
    points = np.zeros((num_points, 2))

    for i in range(num_points):
        transform = choose_transformation()
        x, y = apply_transformation(x, y, transform)
        points[i] = [x, y]

    return points


# Генерация фрактала
points = generate_fractal(1000000)

# Построение графика
plt.figure(figsize=(10, 10))
plt.scatter(points[:, 0], points[:, 1], s=0.1, c='blue', alpha=0.5)
plt.title('Фрактал "Дракон" Хартера-Хейтуэя')
plt.axis('equal')
plt.axis('off')
plt.show()
