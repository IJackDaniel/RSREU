import sys
from PyQt6.QtWidgets import (QApplication, QMainWindow, QWidget, QVBoxLayout,
                             QHBoxLayout, QLabel, QLineEdit, QPushButton,
                             QGraphicsView, QGraphicsScene, QMessageBox)
from PyQt6.QtCore import Qt, QPointF, QTimer
from PyQt6.QtGui import QPen, QColor, QBrush, QPainter, QFont


class DDAVisualizer(QMainWindow):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("Визуализация алгоритма ЦДА с декартовой системой координат")
        self.setGeometry(100, 100, 800, 600)

        # Основные виджеты
        self.central_widget = QWidget()
        self.setCentralWidget(self.central_widget)

        # Создаем графическую сцену
        self.scene = QGraphicsScene()
        self.view = QGraphicsView(self.scene)
        self.view.setRenderHint(QPainter.RenderHint.Antialiasing)
        self.view.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.view.setBackgroundBrush(QBrush(Qt.GlobalColor.white))

        # Элементы управления
        self.x1_input = QLineEdit("10")
        self.y1_input = QLineEdit("5")
        self.x2_input = QLineEdit("-15")
        self.y2_input = QLineEdit("-10")
        self.draw_button = QPushButton("Построить отрезок")
        self.draw_button.clicked.connect(self.draw_line)

        # Разметка
        control_layout = QHBoxLayout()
        control_layout.addWidget(QLabel("x1:"))
        control_layout.addWidget(self.x1_input)
        control_layout.addWidget(QLabel("y1:"))
        control_layout.addWidget(self.y1_input)
        control_layout.addWidget(QLabel("x2:"))
        control_layout.addWidget(self.x2_input)
        control_layout.addWidget(QLabel("y2:"))
        control_layout.addWidget(self.y2_input)
        control_layout.addWidget(self.draw_button)

        main_layout = QVBoxLayout()
        main_layout.addLayout(control_layout)
        main_layout.addWidget(self.view)

        self.central_widget.setLayout(main_layout)

        # Настройка сцены
        self.cell_size = 20
        self.prev_point = None

        # Используем таймер для отложенной инициализации
        QTimer.singleShot(100, self.initialize_scene)

    def initialize_scene(self):
        """Инициализация сцены после отображения окна"""
        self.origin_x = self.view.width() // 2
        self.origin_y = self.view.height() // 2
        self.draw_axes()

    def resizeEvent(self, event):
        """Обработчик изменения размера окна"""
        super().resizeEvent(event)
        self.initialize_scene()

    def draw_axes(self):
        """Рисуем декартову систему координат с осями X и Y"""
        self.scene.clear()
        self.prev_point = None

        width = self.view.width()
        height = self.view.height()

        # Обновляем центр координат
        self.origin_x = width // 2
        self.origin_y = height // 2

        # Рисуем оси координат
        axis_pen = QPen(QColor(0, 0, 0), 2)
        self.scene.addLine(0, self.origin_y, width, self.origin_y, axis_pen)  # Ось X
        self.scene.addLine(self.origin_x, 0, self.origin_x, height, axis_pen)  # Ось Y

        # Стрелки на осях
        arrow_size = 10
        # Стрелка для оси X
        self.scene.addLine(width - arrow_size, self.origin_y - arrow_size,
                           width, self.origin_y, axis_pen)
        self.scene.addLine(width - arrow_size, self.origin_y + arrow_size,
                           width, self.origin_y, axis_pen)
        # Стрелка для оси Y
        self.scene.addLine(self.origin_x - arrow_size, arrow_size,
                           self.origin_x, 0, axis_pen)
        self.scene.addLine(self.origin_x + arrow_size, arrow_size,
                           self.origin_x, 0, axis_pen)

        # Подписи осей
        font = QFont("Arial", 10)
        x_label = self.scene.addText("X", font)
        x_label.setPos(width - 30, self.origin_y - 30)
        y_label = self.scene.addText("Y", font)
        y_label.setPos(self.origin_x + 10, 10)

        # Рисуем сетку
        grid_pen = QPen(QColor(200, 200, 200))

        # Вертикальные линии (слева от оси Y)
        x = self.origin_x - self.cell_size
        while x > 0:
            self.scene.addLine(x, 0, x, height, grid_pen)
            x -= self.cell_size

        # Вертикальные линии (справа от оси Y)
        x = self.origin_x + self.cell_size
        while x < width:
            self.scene.addLine(x, 0, x, height, grid_pen)
            x += self.cell_size

        # Горизонтальные линии (выше оси X)
        y = self.origin_y - self.cell_size
        while y > 0:
            self.scene.addLine(0, y, width, y, grid_pen)
            y -= self.cell_size

        # Горизонтальные линии (ниже оси X)
        y = self.origin_y + self.cell_size
        while y < height:
            self.scene.addLine(0, y, width, y, grid_pen)
            y += self.cell_size

        # Подписи делений на осях
        font = QFont("Arial", 8)

        # Подписи по оси X (положительные)
        x_val = 1
        x = self.origin_x + self.cell_size
        while x < width:
            label = self.scene.addText(str(x_val), font)
            label.setPos(x - 5, self.origin_y + 5)
            x_val += 1
            x += self.cell_size

        # Подписи по оси X (отрицательные)
        x_val = -1
        x = self.origin_x - self.cell_size
        while x > 0:
            label = self.scene.addText(str(x_val), font)
            label.setPos(x - 5, self.origin_y + 5)
            x_val -= 1
            x -= self.cell_size

        # Подписи по оси Y (положительные)
        y_val = 1
        y = self.origin_y - self.cell_size
        while y > 0:
            label = self.scene.addText(str(y_val), font)
            label.setPos(self.origin_x + 5, y - 8)
            y_val += 1
            y -= self.cell_size

        # Подписи по оси Y (отрицательные)
        y_val = -1
        y = self.origin_y + self.cell_size
        while y < height:
            label = self.scene.addText(str(y_val), font)
            label.setPos(self.origin_x + 5, y - 8)
            y_val -= 1
            y += self.cell_size

    def draw_line(self):
        """Реализация алгоритма ЦДА с визуализацией"""
        try:
            x1 = int(self.x1_input.text())
            y1 = int(self.y1_input.text())
            x2 = int(self.x2_input.text())
            y2 = int(self.y2_input.text())
        except ValueError:
            QMessageBox.warning(self, "Ошибка", "Введите целочисленные координаты")
            return

        self.draw_axes()

        # Вычисляем разницы координат
        dx = x2 - x1
        dy = y2 - y1

        # Определяем количество шагов
        steps = max(abs(dx), abs(dy))

        if steps == 0:
            # Точка
            self.plot_point(x1, y1, True)
            return

        # Вычисляем приращения
        x_increment = dx / steps
        y_increment = dy / steps

        # Начальная точка
        x = x1
        y = y1

        # Рисуем начальную точку
        self.plot_point(round(x), round(y), True)

        # Процесс растеризации
        for _ in range(steps):
            x += x_increment
            y += y_increment

            # Округляем до ближайших целых координат
            x_rounded = round(x)
            y_rounded = round(y)

            # Рисуем промежуточную точку и соединяем с предыдущей
            self.plot_point(x_rounded, y_rounded, False)

            # Показываем процесс построения
            QApplication.processEvents()

    def plot_point(self, x, y, is_start):
        """Рисуем точку на сцене и соединяем с предыдущей"""
        # Преобразуем координаты в экранные
        screen_x = self.origin_x + x * self.cell_size
        screen_y = self.origin_y - y * self.cell_size  # Инвертируем Y

        current_point = QPointF(screen_x, screen_y)

        if is_start:
            color = QColor(255, 0, 0)  # Красный для начальной точки
            size = 8
            self.prev_point = current_point
        else:
            color = QColor(0, 0, 255)  # Синий для промежуточных точек
            size = 6

            # Соединяем с предыдущей точкой
            if self.prev_point:
                line_pen = QPen(QColor(0, 128, 0), 2)  # Зеленая линия
                self.scene.addLine(self.prev_point.x(), self.prev_point.y(),
                                   current_point.x(), current_point.y(), line_pen)

            self.prev_point = current_point

        # Отрисовка точки
        pen = QPen(color)
        brush = QBrush(color)
        self.scene.addEllipse(screen_x - size // 2, screen_y - size // 2,
                              size, size, pen, brush)

        # Подпись координат для начальной точки
        if is_start:
            text = self.scene.addText(f"({x}, {y})")
            text.setPos(screen_x + 10, screen_y - 20)
            text.setDefaultTextColor(color)


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = DDAVisualizer()
    window.show()
    sys.exit(app.exec())
