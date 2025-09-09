# Задача Монте-Карло: Оценка числа пи.
# Имеются две фигуры:
# * Квадрат со стороной 2R (Площадь равна 4R^2);
# * Круг с радиусом R, вписанный в квадрат (Площадь равна Pi * R^2).
#
# Можно сделать вывод, что вероятность попадания случайной точки в круг равна отношению площади круга
# к площади квадрата, а значит равна (Pi * R ^ 2) / (4 * R ^ 2) = Pi / 4

# Импорт библиотек
import tkinter as tk
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.patches import Circle
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg


# Создание главного окна
root = tk.Tk()
root.title("Оценка π методом Монте-Карло")
root.geometry("1200x800")
root.resizable(False, False)

# Matplotlib
plt.rcParams.update({'font.size': 16})
fig, ax = plt.subplots(figsize=(6, 6))
ax.set_xlim(-1.1, 1.1)
ax.set_ylim(-1.1, 1.1)
ax.set_aspect('equal')

# Рисуем квадрат и вписанный в него круг
ax.plot([-1, 1, 1, -1, -1], [-1, -1, 1, 1, -1], 'r-', linewidth=2)
circle = Circle((0, 0), 1, fill=False, color='green', linewidth=2)
ax.add_patch(circle)
ax.set_title('Метод Монте-Карло для оценки π')

# Создаём canvas для matplotlib
canvas = FigureCanvasTkAgg(fig, master=root)
canvas.draw()

# Размещаем график слева
canvas.get_tk_widget().pack(side=tk.LEFT, padx=10, pady=10, fill=tk.BOTH, expand=True)

# Запускаем главный цикл
root.mainloop()
