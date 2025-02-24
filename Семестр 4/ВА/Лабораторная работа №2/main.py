import sys
import numpy as np
from PyQt6.QtGui import QPalette, QColor
from PyQt6.QtWidgets import QApplication, QMainWindow, QVBoxLayout, QWidget, QHBoxLayout, QLabel, QCheckBox, \
    QComboBox, QGridLayout, QLineEdit, QPushButton
from matplotlib import pyplot as plt
from matplotlib.backends.backend_qtagg import FigureCanvasQTAgg as FigureCanvas
from matplotlib.figure import Figure
from PyQt6.QtCore import Qt


class PlotWindow(QWidget):
    def __init__(self, n0, n1, n2, n3, n4, n5, n6):
        super().__init__()

        self.a = int(n0)
        self.b = int(n1)
        self.c = int(n2)
        self.d = int(n3)
        self.e = int(n4)
        self.f = int(n5)
        self.g = int(n6)

        self.initUI()

    def initUI(self):
        self.setWindowTitle("График")
        self.setGeometry(100, 100, 600, 400)
        # Генерация данных для графика
        x = np.linspace(0, 10, 100)
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
        self.result = QLabel(widget)
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
        self.res_size = 36
        # Параметры геометрии полей вывода результата
        self.restxt_start_x = 240
        self.restxt_start_y = 70
        self.res_start_x = self.restxt_start_x
        self.res_start_y = self.restxt_start_y + 50
        self.res_size_x = 200
        self.res_size_y = 50

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

        #############################################################################
        ### Поля текста "Результат"
        self.result_txt.setText("Результат:")
        self.result.setText("-")
        # Стиль для текста "Результат"
        self.result_txt.setStyleSheet(f"""font-size: {self.res_size}px;""")
        self.result.setStyleSheet(f"""font-size: {self.res_size}px; background-color: 'gray'; text-align: right;""")
        # Перемещение текста "Результат"
        self.result_txt.move(self.restxt_start_x, self.restxt_start_y)
        self.result.move(self.res_start_x, self.res_start_y)
        # Изменение размера поля текста "Результат"
        self.result_txt.setFixedSize(self.res_size_x, self.res_size_y)
        self.result.setFixedSize(self.res_size_x, self.res_size_y)
        # Выравнивание текста "Результат"
        self.result.setAlignment(Qt.AlignmentFlag.AlignCenter)

        #############################################################################
        ### Поле текста "Формула"
        # self.equation.setText("1111x^6 + 1111x^5 + 1111x^4 + 1111x^3 + 1111x^2 + 1111x + 1111")
        self.equation.move(self.eq_start_x, self.eq_start_y)
        self.equation.setFixedSize(self.ex_size_x, self.ex_size_y)

        self.equation.setStyleSheet(f"""font-size: {self.eq_size}px; background-color: 'gray';""")
        self.equation.setAlignment(Qt.AlignmentFlag.AlignCenter)

        #############################################################################
        ### Поле графика matplotlib
        # Кнопка
        self.button.setText("Показать график")
        self.button.setGeometry(500, 200, 100, 100)
        self.button.clicked.connect(self.show_plot_window)


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
            if int(elem) != 0:
                if int(elem) > 0:
                    if i < 5:
                        s += f"{str(elem)}x^{6 - i} + "
                    elif i == 5:
                        s += f"{str(elem)}x + "
                    else:
                        s += f"{str(elem)} + "
                else:
                    if i < 5:
                        s += f"{str(elem)}x^{6 - i} - "
                    elif i == 5:
                        s += f"{str(elem)}x - "
                    else:
                        s += f"{str(elem)} - "
        s = s[:-2]
        self.equation.setText(s)
        raw_1 = (s.replace("^", "**")).replace("x", "*x")
        self.str_formula = raw_1
        print(f"Уравнение: {raw_1} = 0")
        raw_2 = raw_1.replace("x", "23")
        print(f"Имеет корень: {eval(raw_2)}\n")

    def rechenie(self):
        ...

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
        print("Index changed", index)

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


if __name__ == "__main__":
    app = QApplication(sys.argv)
    main_window = MainWindow()
    main_window.resize(800, 600)
    main_window.show()
    sys.exit(app.exec())
