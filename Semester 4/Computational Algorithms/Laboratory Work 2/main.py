"""
Справа от уравнения можно добавить кнопку, для показа графика

"""
import sys
from time import sleep
from math import tan, acos

import numpy as np
from PyQt6.QtGui import QPalette, QColor, QIntValidator
from PyQt6.QtWidgets import QApplication, QMainWindow, QVBoxLayout, QWidget, QHBoxLayout, QLabel, QCheckBox, \
    QComboBox, QGridLayout, QLineEdit, QPushButton
from matplotlib import pyplot as plt
from matplotlib.backends.backend_qtagg import FigureCanvasQTAgg as FigureCanvas
from matplotlib.figure import Figure
from PyQt6.QtCore import Qt


class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()

        self.setWindowTitle("Finding the smallest root")
        self.setGeometry(400, 150, 400, 600)
        self.setFixedSize(660, 385)

        widget = QWidget()
        self.setCentralWidget(widget)

        ###### Часть для решения уравнений
        self.l = - 1e5
        self.r = 1e5 + 1
        self.step = 0.2
        self.eps = 1e-6
        self.results = ["-", "-", "-", "-", "-", "-"]
        # Суть в чём. Сначала пойдёт первая стадия, на которой выделится большой промежуток, содержащий корень
        # На второй стадии мы будем работать в этом промежутке для определения новых границ с шагом 1

        ###### Графическая часть
        ### Объявления
        # Текст "Уравнение"
        self.equation_text = QLabel(widget)
        self.equation = QLabel(widget)
        # Текст "Результат"
        self.result_txt_1 = QLabel(widget)
        self.result_txt_2 = QLabel(widget)
        self.result_txt_3 = QLabel(widget)
        self.result_txt_4 = QLabel(widget)
        self.result_txt_5 = QLabel(widget)
        self.result_txt_6 = QLabel(widget)
        # Результат
        self.result_1 = QLabel(widget)
        self.result_2 = QLabel(widget)
        self.result_3 = QLabel(widget)
        self.result_4 = QLabel(widget)
        self.result_5 = QLabel(widget)
        self.result_6 = QLabel(widget)
        # Кнопка "Решить"
        self.btn_solution = QPushButton(widget)

        ### Параметры
        # Текст "Уравнение"
        self.equation_txt_size = 32
        self.equation_size_x = 200
        self.equation_size_y = 45
        self.equation_start_x = 20
        self.equation_start_y = 20
        self.equation_delta_y = self.equation_size_y + 10
        self.equation_style = f"font-size: {self.equation_txt_size}px; background-color: 'lightGray'; color: black;"
        # Текст "Результат"
        self.result_txt_size = 18
        self.result_txt_size_x = 350
        self.result_txt_size_y = 30
        self.result_txt_start_x = 20
        self.result_txt_start_y = 135
        self.result_txt_delta_y = self.result_txt_size_y + 10
        self.result_txt_style = f"font-size: {self.result_txt_size}px; background-color: 'lightGray'; color: black;"
        # Результат
        self.result_size = self.result_txt_size
        self.result_size_x = 250
        self.result_size_y = self.result_txt_size_y
        self.result_between = 20
        self.result_start_x = self.result_txt_start_x + self.result_txt_size_x + self.result_between
        self.result_start_y = self.result_txt_start_y
        self.result_delta_y = self.result_txt_delta_y
        self.result_style = f"font-size: {self.result_txt_size}px; background-color: 'lightGray'; color: black;"
        # Кнопка "Решить"
        # self.btn_size_x = self.result_txt_start_x + self.result_txt_size_x - self.equation_size_x
        # self.btn_size_y = self.equation_size_y * 2 + self.equation_delta_y
        self.btn_size_x = self.result_start_x - self.equation_start_x - self.equation_size_x - self.result_between - 20
        self.btn_size_y = self.equation_size_y + self.equation_delta_y + 5
        self.btn_x = self.equation_start_x + self.equation_size_x + 20
        self.btn_y = 20

        self.start()

    def start(self):
        # Текст "Уравнение"
        self.equation_text.setText(" Уравнение: ")
        self.equation.setText("x**5 - x - 0.2")
        self.equation_text.move(self.equation_start_x, self.equation_start_y)
        self.equation.move(self.equation_start_x, self.equation_start_y + self.equation_delta_y)
        self.equation_text.setFixedSize(self.equation_size_x, self.equation_size_y)
        self.equation.setFixedSize(self.equation_size_x, self.equation_size_y)
        self.equation.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.equation_text.setStyleSheet(self.equation_style)
        self.equation.setStyleSheet(self.equation_style)

        # Текст "Результат"
        self.result_txt_1.setText(" Метод Дихотомии")
        self.result_txt_2.setText(" Метод Хорд")
        self.result_txt_3.setText(" Метод Касательных")
        self.result_txt_4.setText(" Модифицированный метод касательных")
        self.result_txt_5.setText(" Комбинированный метод")
        self.result_txt_6.setText(" Итерационный метод")
        self.result_txt_1.move(self.result_txt_start_x, self.result_txt_start_y)
        self.result_txt_2.move(self.result_txt_start_x, self.result_txt_start_y + self.result_txt_delta_y)
        self.result_txt_3.move(self.result_txt_start_x, self.result_txt_start_y + self.result_txt_delta_y * 2)
        self.result_txt_4.move(self.result_txt_start_x, self.result_txt_start_y + self.result_txt_delta_y * 3)
        self.result_txt_5.move(self.result_txt_start_x, self.result_txt_start_y + self.result_txt_delta_y * 4)
        self.result_txt_6.move(self.result_txt_start_x, self.result_txt_start_y + self.result_txt_delta_y * 5)
        self.result_txt_1.setStyleSheet(self.result_txt_style)
        self.result_txt_2.setStyleSheet(self.result_txt_style)
        self.result_txt_3.setStyleSheet(self.result_txt_style)
        self.result_txt_4.setStyleSheet(self.result_txt_style)
        self.result_txt_5.setStyleSheet(self.result_txt_style)
        self.result_txt_6.setStyleSheet(self.result_txt_style)
        self.result_txt_1.setFixedSize(self.result_txt_size_x, self.result_txt_size_y)
        self.result_txt_2.setFixedSize(self.result_txt_size_x, self.result_txt_size_y)
        self.result_txt_3.setFixedSize(self.result_txt_size_x, self.result_txt_size_y)
        self.result_txt_4.setFixedSize(self.result_txt_size_x, self.result_txt_size_y)
        self.result_txt_5.setFixedSize(self.result_txt_size_x, self.result_txt_size_y)
        self.result_txt_6.setFixedSize(self.result_txt_size_x, self.result_txt_size_y)

        # Результат
        self.change_result_txt()
        self.result_1.move(self.result_start_x, self.result_start_y)
        self.result_2.move(self.result_start_x, self.result_start_y + self.result_delta_y)
        self.result_3.move(self.result_start_x, self.result_start_y + self.result_delta_y * 2)
        self.result_4.move(self.result_start_x, self.result_start_y + self.result_delta_y * 3)
        self.result_5.move(self.result_start_x, self.result_start_y + self.result_delta_y * 4)
        self.result_6.move(self.result_start_x, self.result_start_y + self.result_delta_y * 5)
        self.result_1.setStyleSheet(self.result_style)
        self.result_2.setStyleSheet(self.result_style)
        self.result_3.setStyleSheet(self.result_style)
        self.result_4.setStyleSheet(self.result_style)
        self.result_5.setStyleSheet(self.result_style)
        self.result_6.setStyleSheet(self.result_style)
        self.result_1.setFixedSize(self.result_size_x, self.result_size_y)
        self.result_2.setFixedSize(self.result_size_x, self.result_size_y)
        self.result_3.setFixedSize(self.result_size_x, self.result_size_y)
        self.result_4.setFixedSize(self.result_size_x, self.result_size_y)
        self.result_5.setFixedSize(self.result_size_x, self.result_size_y)
        self.result_6.setFixedSize(self.result_size_x, self.result_size_y)
        self.result_1.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.result_2.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.result_3.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.result_4.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.result_5.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.result_6.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)

        # Кнопка "Решить"
        self.btn_solution.move(self.btn_x, self.btn_y)
        self.btn_solution.setText("Решить")
        self.btn_solution.setFixedSize(self.btn_size_x, self.btn_size_y)
        self.btn_solution.clicked.connect(self.start_solution)

    # Заданная функция
    def f(self, n):
        return n**5 - n - 0.2

    # Первая производная заданной функции:
    def f1(self, n):
        return 5 * n ** 4 - 1

    # Вторая производная заданной функции:
    def f2(self, n):
        return 20 * n ** 3

    # Вспомогательная функция для реализации метода итераций:
    # СХОДИТСЯ НЕ ТУДА, НУЖНО ПОДОБРАТЬ НОВОЕ ВЫРАЖЕНИЕ
    def fi(self, n):
        return n - (n**5 - n - 0.2) / 4

    def determine_boundaries(self):
        print("Произвожу определение границ первой стадии")
        left = self.l
        right = self.l + self.step
        result = []
        while right <= self.r:
            f_l = self.f(left)
            f_r = self.f(right)
            if f_l * f_r < 0:
                result.append([left, right])
            left = right
            right += self.step
        print(result)
        print("Корни уравнения находятся в данных промежутках:")
        for root in result:
            print(*root)
        print()

        # Выбор необходимого корня
        need = 0
        self.l = result[need][0]
        self.r = result[need][1]

        print(f"Искомый корень находится в промежутке [{self.l}, {self.r}]")

    def dichotomia(self):
        left = self.l
        right = self.r

        while abs(right - left) >= self.eps:
            x = (right + left) / 2
            if self.f(left) * self.f(x) < 0:
                right = x
            else:
                left = x

        x = (right + left) / 2
        self.results[0] = x

    def chord_method(self):
        left = self.l
        right = self.r

        z = self.f(left)
        t = self.f(right)
        x = left
        while True:
            n = x
            x = left - ((right - left) / (t - z)) * z
            y = self.f(x)
            if y * z < 0:
                right = x
                t = y
            else:
                left = x
                z = y
            if abs(n - x) < self.eps:
                break

        self.results[1] = x

    def newton_method(self):
        error = False
        left = self.l
        right = self.r
        x = None

        if self.f(left) * self.f2(left) > 0:
            x = left
        elif self.f(right) * self.f2(right):
            x = right
        else:
            error = True
            print("Ошибка!")
        if not error:
            while True:
                h = self.f(x) / self.f1(x)
                x = x - h
                if abs(h) < self.eps:
                    break
        self.results[2] = x

    def nthtrh(self):
        ...

    def fgnigb(self):
        ...

    def iteration_method(self):
        left = self.l
        right = self.r
        x = (left + right) / 2
        while True:
            if x > 0:
                x1 = self.fi(x)
            else:
                x1 = -self.fi(x)
            r = abs(x1 - x)
            x = x1
            if r < self.eps:
                break
        self.results[5] = x

    def start_solution(self):
        self.determine_boundaries()

        self.dichotomia()
        self.chord_method()
        self.newton_method()
        #
        #
        self.iteration_method()

        self.change_result_txt()

    def change_result_txt(self):
        results = [str(i) for i in self.results]
        self.result_1.setText(results[0])
        self.result_2.setText(results[1])
        self.result_3.setText(results[2])
        self.result_4.setText(results[3])
        self.result_5.setText(results[4])
        self.result_6.setText(results[5])


if __name__ == "__main__":
    app = QApplication(sys.argv)
    main_window = MainWindow()
    main_window.show()

    sys.exit(app.exec())
