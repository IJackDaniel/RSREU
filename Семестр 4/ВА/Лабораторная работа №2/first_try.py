import sys
from time import sleep

import numpy as np
from PyQt6.QtGui import QPalette, QColor, QIntValidator
from PyQt6.QtWidgets import QApplication, QMainWindow, QVBoxLayout, QWidget, QHBoxLayout, QLabel, QCheckBox, \
    QComboBox, QGridLayout, QLineEdit, QPushButton
from matplotlib import pyplot as plt
from matplotlib.backends.backend_qtagg import FigureCanvasQTAgg as FigureCanvas
from matplotlib.figure import Figure
from PyQt6.QtCore import Qt


class PlotWindow(QWidget):
    def __init__(self, n0, n1, n2, n3, n4, n5, n6):
        super().__init__()

        self.a = float(n0)
        self.b = float(n1)
        self.c = float(n2)
        self.d = float(n3)
        self.e = float(n4)
        self.f = float(n5)
        self.g = float(n6)

        self.initUI()

    def initUI(self):
        self.setWindowTitle("График")
        self.setGeometry(100, 100, 600, 400)
        # Генерация данных для графика
        x = np.linspace(-100, 100, 2000)
        # print(self.a, self.b, self.c, self.d, self.e, self.f, self.g)
        y = self.g * x ** 6 + self.f * x ** 5 + self.e * x ** 4 + self.d * x ** 3 + self.c * x ** 2 + self.b * x + self.a
        # Создание графика
        plt.figure()
        plt.plot(x, y, label="y")
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

        self.setWindowTitle("Finding the smallest root")
        self.setGeometry(400, 150, 800, 600)
        self.setFixedSize(800, 360)

        widget = QWidget()
        self.setCentralWidget(widget)

        ########## Параметры уравнения
        self.str_formula = ""
        self.a = 0
        self.b = 0
        self.c = 0
        self.d = 0
        self.e = 0
        self.f = 0
        self.g = 0
        self.results = ["0" for el in range(1, 7)]

        ########## Инициализация объектов
        # Создание текстовых сообщений со степенями x
        self.lbl_0 = QLabel(widget)
        self.lbl_1 = QLabel(widget)
        self.lbl_2 = QLabel(widget)
        self.lbl_3 = QLabel(widget)
        self.lbl_4 = QLabel(widget)
        self.lbl_5 = QLabel(widget)
        self.lbl_6 = QLabel(widget)
        # Создание поля выбора максимальной степени X
        self.type = QComboBox(widget)
        # Создание полей ввода коэффициентов
        self.enter_0 = QLineEdit(widget)
        self.enter_1 = QLineEdit(widget)
        self.enter_2 = QLineEdit(widget)
        self.enter_3 = QLineEdit(widget)
        self.enter_4 = QLineEdit(widget)
        self.enter_5 = QLineEdit(widget)
        self.enter_6 = QLineEdit(widget)
        # Текстовое поле формулы
        self.equation = QLabel(widget)
        # Текстовые поля "результат"
        self.result_txt = QLabel(widget)
        self.result_dih = QLabel(widget)
        self.result_chord = QLabel(widget)
        self.result_newt = QLabel(widget)
        self.result_newt_mod = QLabel(widget)
        self.result_comb = QLabel(widget)
        self.result_iter = QLabel(widget)
        # Кнопка "Показать график"
        self.button = QPushButton(widget)

        ########### Параметры интерфейса
        ### Выбор максимальной степени
        # Размеры поля выбора максимальной степени Х
        self.cbbx_size_x = 200
        self.cbbx_size_y = 30
        # Параметры геометрии расположения поля выбора максимальной степени Х
        self.cbbx_start_x = 20
        self.cbbx_start_y = 20

        ### Поле текста со степенями X
        # Размер текста "степени X"
        self.lbl_size = 24
        # Параметры геометрии расположения текста "степени X"
        self.lbl_start_x = 20
        self.lbl_start_y = 70
        self.lbl_delta_y = 40

        ### Поле ввода коэффициентов
        # Параметры геометрии полей ввода
        self.ent_start_x = 70
        self.ent_start_y = self.lbl_start_y + 5
        self.ent_delta_y = self.lbl_delta_y
        self.ent_size_x = self.cbbx_size_x - self.ent_start_x + self.cbbx_start_x
        self.ent_size_y = 30
        # Размер текста в поле ввода
        self.ent_size = 18

        ### Поле вывода результата
        # Размер текста "Результат"
        self.res_size = 28
        # Параметры геометрии полей вывода результата
        self.restxt_start_x = 240
        self.restxt_start_y = 70
        self.res_start_x = self.restxt_start_x + 170
        self.res_start_y = self.restxt_start_y + 40
        self.res_delta_y = 40
        self.res_size_x = 200
        self.res_size_y = 35

        ### Поле вывода итоговой формулы
        # Размер текста "Формула"
        self.eq_size = 16
        # Параметры геометрии поля "Формула"
        self.eq_start_x = 240
        self.eq_start_y = 20
        self.ex_size_x = 540
        self.ex_size_y = 50

        self.start()

    def start(self):
        #############################################################################
        ### Поле выбора максимальной степени X
        self.type.addItems(["1", "2", "3", "4", "5", "6"])
        self.type.setFixedSize(self.cbbx_size_x, self.cbbx_size_y)
        self.type.setStyleSheet("""QComboBox {
                            background-color: 'gray'; 
                            font-size: 24px;
                            text-align: right; 
                            }""")
        self.type.move(self.cbbx_start_x, self.cbbx_start_y)
        self.type.currentIndexChanged.connect(self.index_changed)

        #############################################################################
        ### Поля текста "степени X"
        # Заполнение полей текстом
        self.lbl_0.setText("x^0")
        self.lbl_1.setText("x^1")
        self.lbl_2.setText("x^2")
        self.lbl_3.setText("x^3")
        self.lbl_4.setText("x^4")
        self.lbl_5.setText("x^5")
        self.lbl_6.setText("x^6")
        # Изменение размера текста
        self.lbl_0.setStyleSheet(f"font-size: {self.lbl_size}px;")
        self.lbl_1.setStyleSheet(f"font-size: {self.lbl_size}px;")
        self.lbl_2.setStyleSheet(f"font-size: {self.lbl_size}px;")
        self.lbl_3.setStyleSheet(f"font-size: {self.lbl_size}px;")
        self.lbl_4.setStyleSheet(f"font-size: {self.lbl_size}px;")
        self.lbl_5.setStyleSheet(f"font-size: {self.lbl_size}px;")
        self.lbl_6.setStyleSheet(f"font-size: {self.lbl_size}px;")
        # Перемещение текста на своё место
        self.lbl_0.move(self.lbl_start_x, self.lbl_start_y)
        self.lbl_1.move(self.lbl_start_x, self.lbl_start_y + self.lbl_delta_y)
        self.lbl_2.move(self.lbl_start_x, self.lbl_start_y + self.lbl_delta_y * 2)
        self.lbl_3.move(self.lbl_start_x, self.lbl_start_y + self.lbl_delta_y * 3)
        self.lbl_4.move(self.lbl_start_x, self.lbl_start_y + self.lbl_delta_y * 4)
        self.lbl_5.move(self.lbl_start_x, self.lbl_start_y + self.lbl_delta_y * 5)
        self.lbl_6.move(self.lbl_start_x, self.lbl_start_y + self.lbl_delta_y * 6)
        # Видимость текста
        self.lbl_2.setVisible(False)
        self.lbl_3.setVisible(False)
        self.lbl_4.setVisible(False)
        self.lbl_5.setVisible(False)
        self.lbl_6.setVisible(False)

        #############################################################################
        ### Поля ввода коэффициентов
        # Перемещение полей ввода на своё место
        self.enter_0.move(self.ent_start_x, self.ent_start_y)
        self.enter_1.move(self.ent_start_x, self.ent_start_y + self.ent_delta_y)
        self.enter_2.move(self.ent_start_x, self.ent_start_y + self.ent_delta_y * 2)
        self.enter_3.move(self.ent_start_x, self.ent_start_y + self.ent_delta_y * 3)
        self.enter_4.move(self.ent_start_x, self.ent_start_y + self.ent_delta_y * 4)
        self.enter_5.move(self.ent_start_x, self.ent_start_y + self.ent_delta_y * 5)
        self.enter_6.move(self.ent_start_x, self.ent_start_y + self.ent_delta_y * 6)
        # Задание размера поля ввода
        self.enter_0.setFixedSize(self.ent_size_x, self.ent_size_y)
        self.enter_1.setFixedSize(self.ent_size_x, self.ent_size_y)
        self.enter_2.setFixedSize(self.ent_size_x, self.ent_size_y)
        self.enter_3.setFixedSize(self.ent_size_x, self.ent_size_y)
        self.enter_4.setFixedSize(self.ent_size_x, self.ent_size_y)
        self.enter_5.setFixedSize(self.ent_size_x, self.ent_size_y)
        self.enter_6.setFixedSize(self.ent_size_x, self.ent_size_y)
        # Изменение размера текста
        self.enter_0.setStyleSheet(f"font-size: {self.ent_size}px;")
        self.enter_1.setStyleSheet(f"font-size: {self.ent_size}px;")
        self.enter_2.setStyleSheet(f"font-size: {self.ent_size}px;")
        self.enter_3.setStyleSheet(f"font-size: {self.ent_size}px;")
        self.enter_4.setStyleSheet(f"font-size: {self.ent_size}px;")
        self.enter_5.setStyleSheet(f"font-size: {self.ent_size}px;")
        self.enter_6.setStyleSheet(f"font-size: {self.ent_size}px;")
        # Видимость полей ввода
        self.enter_2.setVisible(False)
        self.enter_3.setVisible(False)
        self.enter_4.setVisible(False)
        self.enter_5.setVisible(False)
        self.enter_6.setVisible(False)
        # Привязка действий к полям ввода
        self.enter_0.returnPressed.connect(self.text_changed_0)
        self.enter_1.returnPressed.connect(self.text_changed_1)
        self.enter_2.returnPressed.connect(self.text_changed_2)
        self.enter_3.returnPressed.connect(self.text_changed_3)
        self.enter_4.returnPressed.connect(self.text_changed_4)
        self.enter_5.returnPressed.connect(self.text_changed_5)
        self.enter_6.returnPressed.connect(self.text_changed_6)
        # Ограничение на ввод чисел
        # self.enter_0.setValidator(QIntValidator())
        # self.enter_1.setValidator(QIntValidator())
        # self.enter_2.setValidator(QIntValidator())
        # self.enter_3.setValidator(QIntValidator())
        # self.enter_4.setValidator(QIntValidator())
        # self.enter_5.setValidator(QIntValidator())
        # self.enter_6.setValidator(QIntValidator())
        self.enter_0.textChanged.connect(self.validate_input_0)
        self.enter_1.textChanged.connect(self.validate_input_1)
        self.enter_2.textChanged.connect(self.validate_input_2)
        self.enter_3.textChanged.connect(self.validate_input_3)
        self.enter_4.textChanged.connect(self.validate_input_4)
        self.enter_5.textChanged.connect(self.validate_input_5)
        self.enter_6.textChanged.connect(self.validate_input_6)

        #############################################################################
        ### Поля текста "Результат"
        self.result_txt.setText("Результат:")
        # Стиль для текста "Результат"
        self.result_txt.setStyleSheet(f"""font-size: {self.res_size}px;""")
        self.result_dih.setStyleSheet(f"""font-size: {self.res_size}px; background-color: 'gray';""")
        self.result_chord.setStyleSheet(f"""font-size: {self.res_size}px; background-color: 'gray';""")
        self.result_newt.setStyleSheet(f"""font-size: {self.res_size}px; background-color: 'gray';""")
        self.result_newt_mod.setStyleSheet(f"""font-size: {self.res_size}px; background-color: 'gray';""")
        self.result_comb.setStyleSheet(f"""font-size: {self.res_size}px; background-color: 'gray';""")
        self.result_iter.setStyleSheet(f"""font-size: {self.res_size}px; background-color: 'gray';""")
        # Перемещение текста "Результат"
        self.result_txt.move(self.restxt_start_x, self.restxt_start_y)
        self.result_dih.move(self.res_start_x, self.res_start_y)
        self.result_chord.move(self.res_start_x, self.res_start_y + self.res_delta_y)
        self.result_newt.move(self.res_start_x, self.res_start_y + self.res_delta_y * 2)
        self.result_newt_mod.move(self.res_start_x, self.res_start_y + self.res_delta_y * 3)
        self.result_comb.move(self.res_start_x, self.res_start_y + self.res_delta_y * 4)
        self.result_iter.move(self.res_start_x, self.res_start_y + self.res_delta_y * 5)
        # Изменение размера поля текста "Результат"
        self.result_txt.setFixedSize(self.res_size_x, self.res_size_y)
        self.result_dih.setFixedSize(self.res_size_x, self.res_size_y)
        self.result_chord.setFixedSize(self.res_size_x, self.res_size_y)
        self.result_newt.setFixedSize(self.res_size_x, self.res_size_y)
        self.result_newt_mod.setFixedSize(self.res_size_x, self.res_size_y)
        self.result_comb.setFixedSize(self.res_size_x, self.res_size_y)
        self.result_iter.setFixedSize(self.res_size_x, self.res_size_y)
        # Выравнивание текста "Результат"
        self.result_dih.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.result_chord.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.result_newt.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.result_newt_mod.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.result_comb.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.result_iter.setAlignment(Qt.AlignmentFlag.AlignCenter)

        #############################################################################
        ### Поле текста "Формула"
        # self.equation.setText("1111x^6 + 1111x^5 + 1111x^4 + 1111x^3 + 1111x^2 + 1111x + 1111")
        self.equation.move(self.eq_start_x, self.eq_start_y)
        self.equation.setFixedSize(self.ex_size_x, self.ex_size_y)

        self.equation.setStyleSheet(f"""font-size: {self.eq_size}px; background-color: 'gray';""")
        self.equation.setAlignment(Qt.AlignmentFlag.AlignCenter)

        #############################################################################
        ### Кнопка вывода графика
        self.button.setText("Показать график")
        self.button.setGeometry(630, 190, 150, 150)
        self.button.clicked.connect(self.show_plot_window)

    def validate_input_0(self):
        # Получаем текущий текст из поля ввода
        text = self.enter_0.text()

        # Если текст пустой, просто возвращаем
        if text == "":
            return

        if text != "-":
            try:
                # Проверяем, является ли текст корректным числом
                float_value = float(text)
            except ValueError:
                # Если не является, удаляем последний символ для исправления
                self.enter_0.setText(text[:-1])

    def validate_input_1(self):
        # Получаем текущий текст из поля ввода
        text = self.enter_1.text()

        # Если текст пустой, просто возвращаем
        if text == "":
            return
        if text != "-":
            try:
                # Проверяем, является ли текст корректным числом
                float_value = float(text)
            except ValueError:
                # Если не является, удаляем последний символ для исправления
                self.enter_1.setText(text[:-1])

    def validate_input_2(self):
        # Получаем текущий текст из поля ввода
        text = self.enter_2.text()

        # Если текст пустой, просто возвращаем
        if text == "":
            return

        try:
            # Проверяем, является ли текст корректным числом
            float_value = float(text)
        except ValueError:
            # Если не является, удаляем последний символ для исправления
            self.enter_2.setText(text[:-1])

    def validate_input_3(self):
        # Получаем текущий текст из поля ввода
        text = self.enter_3.text()

        # Если текст пустой, просто возвращаем
        if text == "":
            return

        if text != "-":
            try:
                # Проверяем, является ли текст корректным числом
                float_value = float(text)
            except ValueError:
                # Если не является, удаляем последний символ для исправления
                self.enter_3.setText(text[:-1])

    def validate_input_4(self):
        # Получаем текущий текст из поля ввода
        text = self.enter_4.text()

        # Если текст пустой, просто возвращаем
        if text == "":
            return

        if text != "-":
            try:
                # Проверяем, является ли текст корректным числом
                float_value = float(text)
            except ValueError:
                # Если не является, удаляем последний символ для исправления
                self.enter_4.setText(text[:-1])

    def validate_input_5(self):
        # Получаем текущий текст из поля ввода
        text = self.enter_5.text()

        # Если текст пустой, просто возвращаем
        if text == "":
            return

        if text != "-":
            try:
                # Проверяем, является ли текст корректным числом
                float_value = float(text)
            except ValueError:
                # Если не является, удаляем последний символ для исправления
                self.enter_5.setText(text[:-1])

    def validate_input_6(self):
        # Получаем текущий текст из поля ввода
        text = self.enter_6.text()

        # Если текст пустой, просто возвращаем
        if text == "":
            return

        if text != "-":
            try:
                # Проверяем, является ли текст корректным числом
                float_value = float(text)
            except ValueError:
                # Если не является, удаляем последний символ для исправления
                self.enter_6.setText(text[:-1])

    def show_plot_window(self):
        # Создание нового окна для графика
        # print(self.a, self.b, self.c, self.d, self.e, self.f, self.g)
        plot_window = PlotWindow(self.a, self.b, self.c, self.d, self.e, self.f, self.g)
        plot_window.show()

    def text_changed_0(self):
        self.a = self.enter_0.text()
        self.equation_change()

    def text_changed_1(self):
        self.b = self.enter_1.text()
        self.equation_change()

    def text_changed_2(self):
        self.c = self.enter_2.text()
        self.equation_change()

    def text_changed_3(self):
        self.d = self.enter_3.text()
        self.equation_change()

    def text_changed_4(self):
        self.e = self.enter_4.text()
        self.equation_change()

    def text_changed_5(self):
        self.f = self.enter_5.text()
        self.equation_change()

    def text_changed_6(self):
        self.g = self.enter_6.text()
        self.equation_change()

    def equation_change(self):
        s = ""
        arr = [self.g, self.f, self.e, self.d, self.c, self.b, self.a]
        for i in range(len(arr)):
            elem = arr[i]
            if abs(float(elem)) > 0.001:
                if i < 5:
                    s += f"{str(elem)}x^{6 - i} + "
                elif i == 5:
                    s += f"{str(elem)}x + "
                else:
                    s += f"{str(elem)} + "

        s = s[:-2]
        self.equation.setText(s)
        raw_1 = (s.replace("^", "**")).replace("x", "*x")
        self.str_formula = raw_1
        print(f"Уравнение: {raw_1} = 0")
        raw_2 = raw_1.replace("x", "23")
        print(f"Имеет корень: {eval(raw_2)}\n")
        self.run_solution()

    def index_changed(self, index):
        self.ent_clear()
        match index:
            case 0:
                self.lbl_1.setVisible(True)
                self.lbl_2.setVisible(False)
                self.lbl_3.setVisible(False)
                self.lbl_4.setVisible(False)
                self.lbl_5.setVisible(False)
                self.lbl_6.setVisible(False)
                self.enter_1.setVisible(True)
                self.enter_2.setVisible(False)
                self.enter_3.setVisible(False)
                self.enter_4.setVisible(False)
                self.enter_5.setVisible(False)
                self.enter_6.setVisible(False)
            case 1:
                self.lbl_1.setVisible(True)
                self.lbl_2.setVisible(True)
                self.lbl_3.setVisible(False)
                self.lbl_4.setVisible(False)
                self.lbl_5.setVisible(False)
                self.lbl_6.setVisible(False)
                self.enter_1.setVisible(True)
                self.enter_2.setVisible(True)
                self.enter_3.setVisible(False)
                self.enter_4.setVisible(False)
                self.enter_5.setVisible(False)
                self.enter_6.setVisible(False)
            case 2:
                self.lbl_1.setVisible(True)
                self.lbl_2.setVisible(True)
                self.lbl_3.setVisible(True)
                self.lbl_4.setVisible(False)
                self.lbl_5.setVisible(False)
                self.lbl_6.setVisible(False)
                self.enter_1.setVisible(True)
                self.enter_2.setVisible(True)
                self.enter_3.setVisible(True)
                self.enter_4.setVisible(False)
                self.enter_5.setVisible(False)
                self.enter_6.setVisible(False)
            case 3:
                self.lbl_1.setVisible(True)
                self.lbl_2.setVisible(True)
                self.lbl_3.setVisible(True)
                self.lbl_4.setVisible(True)
                self.lbl_5.setVisible(False)
                self.lbl_6.setVisible(False)
                self.enter_1.setVisible(True)
                self.enter_2.setVisible(True)
                self.enter_3.setVisible(True)
                self.enter_4.setVisible(True)
                self.enter_5.setVisible(False)
                self.enter_6.setVisible(False)
            case 4:
                self.lbl_1.setVisible(True)
                self.lbl_2.setVisible(True)
                self.lbl_3.setVisible(True)
                self.lbl_4.setVisible(True)
                self.lbl_5.setVisible(True)
                self.lbl_6.setVisible(False)
                self.enter_1.setVisible(True)
                self.enter_2.setVisible(True)
                self.enter_3.setVisible(True)
                self.enter_4.setVisible(True)
                self.enter_5.setVisible(True)
                self.enter_6.setVisible(False)
            case 5:
                self.lbl_1.setVisible(True)
                self.lbl_2.setVisible(True)
                self.lbl_3.setVisible(True)
                self.lbl_4.setVisible(True)
                self.lbl_5.setVisible(True)
                self.lbl_6.setVisible(True)
                self.enter_1.setVisible(True)
                self.enter_2.setVisible(True)
                self.enter_3.setVisible(True)
                self.enter_4.setVisible(True)
                self.enter_5.setVisible(True)
                self.enter_6.setVisible(True)
        # print("Index changed", index)

    def ent_clear(self):
        self.a = 0
        self.b = 0
        self.c = 0
        self.d = 0
        self.e = 0
        self.f = 0
        self.g = 0

        self.equation.clear()

        self.enter_0.clear()
        self.enter_1.clear()
        self.enter_2.clear()
        self.enter_3.clear()
        self.enter_4.clear()
        self.enter_5.clear()
        self.enter_6.clear()

    def set_results(self):
        arr = self.results
        self.result_dih.setText(arr[0])
        self.result_chord.setText(arr[1])
        self.result_newt.setText(arr[2])
        self.result_newt_mod.setText(arr[3])
        self.result_comb.setText(arr[4])
        self.result_iter.setText(arr[5])

    def func(self, x):
        return float(self.g)*x**6 + float(self.f)*x**5 + float(self.e)*x**4 + float(self.d)*x**3 + float(self.c)*x**2 + float(self.b)*x + float(self.a)

    def run_solution(self):
        print(1)
        self.dihotomia()
        print(2)
        self.set_results()
        print(3)

    def dihotomia(self):
        l = -1e10 - 10
        r = 1e10 + 10
        eps = 1e-10
        x = 0
        fx = 10
        while abs(fx) >= eps:
            x = (r + l) / 2

            # print(l, x, r)
            fl = self.func(l)
            fx = self.func(x)
            if fl * fx <= 0:
                r = x
            else:
                l = x
        x = str(round(x, 5))
        self.results[0] = x


if __name__ == "__main__":
    app = QApplication(sys.argv)
    main_window = MainWindow()
    main_window.resize(800, 600)
    main_window.show()

    sys.exit(app.exec())


