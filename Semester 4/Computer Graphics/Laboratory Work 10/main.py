import tkinter as tk
from tkinter import ttk, messagebox
from math import cos, sin, radians


class RotationMatrixApp:
    def __init__(self, root):
        self.root = root
        self.root.title("Матрица вращения")

        # Функция валидации ввода (только числа)
        self.validate_number = (root.register(self._validate_number), '%P')

        # Поля ввода
        ttk.Label(root, text="Координата x:").grid(row=0, column=0, padx=5, pady=5, sticky='e')
        self.entry_x = ttk.Entry(root, width=15, validate='key', validatecommand=self.validate_number)
        self.entry_x.grid(row=0, column=1, padx=5, pady=5)

        ttk.Label(root, text="Координата y:").grid(row=1, column=0, padx=5, pady=5, sticky='e')
        self.entry_y = ttk.Entry(root, width=15, validate='key', validatecommand=self.validate_number)
        self.entry_y.grid(row=1, column=1, padx=5, pady=5)

        ttk.Label(root, text="Угол поворота (градусы):").grid(row=2, column=0, padx=5, pady=5, sticky='e')
        self.entry_angle = ttk.Entry(root, width=15, validate='key', validatecommand=self.validate_number)
        self.entry_angle.grid(row=2, column=1, padx=5, pady=5)

        # Кнопки
        ttk.Button(root, text="Очистить", command=self.clear_entries).grid(row=3, column=0, padx=5, pady=10)
        ttk.Button(root, text="Показать матрицу", command=self.show_rotation_matrix).grid(row=3, column=1, padx=5,
                                                                                          pady=10)

    def _validate_number(self, new_value):
        """Проверяет, что ввод является числом (целым или вещественным)"""
        if new_value == "":
            return True  # Разрешаем пустую строку
        try:
            float(new_value)
            return True
        except ValueError:
            return False

    def clear_entries(self):
        """Очищает все поля ввода"""
        self.entry_x.delete(0, tk.END)
        self.entry_y.delete(0, tk.END)
        self.entry_angle.delete(0, tk.END)

    def get_rotation_matrix(self, angle_deg, x, y):
        """Возвращает матрицу вращения 3x3 вокруг точки (x, y)"""
        angle_rad = radians(angle_deg)
        cos_theta = cos(angle_rad)
        sin_theta = sin(angle_rad)

        # Матрица перехода в начало координат
        translate_to_origin = [
            [1, 0, -x],
            [0, 1, -y],
            [0, 0, 1]
        ]

        # Матрица поворота
        rotation = [
            [cos_theta, -sin_theta, 0],
            [sin_theta, cos_theta, 0],
            [0, 0, 1]
        ]

        # Матрица обратного перехода
        translate_back = [
            [1, 0, x],
            [0, 1, y],
            [0, 0, 1]
        ]

        # Композиция преобразований: translate_back * rotation * translate_to_origin
        step1 = self.matrix_multiply(rotation, translate_to_origin)
        result = self.matrix_multiply(translate_back, step1)

        return result

    def matrix_multiply(self, a, b):
        """Умножение матриц a и b (собственная реализация)"""
        rows_a = len(a)
        cols_a = len(a[0]) if rows_a > 0 else 0
        rows_b = len(b)
        cols_b = len(b[0]) if rows_b > 0 else 0

        if cols_a != rows_b:
            raise ValueError("Несовместимые размеры матриц для умножения")

        # Создаем результирующую матрицу
        result = [[0 for _ in range(cols_b)] for _ in range(rows_a)]

        for i in range(rows_a):
            for j in range(cols_b):
                for k in range(cols_a):
                    result[i][j] += a[i][k] * b[k][j]

        return result

    def show_rotation_matrix(self):
        """Показывает матрицу вращения в новом окне"""
        try:
            x = float(self.entry_x.get()) if self.entry_x.get() else 0.0
            y = float(self.entry_y.get()) if self.entry_y.get() else 0.0
            angle = float(self.entry_angle.get()) if self.entry_angle.get() else 0.0
        except ValueError:
            messagebox.showerror("Ошибка", "Пожалуйста, введите корректные числовые значения")
            return

        # Получаем матрицу вращения
        rotation_matrix = self.get_rotation_matrix(angle, x, y)

        # Создаем новое окно для отображения матрицы
        matrix_window = tk.Toplevel(self.root)
        matrix_window.title(f"Матрица вращения на {angle}° вокруг точки ({x}, {y})")

        # Отображаем матрицу
        ttk.Label(matrix_window, text="Матрица вращения (3x3):", font=('Arial', 10, 'bold')).pack(pady=5)

        for row in rotation_matrix:
            row_text = " ".join([f"{elem:10.4f}" for elem in row])
            ttk.Label(matrix_window, text=row_text, font=('Courier', 10)).pack()

        # Кнопка закрытия
        ttk.Button(matrix_window, text="Закрыть", command=matrix_window.destroy).pack(pady=10)


if __name__ == "__main__":
    root = tk.Tk()
    app = RotationMatrixApp(root)
    root.mainloop()
