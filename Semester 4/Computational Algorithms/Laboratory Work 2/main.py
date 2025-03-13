import sys

import numpy as np
from PyQt6.QtWidgets import QApplication, QMainWindow, QWidget, QLabel, QPushButton
from matplotlib import pyplot as plt

from PyQt6.QtCore import Qt


class PlotWindow(QWidget):
    def __init__(self):
        super().__init__()

        self.initUI()

    def initUI(self):
        self.setWindowTitle("График")
        self.setGeometry(100, 100, 600, 400)
        # Генерация данных для графика
        x = np.linspace(-1.5, 1.5, 10000)
        y = x**5 - x - 0.2
        # Создание графика
        plt.figure()
        plt.plot(x, y, label="y")
        plt.plot(-0.942086, 0, 'ro')
        plt.title("График функции")
        plt.xlabel("x")
        plt.ylabel("y")
        plt.legend()
        plt.grid()
        # Оси x и y
        ax = plt.gca()
        ax.axhline(y=0, color='k')
        ax.axvline(x=0, color='k')
        # Отображение графика
        plt.show()


class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()

        # Параметры окна
        self.setWindowTitle("Finding the smallest root")
        self.setGeometry(400, 150, 800, 385)
        self.setFixedSize(800, 385)

        widget = QWidget()
        self.setCentralWidget(widget)

        ###### Часть для решения уравнений
        self.l = - 1e5
        self.r = 1e5 + 1
        self.step = 0.2
        self.eps = 1e-10
        self.rnd = 20
        # self.eps = 1e-6
        # self.rnd = 15
        self.results = ["-", "-", "-", "-", "-", "-"]
        self.accuracy_list = ["-", "-", "-", "-", "-", "-"]

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
        # Кнопка "График"
        self.btn_func = QPushButton(widget)
        # Текст "Результат" - одиночный
        self.result = QLabel(widget)
        # Текст "Погрешность"
        self.accuracy = QLabel(widget)
        # Погрешность
        self.accuracy_1 = QLabel(widget)
        self.accuracy_2 = QLabel(widget)
        self.accuracy_3 = QLabel(widget)
        self.accuracy_4 = QLabel(widget)
        self.accuracy_5 = QLabel(widget)
        self.accuracy_6 = QLabel(widget)

        ### Параметры
        # Общее
        self.horizontal_between = 10
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
        self.result_size_x = 200
        self.result_size_y = self.result_txt_size_y
        self.result_start_x = self.result_txt_start_x + self.result_txt_size_x + self.horizontal_between
        self.result_start_y = self.result_txt_start_y
        self.result_delta_y = self.result_txt_delta_y
        self.result_style = f"font-size: {self.result_txt_size}px; background-color: 'lightGray'; color: black;"
        # Кнопка "Решить"
        self.btn_size_x = self.result_start_x - self.equation_start_x - self.equation_size_x - self.horizontal_between - self.horizontal_between
        self.btn_size_y = self.equation_size_y + self.equation_delta_y + 5 + 1
        self.btn_x = self.equation_start_x + self.equation_size_x + self.horizontal_between
        self.btn_y = 17
        # Кнопка "График"
        self.btn_func_x = self.btn_x + self.btn_size_x + self.horizontal_between - 2
        self.btn_func_y = self.btn_y
        self.btn_func_size_x = self.result_size_x * 2 + 4
        self.btn_func_size_y = self.btn_size_y // 2
        # Текст "Результат" - одиночный
        self.result_one_txt_size = 24
        self.result_one_x = self.result_start_x
        self.result_one_y = self.btn_func_y + self.btn_func_size_y + 10
        self.result_one_size_x = self.result_size_x
        self.result_one_size_y = self.equation_size_y
        self.result_one_style = f"font-size: {self.result_one_txt_size}px; background-color: 'lightGray'; color: black;"
        # Текст "Погрешность"
        self.accuracy_txt_size = 24
        self.accuracy_txt_x = self.result_one_x + self.result_one_size_x + 10
        self.accuracy_txt_y = self.result_one_y
        self.accuracy_txt_size_x = 190
        self.accuracy_txt_size_y = self.equation_size_y
        self.accuracy_txt_style = f"font-size: {self.accuracy_txt_size}px; background-color: 'lightGray'; color: black;"
        # Погрешность
        self.accuracy_size = self.result_size
        self.accuracy_x = self.result_start_x + self.result_size_x + self.horizontal_between
        self.accuracy_y = self.result_txt_start_y
        self.accuracy_delta_y = self.result_delta_y
        self.accuracy_size_x = 190
        self.accuracy_size_y = self.result_size_y
        self.accuracy_style = f"font-size: {self.accuracy_size}px; background-color: 'lightGray'; color: black;"

        # Запуск
        self.start()

    def start(self):
        # Текст "Уравнение"
        self.equation_text.setText(" Уравнение: ")
        self.equation.setText("x^5 - x - 0.2")
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
        self.result_txt_6.setText(" Метод итераций")
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

        # Кнопка "График"
        self.btn_func.move(self.btn_func_x, self.btn_func_y)
        self.btn_func.setText("График")
        self.btn_func.setFixedSize(self.btn_func_size_x, self.btn_func_size_y)
        self.btn_func.clicked.connect(self.show_plot_window)

        # Текст "Результат" - одиночный
        self.result.move(self.result_one_x, self.result_one_y)
        self.result.setText("Результат")
        self.result.setFixedSize(self.result_one_size_x, self.result_one_size_y)
        self.result.setStyleSheet(self.result_one_style)
        self.result.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)

        # Текст "Погрешность"
        self.accuracy.move(self.accuracy_txt_x, self.accuracy_txt_y)
        self.accuracy.setText("Погрешность")
        self.accuracy.setFixedSize(self.accuracy_txt_size_x, self.accuracy_txt_size_y)
        self.accuracy.setStyleSheet(self.accuracy_txt_style)
        self.accuracy.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)

        # Погрешность
        self.change_result_txt()
        self.accuracy_1.move(self.accuracy_x, self.accuracy_y)
        self.accuracy_2.move(self.accuracy_x, self.accuracy_y + self.accuracy_delta_y)
        self.accuracy_3.move(self.accuracy_x, self.accuracy_y + self.accuracy_delta_y * 2)
        self.accuracy_4.move(self.accuracy_x, self.accuracy_y + self.accuracy_delta_y * 3)
        self.accuracy_5.move(self.accuracy_x, self.accuracy_y + self.accuracy_delta_y * 4)
        self.accuracy_6.move(self.accuracy_x, self.accuracy_y + self.accuracy_delta_y * 5)
        self.accuracy_1.setStyleSheet(self.accuracy_style)
        self.accuracy_2.setStyleSheet(self.accuracy_style)
        self.accuracy_3.setStyleSheet(self.accuracy_style)
        self.accuracy_4.setStyleSheet(self.accuracy_style)
        self.accuracy_5.setStyleSheet(self.accuracy_style)
        self.accuracy_6.setStyleSheet(self.accuracy_style)
        self.accuracy_1.setFixedSize(self.accuracy_size_x, self.accuracy_size_y)
        self.accuracy_2.setFixedSize(self.accuracy_size_x, self.accuracy_size_y)
        self.accuracy_3.setFixedSize(self.accuracy_size_x, self.accuracy_size_y)
        self.accuracy_4.setFixedSize(self.accuracy_size_x, self.accuracy_size_y)
        self.accuracy_5.setFixedSize(self.accuracy_size_x, self.accuracy_size_y)
        self.accuracy_6.setFixedSize(self.accuracy_size_x, self.accuracy_size_y)
        self.accuracy_1.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.accuracy_2.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.accuracy_3.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.accuracy_4.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.accuracy_5.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.accuracy_6.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)

    # Заданная функция
    def f(self, n):
        return n**5 - n - 0.2

    # Первая производная заданной функции:
    def f1(self, n):
        return 5 * n ** 4 - 1

    # Вторая производная заданной функции:
    def f2(self, n):
        return 20 * n ** 3

    def determine_boundaries(self):
        print("Произвожу определение границ")
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
        print("Корни уравнения находятся в данных промежутках:")
        i = 1
        for root in result:
            root[0] = round(root[0], 5)
            root[1] = round(root[1], 5)
            print(f"Корень {i} в промежутке: [{root[0]}, {root[1]}]")
            i += 1
        print()

        # Выбор необходимого корня
        need = 0
        self.l = result[need][0]
        self.r = result[need][1]

        print(f"Искомый корень находится в промежутке [{self.l}, {self.r}]\n")

    # Метод половинного деления (дихотомии, биссекции)
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
        acc = round(((right - left) / 2), self.rnd)
        self.results[0] = x
        self.accuracy_list[0] = acc

    # Метод хорд
    def chord_method(self):
        left = self.l
        right = self.r

        z = self.f(left)
        t = self.f(right)
        x = left
        while True:
            last = x
            x = left - z * ((right - left) / (t - z))
            y = self.f(x)
            if y * z < 0:
                right = x
                t = y
            else:
                left = x
                z = y
            if abs(last - x) < self.eps:
                acc = round(abs(last - x), self.rnd)
                break
        self.results[1] = x
        self.accuracy_list[1] = acc

    # Метод касательных (Ньютона)
    def newton_method(self):
        left = self.l
        right = self.r

        x_prev = float('inf')
        x = left if self.f(left) * self.f2(left) > 0 else right

        while abs(x - x_prev) > self.eps:
            x_prev = x
            x -= self.f(x) / self.f1(x)
        acc = round(abs(x - x_prev), self.rnd)

        self.results[2] = x
        self.accuracy_list[2] = round(acc, self.rnd)

    # Модифицированный метод касательных
    def modified_newton_method(self):
        left = self.l
        right = self.r

        x_prev = float('inf')
        x = left if self.f(left) * self.f2(left) > 0 else right

        d = self.f1(x)

        while abs(x - x_prev) > self.eps:
            x_prev = x
            x -= self.f(x) / d
        acc = round(abs(x - x_prev), self.rnd)

        self.results[3] = x
        self.accuracy_list[3] = acc

    # Комбинированный метод
    def combined_method(self):
        left = self.l
        right = self.r
        last = 0

        if self.f(left) * self.f2(left) > 0:
            x = left
            y = right
        else:
            x = right
            y = left

        while abs(x - y) > self.eps:
            x_new = x - self.f(x) / self.f1(x)
            y_new = y - self.f(y) * (x_new - y) / (self.f(x_new) - self.f(y))
            last = x

            x = x_new
            y = y_new

        res = (x + y) / 2.0
        acc = round(abs(x - last), self.rnd)
        self.results[4] = res
        self.accuracy_list[4] = acc

    # Метод итераций
    def iteration_method(self):
        max_iterations = 100
        left = self.l
        right = self.r

        x = (left + right) / 2
        m = max([self.f(left), self.f(right), self.f(x)])
        for _ in range(max_iterations):

            x_new = x - self.f(x) / m
            m = max(m, self.f1(x_new))

            if abs(x_new - x) < self.eps:
                acc = round(abs(x_new - x), self.rnd)
                self.results[5] = x
                self.accuracy_list[5] = acc
                return
            x = x_new

    # Отображение графика
    def show_plot_window(self):
        # Создание нового окна для графика
        plot_window = PlotWindow()
        plot_window.show()

    # Запуск решения
    def start_solution(self):
        # Определение границ
        self.determine_boundaries()

        # Использование различных методов
        self.dichotomia()
        self.chord_method()
        self.newton_method()
        self.modified_newton_method()
        self.combined_method()
        self.iteration_method()

        # Вызов отображения результата
        self.change_result_txt()

    # Функция установки результатов в Label
    def change_result_txt(self):
        results = [str(i) for i in self.results]
        self.result_1.setText(results[0])
        self.result_2.setText(results[1])
        self.result_3.setText(results[2])
        self.result_4.setText(results[3])
        self.result_5.setText(results[4])
        self.result_6.setText(results[5])
        accuracy = [str(j) for j in self.accuracy_list]
        self.accuracy_1.setText((accuracy[0]))
        self.accuracy_2.setText((accuracy[1]))
        self.accuracy_3.setText((accuracy[2]))
        self.accuracy_4.setText((accuracy[3]))
        self.accuracy_5.setText((accuracy[4]))
        self.accuracy_6.setText((accuracy[5]))


if __name__ == "__main__":
    app = QApplication(sys.argv)
    main_window = MainWindow()
    main_window.show()

    sys.exit(app.exec())
