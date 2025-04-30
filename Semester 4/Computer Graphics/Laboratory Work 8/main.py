import sys
from PyQt6.QtWidgets import (
    QApplication, QMainWindow, QWidget, QVBoxLayout, QHBoxLayout,
    QLabel, QSlider, QPushButton, QFileDialog, QMessageBox
)
from PyQt6.QtCore import Qt
from PyQt6.QtGui import QImage, QPixmap
import numpy as np
from PIL import Image


class RGBtoHSLConverter(QMainWindow):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("RGB to HSL Converter")
        self.setGeometry(100, 100, 800, 600)

        self.original_image = None
        self.hsl_image = None
        self.current_image = None

        self.init_ui()

    def init_ui(self):
        central_widget = QWidget()
        self.setCentralWidget(central_widget)

        main_layout = QHBoxLayout()
        central_widget.setLayout(main_layout)

        # Image display
        self.image_label = QLabel()
        self.image_label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        main_layout.addWidget(self.image_label, 70)

        # Controls
        controls_layout = QVBoxLayout()
        main_layout.addLayout(controls_layout, 30)

        # Load image button
        self.load_button = QPushButton("Load Image")
        self.load_button.clicked.connect(self.load_image)
        controls_layout.addWidget(self.load_button)

        # Hue slider
        self.hue_label = QLabel("Hue: 0")
        controls_layout.addWidget(self.hue_label)
        self.hue_slider = QSlider(Qt.Orientation.Horizontal)
        self.hue_slider.setRange(-180, 180)
        self.hue_slider.setValue(0)
        self.hue_slider.valueChanged.connect(self.update_hsl)
        controls_layout.addWidget(self.hue_slider)

        # Saturation slider
        self.saturation_label = QLabel("Saturation: 100%")
        controls_layout.addWidget(self.saturation_label)
        self.saturation_slider = QSlider(Qt.Orientation.Horizontal)
        self.saturation_slider.setRange(0, 200)
        self.saturation_slider.setValue(100)
        self.saturation_slider.valueChanged.connect(self.update_hsl)
        controls_layout.addWidget(self.saturation_slider)

        # Lightness slider
        self.lightness_label = QLabel("Lightness: 100%")
        controls_layout.addWidget(self.lightness_label)
        self.lightness_slider = QSlider(Qt.Orientation.Horizontal)
        self.lightness_slider.setRange(0, 200)
        self.lightness_slider.setValue(100)
        self.lightness_slider.valueChanged.connect(self.update_hsl)
        controls_layout.addWidget(self.lightness_slider)

        # Save button
        self.save_button = QPushButton("Save Image")
        self.save_button.clicked.connect(self.save_image)
        controls_layout.addWidget(self.save_button)

        # Stretch to push controls up
        controls_layout.addStretch()

    def load_image(self):
        file_name, _ = QFileDialog.getOpenFileName(
            self, "Open Image File", "",
            "Image Files (*.png *.jpg *.jpeg *.bmp *.gif)"
        )

        if file_name:
            try:
                # Load image using PIL
                pil_image = Image.open(file_name).convert("RGB")
                self.original_image = np.array(pil_image)

                # Convert to HSL and store
                self.hsl_image = self.rgb_to_hsl(self.original_image)
                self.current_image = self.original_image.copy()

                # Display the original image
                self.display_image(self.original_image)

                # Reset sliders
                self.hue_slider.setValue(0)
                self.saturation_slider.setValue(100)
                self.lightness_slider.setValue(100)

            except Exception as e:
                QMessageBox.critical(self, "Error", f"Failed to load image: {str(e)}")

    def update_hsl(self):
        if self.hsl_image is None:
            return

        # Get slider values
        hue_shift = self.hue_slider.value()
        saturation_factor = self.saturation_slider.value() / 100.0
        lightness_factor = self.lightness_slider.value() / 100.0

        # Update labels
        self.hue_label.setText(f"Hue: {hue_shift}")
        self.saturation_label.setText(f"Saturation: {self.saturation_slider.value()}%")
        self.lightness_label.setText(f"Lightness: {self.lightness_slider.value()}%")

        # Apply adjustments
        adjusted_hsl = self.hsl_image.copy()

        # Hue adjustment (shift)
        adjusted_hsl[..., 0] = (adjusted_hsl[..., 0] + hue_shift / 360.0) % 1.0

        # Saturation adjustment (multiply)
        adjusted_hsl[..., 1] = np.clip(adjusted_hsl[..., 1] * saturation_factor, 0, 1)

        # Lightness adjustment (multiply)
        adjusted_hsl[..., 2] = np.clip(adjusted_hsl[..., 2] * lightness_factor, 0, 1)

        # Convert back to RGB
        adjusted_rgb = self.hsl_to_rgb(adjusted_hsl)
        self.current_image = adjusted_rgb

        # Display the adjusted image
        self.display_image(adjusted_rgb)

    def display_image(self, image_array):
        height, width, _ = image_array.shape
        bytes_per_line = 3 * width

        # Convert numpy array to QImage
        qimage = QImage(
            image_array.data, width, height, bytes_per_line,
            QImage.Format.Format_RGB888
        )

        # Scale image to fit label while maintaining aspect ratio
        pixmap = QPixmap.fromImage(qimage)
        pixmap = pixmap.scaled(
            self.image_label.size(),
            Qt.AspectRatioMode.KeepAspectRatio,
            Qt.TransformationMode.SmoothTransformation
        )

        self.image_label.setPixmap(pixmap)

    def save_image(self):
        if self.current_image is None:
            QMessageBox.warning(self, "Warning", "No image to save")
            return

        file_name, _ = QFileDialog.getSaveFileName(
            self, "Save Image", "",
            "PNG Image (*.png);;JPEG Image (*.jpg *.jpeg);;BMP Image (*.bmp)"
        )

        if file_name:
            try:
                # Convert numpy array to PIL Image and save
                Image.fromarray(self.current_image).save(file_name)
                QMessageBox.information(self, "Success", "Image saved successfully")
            except Exception as e:
                QMessageBox.critical(self, "Error", f"Failed to save image: {str(e)}")

    def resizeEvent(self, event):
        if self.current_image is not None:
            self.display_image(self.current_image)
        super().resizeEvent(event)

    @staticmethod
    def rgb_to_hsl(rgb):
        """Convert RGB image array (0-255) to HSL image array (0-1)"""
        rgb_normalized = rgb / 255.0
        hsl = np.zeros_like(rgb_normalized)

        max_val = rgb_normalized.max(axis=2)
        min_val = rgb_normalized.min(axis=2)
        delta = max_val - min_val

        # Lightness
        hsl[..., 2] = (max_val + min_val) / 2.0

        # Hue and Saturation
        mask = delta != 0
        r, g, b = rgb_normalized[..., 0], rgb_normalized[..., 1], rgb_normalized[..., 2]

        # Hue calculation
        hue = np.zeros_like(max_val)
        hue[mask & (max_val == r)] = ((g - b) / delta)[mask & (max_val == r)] % 6
        hue[mask & (max_val == g)] = ((b - r) / delta + 2)[mask & (max_val == g)]
        hue[mask & (max_val == b)] = ((r - g) / delta + 4)[mask & (max_val == b)]
        hsl[..., 0] = hue / 6.0  # Normalize to 0-1

        # Saturation calculation
        hsl[..., 1][mask] = delta[mask] / (1 - np.abs(2 * hsl[..., 2][mask] - 1))

        return hsl

    @staticmethod
    def hsl_to_rgb(hsl):
        """Convert HSL image array (0-1) to RGB image array (0-255)"""
        h, s, l = hsl[..., 0], hsl[..., 1], hsl[..., 2]
        rgb = np.zeros_like(hsl)

        c = (1 - np.abs(2 * l - 1)) * s
        x = c * (1 - np.abs((h * 6) % 2 - 1))
        m = l - c / 2

        conditions = [
            (h < 1 / 6),
            (h < 2 / 6),
            (h < 3 / 6),
            (h < 4 / 6),
            (h < 5 / 6),
            (True)
        ]

        choices = [
            (c, x, 0),
            (x, c, 0),
            (0, c, x),
            (0, x, c),
            (x, 0, c),
            (c, 0, x)
        ]

        for i in range(3):
            rgb[..., i] = np.select(conditions, [choice[i] for choice in choices]) + m

        return (np.clip(rgb, 0, 1) * 255).astype(np.uint8)


if __name__ == "__main__":
    app = QApplication(sys.argv)
    converter = RGBtoHSLConverter()
    converter.show()
    sys.exit(app.exec())