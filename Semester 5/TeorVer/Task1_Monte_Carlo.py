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


def update_plot():
    ...


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

# Создание контейнера для правой части
right_frame = tk.Frame(root, width=400)
right_frame.pack(side=tk.RIGHT, padx=20, pady=20, fill=tk.Y)
right_frame.pack_propagate(False)

# Контейнер для элементов управления
control_frame = tk.Frame(right_frame)
control_frame.pack(expand=True, pady=50)

# Элементы управления
label = tk.Label(control_frame, text="Количество точек:", font=("Arial", 24))
entry = tk.Entry(control_frame, width=15, font=("Arial", 24))
button = tk.Button(control_frame, text="Смоделировать", command=update_plot,
                   font=("Arial", 24), height=2, width=15)

# Размещаем элементы управления
label.pack(pady=10)
entry.pack(pady=10)
button.pack(pady=20)

# Отдельный контейнер для результатов
result_frame = tk.Frame(right_frame)
result_frame.pack(side=tk.BOTTOM, fill=tk.X, pady=20)

# Поле для результатов
result_label = tk.Label(result_frame, text="Введите количество точек и нажмите 'Смоделировать'",
                        justify=tk.LEFT, font=("Arial", 20), wraplength=350)
result_label.pack()

# Размещаем график слева
canvas.get_tk_widget().pack(side=tk.LEFT, padx=10, pady=10, fill=tk.BOTH, expand=True)

# Запускаем главный цикл
root.mainloop()
