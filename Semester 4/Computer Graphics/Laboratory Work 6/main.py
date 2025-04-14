import sys
from PyQt6.QtWidgets import (
    QApplication, QMainWindow, QWidget, QVBoxLayout, QHBoxLayout,
    QPushButton, QLabel, QSpinBox, QFormLayout, QGroupBox, QCheckBox
)
from PyQt6.QtGui import QPainter, QPen, QColor, QFont
from PyQt6.QtCore import Qt, QPoint


class DDAWidget(QWidget):
    def __init__(self):
        super().__init__()
        self.points = []
        self.setMinimumSize(800, 800)  # Увеличим минимальный размер
        self.pen_color = QColor(0, 0, 255)  # Синий цвет по умолчанию
        self.setAutoFillBackground(True)
        palette = self.palette()
        palette.setColor(self.backgroundRole(), Qt.GlobalColor.white)
        self.setPalette(palette)
        self.font = QFont("Arial", 10)
        self.scale = 100  # 1 единица = 100 пикселей (увеличили масштаб)
        self.show_steps = False  # Показывать шаги алгоритма
        self.step_points = []  # Точки шагов алгоритма

    def add_point(self, x, y):
        self.points.append(QPoint(x, y))
        if len(self.points) >= 2:
            self.calculate_dda_steps()
            self.update()

    def clear_points(self):
        self.points = []
        self.step_points = []
        self.update()

    def calculate_dda_steps(self):
        if len(self.points) < 2:
            return

        self.step_points = []
        x1, y1 = self.points[0].x(), self.points[0].y()
        x2, y2 = self.points[1].x(), self.points[1].y()

        # Переводим координаты в математическую систему
        w, h = self.width(), self.height()
        x1 = (x1 - w // 2) / self.scale
        y1 = (h // 2 - y1) / self.scale
        x2 = (x2 - w // 2) / self.scale
        y2 = (h // 2 - y2) / self.scale

        dx = x2 - x1
        dy = y2 - y1

        steps = max(abs(dx), abs(dy))

        if steps == 0:
            return

        x_increment = dx / steps
        y_increment = dy / steps

        x, y = x1, y1

        for _ in range(int(steps) + 1):
            # Переводим координаты обратно в систему Qt
            px = round(x * self.scale) + w // 2
            py = h // 2 - round(y * self.scale)
            self.step_points.append(QPoint(px, py))
            x += x_increment
            y += y_increment

    def paintEvent(self, event):
        painter = QPainter(self)
        painter.setRenderHint(QPainter.RenderHint.Antialiasing)
        painter.setFont(self.font)

        # Заливаем фон белым цветом
        painter.fillRect(self.rect(), Qt.GlobalColor.white)

        w, h = self.width(), self.height()
        center_x, center_y = w // 2, h // 2

        # Рисуем оси координат
        painter.setPen(QPen(Qt.GlobalColor.black, 2))  # Толщину увеличили
        painter.drawLine(0, center_y, w, center_y)  # Ось X
        painter.drawLine(center_x, 0, center_x, h)  # Ось Y

        # Подписи осей
        painter.drawText(w - 30, center_y - 15, "X")
        painter.drawText(center_x + 15, 25, "Y")

        # Обозначения на осях (целые числа)
        painter.setPen(QPen(Qt.GlobalColor.darkGray, 1))
        for x in range(-10, 11):
            if x == 0:
                continue
            px = center_x + x * self.scale
            painter.drawLine(px, center_y - 8, px, center_y + 8)
            painter.drawText(px - 10, center_y + 25, str(x))

        for y in range(-10, 11):
            if y == 0:
                continue
            py = center_y - y * self.scale
            painter.drawLine(center_x - 8, py, center_x + 8, py)
            painter.drawText(center_x + 15, py + 5, str(y))

        # Рисуем сетку (пунктирные линии)
        pen = QPen(Qt.GlobalColor.lightGray, 1, Qt.PenStyle.DotLine)
        painter.setPen(pen)
        for x in range(-10, 11):
            px = center_x + x * self.scale
            painter.drawLine(px, 0, px, h)
        for y in range(-10, 11):
            py = center_y - y * self.scale
            painter.drawLine(0, py, w, py)

        if len(self.points) < 2:
            return

        # Рисуем идеальную прямую (тонкую серую линию)
        painter.setPen(QPen(Qt.GlobalColor.gray, 1))
        painter.drawLine(self.points[0], self.points[1])

        # Рисуем отрезок с помощью алгоритма ЦДА
        painter.setPen(QPen(self.pen_color, 3))  # Толщину увеличили
        for point in self.step_points:
            painter.drawPoint(point)

        # Если включена визуализация шагов
        if self.show_steps and len(self.step_points) > 1:
            painter.setPen(QPen(Qt.GlobalColor.red, 1))
            for i in range(len(self.step_points) - 1):
                painter.drawLine(self.step_points[i], self.step_points[i + 1])
                # Подписываем шаги
                painter.drawText(self.step_points[i].x() + 5,
                                 self.step_points[i].y() - 5,
                                 str(i + 1))


class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        # self.setWindowTitle("Алгоритм ЦДА (масштаб 1:100)")
        self.setWindowTitle("Алгоритм ЦДА")
        self.setGeometry(100, 100, 1000, 800)  # Увеличили размер окна

        # Создаем центральный виджет и layout
        central_widget = QWidget()
        self.setCentralWidget(central_widget)
        main_layout = QHBoxLayout(central_widget)

        # Создаем виджет для рисования
        self.dda_widget = DDAWidget()
        main_layout.addWidget(self.dda_widget, stretch=1)

        # Создаем панель управления
        control_panel = QGroupBox("Управление")
        control_layout = QVBoxLayout()

        # Поля для ввода координат (целочисленные)
        form_layout = QFormLayout()

        self.x1_spin = QSpinBox()
        self.x1_spin.setRange(-10, 10)
        self.x1_spin.setValue(0)

        self.y1_spin = QSpinBox()
        self.y1_spin.setRange(-10, 10)
        self.y1_spin.setValue(0)

        self.x2_spin = QSpinBox()
        self.x2_spin.setRange(-10, 10)
        self.x2_spin.setValue(5)

        self.y2_spin = QSpinBox()
        self.y2_spin.setRange(-10, 10)
        self.y2_spin.setValue(3)

        form_layout.addRow("X1:", self.x1_spin)
        form_layout.addRow("Y1:", self.y1_spin)
        form_layout.addRow("X2:", self.x2_spin)
        form_layout.addRow("Y2:", self.y2_spin)

        control_layout.addLayout(form_layout)

        # Чекбокс для показа шагов
        self.steps_check = QCheckBox("Показывать шаги алгоритма")
        self.steps_check.stateChanged.connect(self.toggle_steps)
        control_layout.addWidget(self.steps_check)

        # Кнопки управления
        self.draw_btn = QPushButton("Нарисовать отрезок")
        self.draw_btn.clicked.connect(self.draw_line)
        control_layout.addWidget(self.draw_btn)

        self.clear_btn = QPushButton("Очистить")
        self.clear_btn.clicked.connect(self.clear_canvas)
        control_layout.addWidget(self.clear_btn)

        control_layout.addStretch()
        control_panel.setLayout(control_layout)
        main_layout.addWidget(control_panel, stretch=0)

    def draw_line(self):
        # Получаем значения координат
        x1 = self.x1_spin.value()
        y1 = self.y1_spin.value()
        x2 = self.x2_spin.value()
        y2 = self.y2_spin.value()

        # Переводим математические координаты в экранные
        w, h = self.dda_widget.width(), self.dda_widget.height()
        scale = self.dda_widget.scale

        px1 = w // 2 + x1 * scale
        py1 = h // 2 - y1 * scale

        px2 = w // 2 + x2 * scale
        py2 = h // 2 - y2 * scale

        self.dda_widget.clear_points()
        self.dda_widget.add_point(px1, py1)
        self.dda_widget.add_point(px2, py2)

    def toggle_steps(self, state):
        self.dda_widget.show_steps = (state == Qt.CheckState.Checked.value)
        self.dda_widget.update()

    def clear_canvas(self):
        self.dda_widget.clear_points()


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = MainWindow()
    window.show()
    sys.exit(app.exec())