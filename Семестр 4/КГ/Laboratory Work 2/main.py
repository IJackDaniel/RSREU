import sys

from PyQt6.QtWidgets import QApplication, QMainWindow, QPushButton, QLabel, QVBoxLayout, QHBoxLayout, QWidget, \
    QFileDialog
from PyQt6.QtGui import QPixmap
from PyQt6.QtCore import Qt


class ImageViewer(QMainWindow):
    def __init__(self):
        super().__init__()

        self.setWindowTitle("BMP Image Viewer")

        # Создаем виджет для отображения изображения
        self.image_label = QLabel(self)
        self.image_label.setAlignment(Qt.AlignmentFlag.AlignCenter)

        # Создаем кнопки масштаба
        self.scale_buttons = {}
        scales = [0.5, 1.0, 1.5, 2.0]
        for scale in scales:
            button = QPushButton(f'{int(scale * 100)}%', self)
            button.setCheckable(True)
            button.clicked.connect(lambda checked, s=scale: self.set_scale(s))
            self.scale_buttons[scale] = button

        # Установим кнопку масштаба по умолчанию
        self.scale_buttons[1.0].setChecked(True)

        # Главное меню
        load_button = QPushButton("Загрузить BMP файл", self)
        load_button.clicked.connect(self.load_image)

        # Компоновка
        layout = QVBoxLayout()
        scale_layout = QHBoxLayout()
        for button in self.scale_buttons.values():
            scale_layout.addWidget(button)

        layout.addWidget(load_button)
        layout.addLayout(scale_layout)
        layout.addWidget(self.image_label)

        container = QWidget()
        container.setLayout(layout)
        self.setCentralWidget(container)

        self.image = None  # Для хранения загруженного изображения
        self.scale = 1.0  # Начальный масштаб
        self.standart_size = 400

    def load_image(self):
        file_name = QFileDialog.getOpenFileName(self, "Выберите BMP файл", r"C:\images\"", "Images (*.bmp)")

        if file_name:
            try:
                self.image = QPixmap(file_name[0])

                if self.image.isNull():  # Проверяем, было ли загружено изображение
                    raise ValueError("Ошибка: не удалось загрузить изображение.")
                else:
                    self.scale = 1.0
                    self.scale_buttons[1.0].setChecked(True)
                    self.update_image()
            except Exception as e:
                self.image_label.setText(f"Ошибка: {str(e)}")
        else:
            self.image_label.setText("Нет выбранного файла.")

    def set_scale(self, new_scale):
        # Отключаем все кнопки масштаба и устанавливаем кнопку по нажатию
        for scale, button in self.scale_buttons.items():
            button.setChecked(scale == new_scale)

        self.scale = new_scale
        self.update_image()

    def update_image(self):
        if self.image:
            scaled_image = self.image.scaled(int(self.standart_size * self.scale), int(self.standart_size * self.scale), Qt.AspectRatioMode.KeepAspectRatio)
            self.image_label.setPixmap(scaled_image)
            self.resize(scaled_image.size())


if __name__ == "__main__":
    app = QApplication(sys.argv)

    # Обработка ошибок при запуске
    try:
        viewer = ImageViewer()
        viewer.resize(800, 600)
        viewer.show()
        sys.exit(app.exec())
    except Exception as e:
        print(f"Произошла ошибка: {str(e)}")
        sys.exit(1)
