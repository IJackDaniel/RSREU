import sys

from PyQt6.QtWidgets import QApplication, QMainWindow, QWidget, QLabel, QPushButton, QLineEdit
from PyQt6.QtGui import QPainter, QPen, QDoubleValidator
from PyQt6.QtCore import Qt

from copy import deepcopy


class MatrixBracketWidget(QWidget):
    def __init__(self, parent=None, rows=3, cell_height=30, bracket_width=20, matrix_width=100):
        super().__init__(parent)
        self.rows = rows
        self.cell_height = cell_height
        self.bracket_width = bracket_width
        self.matrix_width = matrix_width
        self.setFixedSize(bracket_width * 2 + matrix_width, rows * cell_height)

    def paintEvent(self, event):
        painter = QPainter(self)
        painter.setPen(QPen(Qt.GlobalColor.white, 2))

        height = self.rows * self.cell_height

        # Рисуем левую скобку
        painter.drawLine(0, 10, 0, height - 10)
        painter.drawArc(0, 0, self.bracket_width, 20, 90 * 16, 90 * 16)
        painter.drawArc(0, height - 20, self.bracket_width, 20, 180 * 16, 90 * 16)

        # Рисуем правую скобку
        right_bracket_x = self.bracket_width + self.matrix_width
        painter.drawLine(right_bracket_x, 10, right_bracket_x, height - 10)
        painter.drawArc(right_bracket_x - self.bracket_width, 0, self.bracket_width, 20, 0, 90 * 16)
        painter.drawArc(right_bracket_x - self.bracket_width, height - 20, self.bracket_width, 20, 270 * 16, 90 * 16)


