import sys
from PyQt6.QtWidgets import (
    QApplication, QMainWindow, QWidget, QVBoxLayout, QHBoxLayout,
    QLabel, QPushButton, QSlider, QFileDialog, QMessageBox
)
from PyQt6.QtGui import QPixmap, QImage, QImageReader
from PyQt6.QtCore import Qt
from PIL import Image, ImageEnhance, ImageFilter


class PhotoEditor(QMainWindow):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("Фоторедактор")
        self.setGeometry(100, 100, 800, 600)

        # Основные переменные
        self.original_image = None
        self.current_image = None
        self.filename = None

        # Создаем интерфейс
        self.init_ui()

    def init_ui(self):
        # Главный виджет и layout
        central_widget = QWidget()
        self.setCentralWidget(central_widget)
        main_layout = QHBoxLayout(central_widget)

        # Панель управления
        control_panel = QWidget()
        control_layout = QVBoxLayout(control_panel)
        control_layout.setAlignment(Qt.AlignmentFlag.AlignTop)

        # Кнопка открытия файла
        self.open_btn = QPushButton("Открыть изображение")
        self.open_btn.clicked.connect(self.open_image)
        control_layout.addWidget(self.open_btn)

        # Кнопка сохранения
        self.save_btn = QPushButton("Сохранить изображение")
        self.save_btn.clicked.connect(self.save_image)
        self.save_btn.setEnabled(False)
        control_layout.addWidget(self.save_btn)

        # Слайдеры для регулировки параметров
        self.brightness_slider = self.create_slider("Яркость", -100, 100, 0, self.adjust_image)
        self.contrast_slider = self.create_slider("Контрастность", -100, 100, 0, self.adjust_image)
        self.saturation_slider = self.create_slider("Насыщенность", -100, 100, 0, self.adjust_image)
        self.sharpness_slider = self.create_slider("Резкость", 0, 200, 100, self.adjust_sharpness)

        control_layout.addWidget(self.brightness_slider['label'])
        control_layout.addWidget(self.brightness_slider['slider'])
        control_layout.addWidget(self.contrast_slider['label'])
        control_layout.addWidget(self.contrast_slider['slider'])
        control_layout.addWidget(self.saturation_slider['label'])
        control_layout.addWidget(self.saturation_slider['slider'])
        control_layout.addWidget(self.sharpness_slider['label'])
        control_layout.addWidget(self.sharpness_slider['slider'])

        # Кнопка сброса
        reset_btn = QPushButton("Сбросить изменения")
        reset_btn.clicked.connect(self.reset_image)
        control_layout.addWidget(reset_btn)

        # Область отображения изображения
        self.image_label = QLabel()
        self.image_label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.image_label.setStyleSheet("QLabel { background-color : white; }")

        # Добавляем панель управления и изображение в главный layout
        main_layout.addWidget(control_panel, stretch=1)
        main_layout.addWidget(self.image_label, stretch=4)

    def create_slider(self, text, min_val, max_val, default_val, callback):
        slider_data = {}
        slider_data['label'] = QLabel(f"{text}: {default_val}")
        slider_data['slider'] = QSlider(Qt.Orientation.Horizontal)
        slider_data['slider'].setRange(min_val, max_val)
        slider_data['slider'].setValue(default_val)
        slider_data['slider'].valueChanged.connect(
            lambda value, label=slider_data['label'], t=text: label.setText(f"{t}: {value}")
        )
        slider_data['slider'].valueChanged.connect(callback)
        return slider_data

    def open_image(self):
        # Открываем диалог выбора файла
        filename, _ = QFileDialog.getOpenFileName(
            self, "Открыть изображение", "",
            "Изображения (*.png *.jpg *.jpeg)"
        )

        if filename:
            try:
                # Читаем изображение с помощью PIL
                self.original_image = Image.open(filename)
                self.current_image = self.original_image.copy()
                self.filename = filename

                # Отображаем изображение
                self.display_image()

                # Активируем кнопку сохранения
                self.save_btn.setEnabled(True)

                # Сбрасываем слайдеры
                self.reset_sliders()

            except Exception as e:
                QMessageBox.critical(self, "Ошибка", f"Не удалось открыть изображение:\n{str(e)}")

    def save_image(self):
        if self.current_image:
            filename, _ = QFileDialog.getSaveFileName(
                self, "Сохранить изображение", "",
                "PNG (*.png);;JPEG (*.jpg *.jpeg)"
            )

            if filename:
                try:
                    self.current_image.save(filename)
                    QMessageBox.information(self, "Успех", "Изображение успешно сохранено!")
                except Exception as e:
                    QMessageBox.critical(self, "Ошибка", f"Не удалось сохранить изображение:\n{str(e)}")

    def display_image(self):
        if self.current_image:
            # Конвертируем PIL.Image в QPixmap
            image = self.current_image.convert("RGB")
            data = image.tobytes("raw", "RGB")
            qimage = QImage(data, image.size[0], image.size[1], QImage.Format.Format_RGB888)
            pixmap = QPixmap.fromImage(qimage)

            # Масштабируем изображение под размер виджета
            scaled_pixmap = pixmap.scaled(
                self.image_label.size(),
                Qt.AspectRatioMode.KeepAspectRatio,
                Qt.TransformationMode.SmoothTransformation
            )
            self.image_label.setPixmap(scaled_pixmap)

    def adjust_image(self):
        if self.original_image:
            # Копируем оригинальное изображение
            self.current_image = self.original_image.copy()

            # Получаем текущие значения слайдеров
            brightness = (self.brightness_slider['slider'].value() + 100) / 100
            contrast = (self.contrast_slider['slider'].value() + 100) / 100
            saturation = (self.saturation_slider['slider'].value() + 100) / 100

            # Применяем коррекции
            if brightness != 1:
                enhancer = ImageEnhance.Brightness(self.current_image)
                self.current_image = enhancer.enhance(brightness)

            if contrast != 1:
                enhancer = ImageEnhance.Contrast(self.current_image)
                self.current_image = enhancer.enhance(contrast)

            if saturation != 1:
                enhancer = ImageEnhance.Color(self.current_image)
                self.current_image = enhancer.enhance(saturation)

            # Отображаем результат
            self.display_image()

    def adjust_sharpness(self):
        if self.original_image and self.current_image:
            # Получаем текущее значение слайдера резкости
            sharpness = self.sharpness_slider['slider'].value() / 100

            # Применяем резкость к уже скорректированному изображению
            enhancer = ImageEnhance.Sharpness(self.current_image)
            self.current_image = enhancer.enhance(sharpness)

            # Отображаем результат
            self.display_image()

    def reset_image(self):
        if self.original_image:
            self.current_image = self.original_image.copy()
            self.display_image()
            self.reset_sliders()

    def reset_sliders(self):
        self.brightness_slider['slider'].setValue(0)
        self.contrast_slider['slider'].setValue(0)
        self.saturation_slider['slider'].setValue(0)
        self.sharpness_slider['slider'].setValue(100)

    def resizeEvent(self, event):
        # При изменении размера окна перерисовываем изображение
        if self.current_image:
            self.display_image()
        super().resizeEvent(event)


if __name__ == "__main__":
    app = QApplication(sys.argv)
    editor = PhotoEditor()
    editor.show()
    sys.exit(app.exec())