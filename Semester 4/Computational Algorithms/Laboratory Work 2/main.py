"""
Справа от уравнения можно добавить кнопку, для показа графика

"""
import sys
from time import sleep
from math import tan

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
        self.l_1 = 0
        self.r_1 = 1e10
        self.step_1 = 100
        self.l = None
        self.r = None
        self.step = 1
        self.results = ["0", "0", "0", "0", "0", "0"]
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
        self.equation.setText("x = tg(x)")
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
        self.result_1.setText(" -")
        self.result_2.setText(" -")
        self.result_3.setText(" -")
        self.result_4.setText(" -")
        self.result_5.setText(" -")
        self.result_6.setText(" -")
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

    def f(self, n):
        return tan(n) - n
        # return (n - 1005) ** 3 - 5

    def determine_boundaries(self):
        print("Произвожу определение границ первой стадии")
        left = self.l_1
        right = self.l_1 + self.step_1
        f_l = self.f(left)
        f_r = self.f(right)
        while f_l * f_r > 0:
            left = right
            right += self.step_1
            f_l = self.f(left)
            f_r = self.f(right)

        self.l = left
        self.r = right
        print(left, right)

        print("Произвожу уточнение границ второй стадии")
        left = self.l
        right = self.l + self.step
        f_l = self.f(left)
        f_r = self.f(right)
        while f_l * f_r > 0:
            left = right
            right += self.step
            f_l = self.f(left)
            f_r = self.f(right)

        print(left, right)
        self.l = left
        self.r = right

    def start_solution(self):
        self.determine_boundaries()

        self.change_result_txt()

    def change_result_txt(self):
        self.result_1.setText(self.results[0])
        self.result_2.setText(self.results[1])
        self.result_3.setText(self.results[2])
        self.result_4.setText(self.results[3])
        self.result_5.setText(self.results[4])
        self.result_6.setText(self.results[5])



if __name__ == "__main__":
    app = QApplication(sys.argv)
    main_window = MainWindow()
    main_window.show()

    sys.exit(app.exec())