class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()

        # Параметры окна
        self.setWindowTitle("Solving SLAE")
        width = 740
        height = 720
        self.setGeometry(300, 100, width, height)
        self.setFixedSize(width, height)

        widget = QWidget()
        self.setCentralWidget(widget)

        ##### Общие параметры
        self.eps = 1e-3
        self.rnd = 7
        self.matrix_a = [[8.2, -3.2, 14.2, 14.8],
                         [5.6, -12, 15, -6.4],
                         [5.7, 3.6, -12.4, -2.3],
                         [6.8, 13.2, -6.3, -8.7]]
        self.matrix_b = [-8.4, 4.5, 3.3, 14.3]
        self.results = ["-", "-", "-", "-"]
        self.determ = 0
        self.cond = 0

        ##### Объявления
        # Текст Исходные данные / Результат
        self.lbl_inp = QLabel(widget)
        self.lbl_res = QLabel(widget)
        # Рамки
        self.frame_inp = QLabel(widget)
        self.frame_out = QLabel(widget)
        # Названия матриц
        self.lbl_A = QLabel(widget)
        self.lbl_B = QLabel(widget)
        self.lbl_X = QLabel(widget)
        # Виджеты со скобками для матриц
        # MatrixBracketWidget(self, rows=3, cell_height=30, bracket_width=20, matrix_width=100):
        self.matrix_A_brackets = MatrixBracketWidget(widget, 4, 58, 30, 360)
        self.matrix_B_brackets = MatrixBracketWidget(widget, 4, 58, 30, 90)
        self.matrix_X_brackets = MatrixBracketWidget(widget, 4, 58, 30, 120)
        ### Элементы Матриц
        # Элементы матрицы A
        self.A_11 = QLineEdit(widget)
        self.A_12 = QLineEdit(widget)
        self.A_13 = QLineEdit(widget)
        self.A_14 = QLineEdit(widget)
        self.A_21 = QLineEdit(widget)
        self.A_22 = QLineEdit(widget)
        self.A_23 = QLineEdit(widget)
        self.A_24 = QLineEdit(widget)
        self.A_31 = QLineEdit(widget)
        self.A_32 = QLineEdit(widget)
        self.A_33 = QLineEdit(widget)
        self.A_34 = QLineEdit(widget)
        self.A_41 = QLineEdit(widget)
        self.A_42 = QLineEdit(widget)
        self.A_43 = QLineEdit(widget)
        self.A_44 = QLineEdit(widget)
        # Элементы матрицы B
        self.B_1 = QLineEdit(widget)
        self.B_2 = QLineEdit(widget)
        self.B_3 = QLineEdit(widget)
        self.B_4 = QLineEdit(widget)
        # Элементы матрицы X
        self.X_1 = QLabel(widget)
        self.X_2 = QLabel(widget)
        self.X_3 = QLabel(widget)
        self.X_4 = QLabel(widget)
        # Текст "Определитель матрицы" и "Чисто обусловленности"
        self.txt_determ = QLabel(widget)
        self.txt_obysl = QLabel(widget)
        # Значение "Определитель матрицы" и "Чисто обусловленности"
        self.res_determ = QLabel(widget)
        self.res_obysl = QLabel(widget)
        # Кнопка "Решить"
        self.btn = QPushButton(widget)

        ##### Параметры
        self.between_y_lbl_txt = 10
        self.between_y_txt_label = 5
        # Текст Исходные данные
        self.lbl_base_start_x = 20
        self.lbl_base_start_y = 20
        self.lbl_base_size_x = 200
        self.lbl_base_size_y = 30
        self.lbl_base_txt_size = 24
        self.lbl_base_style = f"font-size: {self.lbl_base_txt_size}px; color: white;"
        # Рамка 1
        self.frame_start_x = self.lbl_base_start_x
        self.frame_start_y = self.lbl_base_start_y + self.lbl_base_size_y + self.between_y_txt_label
        self.frame_size_x = 700
        self.frame_size_y = 300
        self.frame_style = "border-style: solid; border-width: 1px; border-color: white;"
        # Текст Результат
        self.lbl_base_start_y_2 = self.frame_start_y + self.frame_size_y + self.between_y_lbl_txt
        # Рамка 2
        self.frame_start_y_2 = self.lbl_base_start_y_2 + self.lbl_base_size_y + self.between_y_txt_label
        ### Названия матриц
        self.lbl_mx_size_x = 40
        self.lbl_mx_size_y = 30
        self.lbl_mx_text_size = 24
        self.lbl_mx_style = f"font-size: {self.lbl_mx_text_size}px; color: white;"
        # Матрица A
        self.lbl_A_x = self.frame_start_x + 20
        self.lbl_A_y = self.frame_start_y + self.frame_size_y // 2 - self.lbl_mx_size_y // 2
        # Матрица B
        self.lbl_B_x = 500
        self.lbl_B_y = self.lbl_A_y
        # Матрица X
        self.lbl_X_x = self.lbl_A_x
        self.lbl_X_y = self.frame_start_y_2 + self.frame_size_y // 2 - self.lbl_mx_size_y // 2
        ### Координаты скобок
        self.bracket_btw_x = 100
        self.bracket_btw_y = 33
        # Матрица A
        self.bracket_A_x = self.lbl_A_x + self.lbl_mx_size_x + 10
        self.bracket_A_y = self.frame_start_y + self.bracket_btw_y
        # Матрица B
        self.bracket_B_x = self.lbl_B_x + self.lbl_mx_size_x + 10
        self.bracket_B_y = self.bracket_A_y
        # Матрица X
        self.bracket_X_x = self.lbl_X_x + self.lbl_mx_size_x + 10
        self.bracket_X_y = self.frame_start_y_2 + self.bracket_btw_y
        ### Элементы Матриц
        self.mx_elem_size_x = 80
        self.mx_elem_size_y = 40
        self.mx_elem_txt_size = 24
        self.mx_elem_style = f"font-size: {self.mx_elem_txt_size}px; background-color: 'White'; color: black;"
        self.mx_elem_style_x = f"font-size: {self.mx_elem_txt_size - 4}px; background-color: 'lightGray'; color: black;"
        self.mx_elem_btw_bracket_x = 20
        self.mx_elem_btw_bracket_y = 20
        self.mx_elem_btw_x = self.mx_elem_size_x + 10
        self.mx_elem_btw_y = self.mx_elem_size_y + 10
        # Элементы матрицы A
        self.A_start_x = self.bracket_A_x + self.mx_elem_btw_bracket_x
        self.A_start_y = self.bracket_A_y + self.mx_elem_btw_bracket_y
        # Элементы матрицы B
        self.B_start_x = self.bracket_B_x + self.mx_elem_btw_bracket_x
        self.B_start_y = self.bracket_B_y + self.mx_elem_btw_bracket_y
        # Элементы матрицы X
        self.X_start_x = self.bracket_X_x + self.mx_elem_btw_bracket_x
        self.X_start_y = self.bracket_X_y + self.mx_elem_btw_bracket_y
        # Текст "Определитель матрицы" и "Чисто обусловленности"
        self.txt_d_o_x = 250
        self.txt_d_o_y_1 = 450
        self.txt_d_o_between = 20
        self.txt_d_o_size_x = 230
        self.txt_d_o_size_y = 50
        self.txt_d_o_y_2 = self.txt_d_o_y_1 + self.txt_d_o_size_y + self.txt_d_o_between
        self.txt_d_o_text_size = 20
        self.txt_d_o_elem_style = f"font-size: {self.txt_d_o_text_size}px;; color: white;"
        # Значение "Определитель матрицы" и "Чисто обусловленности"
        self.d_o_x = self.txt_d_o_x + self.txt_d_o_size_x + 10
        self.d_o_y_1 = self.txt_d_o_y_1
        self.d_o_size_x = 200
        self.d_o_size_y = self.txt_d_o_size_y
        self.d_o_y_2 = self.txt_d_o_y_2
        self.d_o_text_size = self.txt_d_o_text_size
        self.d_o_elem_style = f"font-size: {self.txt_d_o_text_size}px; background-color: 'lightGray'; color: black;"
        # Кнопка "Решить"
        self.btn_x = self.txt_d_o_x
        self.btn_y = self.txt_d_o_y_2 + self.txt_d_o_size_y + 20
        self.btn_size_x = self.d_o_x + self.d_o_size_x - self.btn_x
        self.btn_size_y = 75
        self.btn_txt_size = 24
        self.btn_style = f"font-size: {self.btn_txt_size}px; background-color: 'lightGray'; color: black;"

        self.start()

    def start(self):
        # Рамка 1
        self.frame_inp.move(self.frame_start_x, self.frame_start_y)
        self.frame_inp.setFixedSize(self.frame_size_x, self.frame_size_y)
        self.frame_inp.setStyleSheet(self.frame_style)

        # Рамка 2
        self.frame_out.move(self.frame_start_x, self.frame_start_y_2)
        self.frame_out.setFixedSize(self.frame_size_x, self.frame_size_y)
        self.frame_out.setStyleSheet(self.frame_style)

        # Текст Исходные данные / Результат
        self.lbl_inp.move(self.lbl_base_start_x, self.lbl_base_start_y)
        self.lbl_res.move(self.lbl_base_start_x, self.lbl_base_start_y_2)
        self.lbl_inp.setFixedSize(self.lbl_base_size_x, self.lbl_base_size_y)
        self.lbl_res.setFixedSize(self.lbl_base_size_x, self.lbl_base_size_y)
        self.lbl_inp.setStyleSheet(self.lbl_base_style)
        self.lbl_res.setStyleSheet(self.lbl_base_style)
        self.lbl_inp.setText("Исходные данные")
        self.lbl_res.setText("Результат")

        # Названия матриц
        # Матрица A
        self.lbl_A.move(self.lbl_A_x, self.lbl_A_y)
        self.lbl_A.setFixedSize(self.lbl_mx_size_x, self.lbl_mx_size_y)
        self.lbl_A.setStyleSheet(self.lbl_mx_style)
        self.lbl_A.setText("A = ")
        # Матрица B
        self.lbl_B.move(self.lbl_B_x, self.lbl_B_y)
        self.lbl_B.setFixedSize(self.lbl_mx_size_x, self.lbl_mx_size_y)
        self.lbl_B.setStyleSheet(self.lbl_mx_style)
        self.lbl_B.setText("B = ")
        # Матрица X
        self.lbl_X.move(self.lbl_X_x, self.lbl_X_y)
        self.lbl_X.setFixedSize(self.lbl_mx_size_x, self.lbl_mx_size_y)
        self.lbl_X.setStyleSheet(self.lbl_mx_style)
        self.lbl_X.setText("X = ")

        # Скобки для матриц
        # Скобки для матрицы A
        self.matrix_A_brackets.move(self.bracket_A_x, self.bracket_A_y)
        # Скобки для матрицы B
        self.matrix_B_brackets.move(self.bracket_B_x, self.bracket_B_y)
        # Скобки для матрицы X
        self.matrix_X_brackets.move(self.bracket_X_x, self.bracket_X_y)

        # Элементы матриц
        # Элементы матрицы A
        self.A_11.move(self.A_start_x, self.A_start_y)
        self.A_12.move(self.A_start_x + self.mx_elem_btw_x, self.A_start_y)
        self.A_13.move(self.A_start_x + self.mx_elem_btw_x * 2, self.A_start_y)
        self.A_14.move(self.A_start_x + self.mx_elem_btw_x * 3, self.A_start_y)
        self.A_21.move(self.A_start_x, self.A_start_y + self.mx_elem_btw_y)
        self.A_22.move(self.A_start_x + self.mx_elem_btw_x, self.A_start_y + self.mx_elem_btw_y)
        self.A_23.move(self.A_start_x + self.mx_elem_btw_x * 2, self.A_start_y + self.mx_elem_btw_y)
        self.A_24.move(self.A_start_x + self.mx_elem_btw_x * 3, self.A_start_y + self.mx_elem_btw_y)
        self.A_31.move(self.A_start_x, self.A_start_y + self.mx_elem_btw_y * 2)
        self.A_32.move(self.A_start_x + self.mx_elem_btw_x, self.A_start_y + self.mx_elem_btw_y * 2)
        self.A_33.move(self.A_start_x + self.mx_elem_btw_x * 2, self.A_start_y + self.mx_elem_btw_y * 2)
        self.A_34.move(self.A_start_x + self.mx_elem_btw_x * 3, self.A_start_y + self.mx_elem_btw_y * 2)
        self.A_41.move(self.A_start_x, self.A_start_y + self.mx_elem_btw_y * 3)
        self.A_42.move(self.A_start_x + self.mx_elem_btw_x, self.A_start_y + self.mx_elem_btw_y * 3)
        self.A_43.move(self.A_start_x + self.mx_elem_btw_x * 2, self.A_start_y + self.mx_elem_btw_y * 3)
        self.A_44.move(self.A_start_x + self.mx_elem_btw_x * 3, self.A_start_y + self.mx_elem_btw_y * 3)
        self.A_11.setText(str(self.matrix_a[0][0]).replace('.', ','))
        self.A_12.setText(str(self.matrix_a[0][1]).replace('.', ','))
        self.A_13.setText(str(self.matrix_a[0][2]).replace('.', ','))
        self.A_14.setText(str(self.matrix_a[0][3]).replace('.', ','))
        self.A_21.setText(str(self.matrix_a[1][0]).replace('.', ','))
        self.A_22.setText(str(self.matrix_a[1][1]).replace('.', ','))
        self.A_23.setText(str(self.matrix_a[1][2]).replace('.', ','))
        self.A_24.setText(str(self.matrix_a[1][3]).replace('.', ','))
        self.A_31.setText(str(self.matrix_a[2][0]).replace('.', ','))
        self.A_32.setText(str(self.matrix_a[2][1]).replace('.', ','))
        self.A_33.setText(str(self.matrix_a[2][2]).replace('.', ','))
        self.A_34.setText(str(self.matrix_a[2][3]).replace('.', ','))
        self.A_41.setText(str(self.matrix_a[3][0]).replace('.', ','))
        self.A_42.setText(str(self.matrix_a[3][1]).replace('.', ','))
        self.A_43.setText(str(self.matrix_a[3][2]).replace('.', ','))
        self.A_44.setText(str(self.matrix_a[3][3]).replace('.', ','))
        self.A_11.setStyleSheet(self.mx_elem_style)
        self.A_12.setStyleSheet(self.mx_elem_style)
        self.A_13.setStyleSheet(self.mx_elem_style)
        self.A_14.setStyleSheet(self.mx_elem_style)
        self.A_21.setStyleSheet(self.mx_elem_style)
        self.A_22.setStyleSheet(self.mx_elem_style)
        self.A_23.setStyleSheet(self.mx_elem_style)
        self.A_24.setStyleSheet(self.mx_elem_style)
        self.A_31.setStyleSheet(self.mx_elem_style)
        self.A_32.setStyleSheet(self.mx_elem_style)
        self.A_33.setStyleSheet(self.mx_elem_style)
        self.A_34.setStyleSheet(self.mx_elem_style)
        self.A_41.setStyleSheet(self.mx_elem_style)
        self.A_42.setStyleSheet(self.mx_elem_style)
        self.A_43.setStyleSheet(self.mx_elem_style)
        self.A_44.setStyleSheet(self.mx_elem_style)
        self.A_11.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.A_12.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.A_13.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.A_14.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.A_21.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.A_22.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.A_23.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.A_24.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.A_31.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.A_32.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.A_33.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.A_34.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.A_41.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.A_42.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.A_43.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.A_44.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.A_11.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.A_12.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.A_13.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.A_14.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.A_21.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.A_22.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.A_23.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.A_24.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.A_31.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.A_32.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.A_33.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.A_34.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.A_41.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.A_42.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.A_43.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.A_44.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        validator = QDoubleValidator()
        validator.setNotation(QDoubleValidator.Notation.StandardNotation)
        self.A_11.setValidator(validator)
        self.A_12.setValidator(validator)
        self.A_13.setValidator(validator)
        self.A_14.setValidator(validator)
        self.A_21.setValidator(validator)
        self.A_22.setValidator(validator)
        self.A_23.setValidator(validator)
        self.A_24.setValidator(validator)
        self.A_31.setValidator(validator)
        self.A_32.setValidator(validator)
        self.A_33.setValidator(validator)
        self.A_34.setValidator(validator)
        self.A_41.setValidator(validator)
        self.A_42.setValidator(validator)
        self.A_43.setValidator(validator)
        self.A_44.setValidator(validator)
        self.A_11.editingFinished.connect(self.text_changed_a_11)
        self.A_12.editingFinished.connect(self.text_changed_a_12)
        self.A_13.editingFinished.connect(self.text_changed_a_13)
        self.A_14.editingFinished.connect(self.text_changed_a_14)
        self.A_21.editingFinished.connect(self.text_changed_a_21)
        self.A_22.editingFinished.connect(self.text_changed_a_22)
        self.A_23.editingFinished.connect(self.text_changed_a_23)
        self.A_24.editingFinished.connect(self.text_changed_a_24)
        self.A_31.editingFinished.connect(self.text_changed_a_31)
        self.A_32.editingFinished.connect(self.text_changed_a_32)
        self.A_33.editingFinished.connect(self.text_changed_a_33)
        self.A_34.editingFinished.connect(self.text_changed_a_34)
        self.A_41.editingFinished.connect(self.text_changed_a_41)
        self.A_42.editingFinished.connect(self.text_changed_a_42)
        self.A_43.editingFinished.connect(self.text_changed_a_43)
        self.A_44.editingFinished.connect(self.text_changed_a_44)
        # Элементы матрицы B
        self.B_1.move(self.B_start_x, self.B_start_y)
        self.B_2.move(self.B_start_x, self.B_start_y + self.mx_elem_btw_y)
        self.B_3.move(self.B_start_x, self.B_start_y + self.mx_elem_btw_y * 2)
        self.B_4.move(self.B_start_x, self.B_start_y + self.mx_elem_btw_y * 3)
        self.B_1.setText(str(self.matrix_b[0]).replace('.', ','))
        self.B_2.setText(str(self.matrix_b[1]).replace('.', ','))
        self.B_3.setText(str(self.matrix_b[2]).replace('.', ','))
        self.B_4.setText(str(self.matrix_b[3]).replace('.', ','))
        self.B_1.setStyleSheet(self.mx_elem_style)
        self.B_2.setStyleSheet(self.mx_elem_style)
        self.B_3.setStyleSheet(self.mx_elem_style)
        self.B_4.setStyleSheet(self.mx_elem_style)
        self.B_1.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.B_2.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.B_3.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.B_4.setFixedSize(self.mx_elem_size_x, self.mx_elem_size_y)
        self.B_1.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.B_2.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.B_3.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.B_4.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.B_1.setValidator(validator)
        self.B_2.setValidator(validator)
        self.B_3.setValidator(validator)
        self.B_4.setValidator(validator)
        self.B_1.editingFinished.connect(self.text_changed_b_1)
        self.B_2.editingFinished.connect(self.text_changed_b_2)
        self.B_3.editingFinished.connect(self.text_changed_b_3)
        self.B_4.editingFinished.connect(self.text_changed_b_4)
        # Элементы матрицы X
        self.X_1.move(self.X_start_x, self.X_start_y)
        self.X_2.move(self.X_start_x, self.X_start_y + self.mx_elem_btw_y)
        self.X_3.move(self.X_start_x, self.X_start_y + self.mx_elem_btw_y * 2)
        self.X_4.move(self.X_start_x, self.X_start_y + self.mx_elem_btw_y * 3)
        self.X_1.setText("-")
        self.X_2.setText("-")
        self.X_3.setText("-")
        self.X_4.setText("-")
        self.X_1.setStyleSheet(self.mx_elem_style_x)
        self.X_2.setStyleSheet(self.mx_elem_style_x)
        self.X_3.setStyleSheet(self.mx_elem_style_x)
        self.X_4.setStyleSheet(self.mx_elem_style_x)
        self.X_1.setFixedSize(self.mx_elem_size_x + 30, self.mx_elem_size_y)
        self.X_2.setFixedSize(self.mx_elem_size_x + 30, self.mx_elem_size_y)
        self.X_3.setFixedSize(self.mx_elem_size_x + 30, self.mx_elem_size_y)
        self.X_4.setFixedSize(self.mx_elem_size_x + 30, self.mx_elem_size_y)
        self.X_1.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.X_2.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.X_3.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.X_4.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        # Текст "Определитель матрицы" и "Чисто обусловленности"
        self.txt_determ.move(self.txt_d_o_x, self.txt_d_o_y_1)
        self.txt_obysl.move(self.txt_d_o_x, self.txt_d_o_y_2)
        self.txt_determ.setText("Определитель матрицы:")
        self.txt_obysl.setText("Число обусловленности:")
        self.txt_determ.setStyleSheet(self.txt_d_o_elem_style)
        self.txt_obysl.setStyleSheet(self.txt_d_o_elem_style)
        self.txt_determ.setFixedSize(self.txt_d_o_size_x, self.txt_d_o_size_y)
        self.txt_obysl.setFixedSize(self.txt_d_o_size_x, self.txt_d_o_size_y)
        self.txt_determ.setAlignment(Qt.AlignmentFlag.AlignVCenter)
        self.txt_obysl.setAlignment(Qt.AlignmentFlag.AlignVCenter)
        # Значение "Определитель матрицы" и "Чисто обусловленности"
        self.res_determ.move(self.d_o_x, self.d_o_y_1)
        self.res_obysl.move(self.d_o_x, self.d_o_y_2)
        self.res_determ.setText("-------------")
        self.res_obysl.setText("-------------")
        self.res_determ.setStyleSheet(self.d_o_elem_style)
        self.res_obysl.setStyleSheet(self.d_o_elem_style)
        self.res_determ.setFixedSize(self.d_o_size_x, self.d_o_size_y)
        self.res_obysl.setFixedSize(self.d_o_size_x, self.d_o_size_y)
        self.res_determ.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        self.res_obysl.setAlignment(Qt.AlignmentFlag.AlignHCenter | Qt.AlignmentFlag.AlignVCenter)
        # Кнопка "Решить"
        self.btn.move(self.btn_x, self.btn_y)
        self.btn.setText("Решить")
        self.btn.setStyleSheet(self.btn_style)
        self.btn.setFixedSize(self.btn_size_x, self.btn_size_y)
        self.btn.clicked.connect(self.start_solution)

    def start_solution(self):
        self.text_changed_all()
        self.solve_with_iterations(self.matrix_a, self.matrix_b)
        self.text_changed_all()
        self.det(deepcopy(self.matrix_a))
        self.text_changed_all()
        self.calculate_cond(deepcopy(self.matrix_a))

        self.change_res()

    def change_res(self):
        self.X_1.setText(str(round(float(self.results[0]), self.rnd)).replace('.', ','))
        self.X_2.setText(str(round(float(self.results[1]), self.rnd)).replace('.', ','))
        self.X_3.setText(str(round(float(self.results[2]), self.rnd)).replace('.', ','))
        self.X_4.setText(str(round(float(self.results[3]), self.rnd)).replace('.', ','))
        self.res_determ.setText(str(round(float(self.determ), self.rnd)).replace('.', ','))
        self.res_obysl.setText(str(round(float(self.cond), self.rnd)).replace('.', ','))

    def difference_between_vectors(self, x1, x2):
        if len(x1) != len(x2):
            raise ValueError("Размеры векторов не совпадают.")
        diff = 0
        for i in range(len(x1)):
            diff += abs(x1[i] - x2[i])
        return diff

    def vectors_sum(self, x1, x2):
        if len(x1) != len(x2):
            raise ValueError("Размеры векторов не совпадают.")
        res = [x1[i] + x2[i] for i in range(len(x1))]
        return res

    def vectors_dif(self, x1, x2):
        if len(x1) != len(x2):
            raise ValueError("Размеры векторов не совпадают.")
        res = [x1[i] - x2[i] for i in range(len(x1))]
        return res

    def vector_num_mul(self, v, n):
        return [round(t * n, 9) for t in v]

    def matrix_vector_multiply(self, A, v):
        if len(A[0]) != len(v):
            raise ValueError("Размеры матрицы и вектора не позволяют их перемножить.")

        result = [sum(A[i][j] * v[j] for j in range(len(v))) for i in range(len(A))]
        return result

    def solve_with_iterations(self, matrix, vec_b):
        matrix, vec_b = self.gauss(matrix, vec_b)

        alpha = [[0] * len(matrix[0]) for _ in range(len(matrix))]
        beta = [0] * len(vec_b)
        for i in range(len(matrix)):
            beta[i] = round(vec_b[i] / matrix[i][i], 10)

        for i in range(len(matrix)):
            for j in range(len(matrix[0])):
                if i != j:
                    alpha[i][j] = - round(matrix[i][j] / matrix[i][i], 10)
                else:
                    alpha[i][j] = 0

        x = [0] * len(matrix)
        inaccuracy = float("inf")
        while inaccuracy >= self.eps:
            x_prev = deepcopy(x)
            x = self.vectors_sum(beta, self.matrix_vector_multiply(alpha, x))
            inaccuracy = self.difference_between_vectors(x, x_prev)
        self.results[0] = x[0]
        self.results[1] = x[1]
        self.results[2] = x[2]
        self.results[3] = x[3]

    def gauss(self, matrix, vec_b):
        if len(matrix) != len(vec_b):
            raise ValueError("Размеры матрицы и вектора не совпадают.")

        for i in range(len(matrix)):
            matrix[i] += [vec_b[i]]

        for i in range(len(matrix)):
            max_el = abs(matrix[i][i])
            max_row = i
            for k in range(i + 1, len(matrix)):
                if abs(matrix[k][i]) > max_el:
                    max_el = abs(matrix[k][i])
                    max_row = k
            matrix[i], matrix[max_row] = matrix[max_row], matrix[i]

            mat_str = [x for x in matrix[i]]
            for j in range(i + 1, len(matrix)):
                if matrix[i][i] != 0:
                    cur = self.vector_num_mul(mat_str, matrix[j][i] / matrix[i][i])
                    matrix[j] = self.vectors_dif(matrix[j], cur)

        for i in range(len(matrix)):
            vec_b[i] = matrix[i].pop()

        return matrix, vec_b

    def gauss_jordan(self, matrix):
        n = len(matrix)

        E = [[0] * len(matrix[0]) for _ in range(n)]
        for i in range(len(E)):
            E[i][i] = 1

        for i in range(n):
            matrix[i] += E[i]

        for i in range(n):
            max_el = abs(matrix[i][i])
            max_row = i
            for k in range(i + 1, n):
                if abs(matrix[k][i]) > max_el:
                    max_el = abs(matrix[k][i])
                    max_row = k

            matrix[i], matrix[max_row] = matrix[max_row], matrix[i]

            pivot = matrix[i][i]
            if pivot == 0:
                raise ValueError("Матрица необратима.")
            matrix[i] = [element / pivot for element in matrix[i]]

            for j in range(n):
                if j != i:
                    factor = matrix[j][i]
                    matrix[j] = [matrix[j][k] - factor * matrix[i][k] for k in range(2 * n)]

        inverse_matrix = [row[n:] for row in matrix]
        return inverse_matrix

    def calculate_cond(self, matrix):
        inverse_mat = self.gauss_jordan(deepcopy(matrix))
        a_norm = float("-inf")
        for row in matrix:
            s = 0
            for item in row:
                s += abs(item)
            a_norm = max(a_norm, s)

        inverse_norm = float("-inf")
        for row in inverse_mat:
            s = 0
            for item in row:
                s += abs(item)
            inverse_norm = max(inverse_norm, s)
        cond = a_norm * inverse_norm
        self.cond = round(cond, self.rnd)

    def det(self, matrix):
        matrix, v = self.gauss(matrix, [0] * len(matrix))
        res = 1
        for i in range(len(matrix)):
            res *= matrix[i][i]
        self.determ = round(res, self.rnd)

    def text_changed_all(self):
        self.matrix_a[0][0] = float(self.A_11.text().replace(',', '.'))
        self.matrix_a[0][1] = float(self.A_12.text().replace(',', '.'))
        self.matrix_a[0][2] = float(self.A_13.text().replace(',', '.'))
        self.matrix_a[0][3] = float(self.A_14.text().replace(',', '.'))

        self.matrix_a[1][0] = float(self.A_21.text().replace(',', '.'))
        self.matrix_a[1][1] = float(self.A_22.text().replace(',', '.'))
        self.matrix_a[1][2] = float(self.A_23.text().replace(',', '.'))
        self.matrix_a[1][3] = float(self.A_24.text().replace(',', '.'))

        self.matrix_a[2][0] = float(self.A_31.text().replace(',', '.'))
        self.matrix_a[2][1] = float(self.A_32.text().replace(',', '.'))
        self.matrix_a[2][2] = float(self.A_33.text().replace(',', '.'))
        self.matrix_a[2][3] = float(self.A_34.text().replace(',', '.'))

        self.matrix_a[3][0] = float(self.A_41.text().replace(',', '.'))
        self.matrix_a[3][1] = float(self.A_42.text().replace(',', '.'))
        self.matrix_a[3][2] = float(self.A_43.text().replace(',', '.'))
        self.matrix_a[3][3] = float(self.A_44.text().replace(',', '.'))

        self.matrix_b[0] = float(self.B_1.text().replace(',', '.'))
        self.matrix_b[1] = float(self.B_2.text().replace(',', '.'))
        self.matrix_b[2] = float(self.B_3.text().replace(',', '.'))
        self.matrix_b[3] = float(self.B_4.text().replace(',', '.'))

    def text_changed_a_11(self):
        self.matrix_a[0][0] = float(self.A_11.text().replace(',', '.'))

    def text_changed_a_12(self):
        self.matrix_a[0][1] = float(self.A_12.text().replace(',', '.'))

    def text_changed_a_13(self):
        self.matrix_a[0][2] = float(self.A_13.text().replace(',', '.'))

    def text_changed_a_14(self):
        self.matrix_a[0][3] = float(self.A_14.text().replace(',', '.'))

    def text_changed_a_21(self):
        self.matrix_a[1][0] = float(self.A_21.text().replace(',', '.'))

    def text_changed_a_22(self):
        self.matrix_a[1][1] = float(self.A_22.text().replace(',', '.'))

    def text_changed_a_23(self):
        self.matrix_a[1][2] = float(self.A_23.text().replace(',', '.'))

    def text_changed_a_24(self):
        self.matrix_a[1][3] = float(self.A_24.text().replace(',', '.'))

    def text_changed_a_31(self):
        self.matrix_a[2][0] = float(self.A_31.text().replace(',', '.'))

    def text_changed_a_32(self):
        self.matrix_a[2][1] = float(self.A_32.text().replace(',', '.'))

    def text_changed_a_33(self):
        self.matrix_a[2][2] = float(self.A_33.text().replace(',', '.'))

    def text_changed_a_34(self):
        self.matrix_a[2][3] = float(self.A_34.text().replace(',', '.'))

    def text_changed_a_41(self):
        self.matrix_a[3][0] = float(self.A_41.text().replace(',', '.'))

    def text_changed_a_42(self):
        self.matrix_a[3][1] = float(self.A_42.text().replace(',', '.'))

    def text_changed_a_43(self):
        self.matrix_a[3][2] = float(self.A_43.text().replace(',', '.'))

    def text_changed_a_44(self):
        self.matrix_a[3][3] = float(self.A_44.text().replace(',', '.'))

    def text_changed_b_1(self):
        self.matrix_b[0] = float(self.B_1.text().replace(',', '.'))

    def text_changed_b_2(self):
        self.matrix_b[1] = float(self.B_2.text().replace(',', '.'))

    def text_changed_b_3(self):
        self.matrix_b[2] = float(self.B_3.text().replace(',', '.'))

    def text_changed_b_4(self):
        self.matrix_b[3] = float(self.B_4.text().replace(',', '.'))


if __name__ == "__main__":
    app = QApplication(sys.argv)
    main_window = MainWindow()
    main_window.show()

    sys.exit(app.exec())
