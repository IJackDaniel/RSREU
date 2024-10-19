from PyQt5.QtWidgets import QMessageBox, QAction, QMenu, QLineEdit, QButtonGroup, QRadioButton
from PyQt5.QtWidgets import QApplication, QMainWindow, QLabel, QPushButton, QWidget
from PyQt5.QtCore import Qt
import sys


class Window(QMainWindow):
    def __init__(self):
        super(Window, self).__init__()

        self.setWindowTitle("Рассчёт цен на CorgiShop")
        self.setGeometry(220, 250, 900, 750)
        self.setFixedSize(900, 750)

        self.lbl_prod = QLabel("Товар:", self)
        self.prod_ts = QRadioButton("Футболка", self)
        self.prod_sw = QRadioButton("Свитшот", self)
        self.prod_hd = QRadioButton("Худи", self)
        self.prod_pn = QRadioButton("Панамка", self)
        self.prod_cp = QRadioButton("Кепка", self)
        self.prod_ts.adjustSize()
        self.prod_sw.adjustSize()
        self.prod_hd.adjustSize()
        self.prod_pn.adjustSize()
        self.prod_cp.adjustSize()
        startx1 = 100
        y1 = 50
        distance = 160
        self.lbl_prod.move(startx1 - 80, y1)
        self.prod_ts.move(startx1, y1)
        self.prod_sw.move(startx1 + distance, y1)
        self.prod_hd.move(startx1 + distance * 2, y1)
        self.prod_pn.move(startx1 + distance * 3, y1)
        self.prod_cp.move(startx1 + distance * 4, y1)
        self.prod_group = QButtonGroup(self)
        self.prod_group.addButton(self.prod_ts)
        self.prod_group.addButton(self.prod_sw)
        self.prod_group.addButton(self.prod_hd)
        self.prod_group.addButton(self.prod_pn)
        self.prod_group.addButton(self.prod_cp)
        self.prod_ts.setChecked(True)
        self.prod_group.buttonClicked.connect(self.var_change)
        self.var = 0

        self.lbl_prtype = QLabel("Количество:", self)
        self.lbl_prtype.adjustSize()
        self.lbl_xs = QLabel("XS", self)
        self.lbl_s = QLabel("S", self)
        self.lbl_m = QLabel("M", self)
        self.lbl_l = QLabel("L", self)
        self.lbl_xl = QLabel("XL", self)
        self.lbl_xxl = QLabel("XXL", self)
        self.ent_xs = QLineEdit(self)
        self.ent_s = QLineEdit(self)
        self.ent_m = QLineEdit(self)
        self.ent_l = QLineEdit(self)
        self.ent_xl = QLineEdit(self)
        self.ent_xxl = QLineEdit(self)
        self.ent_hat = QLineEdit(self)
        entx = 150
        enty = 30
        self.ent_xs.resize(entx, enty)
        self.ent_s.resize(entx, enty)
        self.ent_m.resize(entx, enty)
        self.ent_l.resize(entx, enty)
        self.ent_xl.resize(entx, enty)
        self.ent_xxl.resize(entx, enty)
        self.ent_hat.resize(700, enty)
        startx2 = 150
        y2 = 150
        dstx = 250
        dsty = 70
        self.lbl_prtype.move(startx2 - 130, (y2 * 2 + dsty) // 2)
        self.lbl_xs.move(startx2, y2)
        self.lbl_s.move(startx2, y2 + dsty)
        self.lbl_m.move(startx2 + dstx, y2)
        self.lbl_l.move(startx2 + dstx, y2 + dsty)
        self.lbl_xl.move(startx2 + dstx * 2, y2)
        self.lbl_xxl.move(startx2 + dstx * 2, y2 + dsty)
        dst_btw_lb_en = 40
        self.ent_xs.move(startx2 + dst_btw_lb_en, y2)
        self.ent_s.move(startx2 + dst_btw_lb_en, y2 + dsty)
        self.ent_m.move(startx2 + dstx + dst_btw_lb_en, y2)
        self.ent_l.move(startx2 + dstx + dst_btw_lb_en, y2 + dsty)
        self.ent_xl.move(startx2 + dstx * 2 + dst_btw_lb_en, y2)
        self.ent_xxl.move(startx2 + dstx * 2 + dst_btw_lb_en, y2 + dsty)
        self.ent_hat.move(startx2, (y2 * 2 + dsty)//2)
        self.ent_hat.setVisible(False)

        self.ent_xs.mousePressEvent = self.mousePressEvent
        self.ent_s.mousePressEvent = self.mousePressEvent
        self.ent_m.mousePressEvent = self.mousePressEvent
        self.ent_l.mousePressEvent = self.mousePressEvent
        self.ent_xl.mousePressEvent = self.mousePressEvent
        self.ent_xxl.mousePressEvent = self.mousePressEvent
        self.ent_hat.mousePressEvent = self.mousePressEvent

        self.txt_sf = ""

        self.btn1 = QPushButton("Узнать цену", self)
        self.btn2 = QPushButton("Очистить", self)
        self.btn3 = QPushButton("Сохранить", self)
        btn_x = 250
        btn_y = 50
        self.btn1.resize(btn_x, btn_y)
        self.btn2.resize(btn_x, btn_y)
        self.btn3.resize(btn_x, btn_y)
        dsty_btn = btn_x + 20
        startx3 = 55
        starty3 = 270
        self.btn1.move(startx3, starty3)
        self.btn2.move(startx3 + dsty_btn, starty3)
        self.btn3.move(startx3 + dsty_btn * 2, starty3)
        self.btn1.clicked.connect(self.run_solution)
        self.btn2.clicked.connect(self.data_clear)
        self.btn3.clicked.connect(self.save_history)
        self.flag = False
        self.btn2.setEnabled(False)
        self.btn3.setEnabled(False)

        self.lbl_result = QLabel(self)
        self.lbl_result.resize(860, 50)
        self.lbl_result.move(20, 350)

        self.lbl_story = QLabel(self)
        self.lbl_story.resize(860, 300)
        self.lbl_story.setAlignment(Qt.AlignTop | Qt.AlignLeft)
        self.lbl_story.setStyleSheet("background-color: white; border: 2px solid black")
        self.lbl_story.move(20, 400)

    def contextMenuEvent(self, event):
        cmenu = QMenu(self)

        newAct = QAction("Очистить историю", self)
        win2Act = QAction("История вычислений")
        inf1Act = QAction("О программе", self)
        inf2Act = QAction("Об авторе", self)

        cmenu.addAction(newAct)
        cmenu.addAction(win2Act)
        cmenu.addAction(inf1Act)
        cmenu.addAction(inf2Act)

        action = cmenu.exec_(self.mapToGlobal(event.pos()))

        txt = self.lbl_story.text()

        if action == newAct:
            self.clear_history()
        elif action == win2Act:
            self.open_second_form(txt)
        elif action == inf1Act:
            self.showInfo(1)
        elif action == inf2Act:
            self.showInfo(2)

    def save_history(self):
        txt = self.lbl_result.text()
        txt_before = self.lbl_story.text()
        self.lbl_story.setText(txt_before + "\n" + txt)

    def clear_history(self):
        self.lbl_story.setText("")

    def var_change(self, button):
        self.data_clear()
        tp = button.text()
        if tp == "Футболка":
            self.var = 0
        elif tp == "Свитшот":
            self.var = 1
        elif tp == "Худи":
            self.var = 2
        elif tp == "Панамка":
            self.var = 3
        elif tp == "Кепка":
            self.var = 4
        if self.var <= 2:
            self.ent_xs.setVisible(True)
            self.ent_s.setVisible(True)
            self.ent_m.setVisible(True)
            self.ent_l.setVisible(True)
            self.ent_xl.setVisible(True)
            self.ent_xxl.setVisible(True)
            self.lbl_xs.setVisible(True)
            self.lbl_s.setVisible(True)
            self.lbl_m.setVisible(True)
            self.lbl_l.setVisible(True)
            self.lbl_xl.setVisible(True)
            self.lbl_xxl.setVisible(True)
            self.ent_hat.setVisible(False)
        else:
            self.ent_xs.setVisible(False)
            self.ent_s.setVisible(False)
            self.ent_m.setVisible(False)
            self.ent_l.setVisible(False)
            self.ent_xl.setVisible(False)
            self.ent_xxl.setVisible(False)
            self.lbl_xs.setVisible(False)
            self.lbl_s.setVisible(False)
            self.lbl_m.setVisible(False)
            self.lbl_l.setVisible(False)
            self.lbl_xl.setVisible(False)
            self.lbl_xxl.setVisible(False)
            self.ent_hat.setVisible(True)

    def run_solution(self):
        """ Считывание параметров """
        self.flag = True
        try:
            if self.var == 0 or self.var == 1 or self.var == 2:
                xs = int(self.ent_xs.text()) if self.ent_xs.text() != "" else 0
                s = int(self.ent_s.text()) if self.ent_s.text() != "" else 0
                m = int(self.ent_m.text()) if self.ent_m.text() != "" else 0
                l = int(self.ent_l.text()) if self.ent_l.text() != "" else 0
                xl = int(self.ent_xl.text()) if self.ent_xl.text() != "" else 0
                xxl = int(self.ent_xxl.text()) if self.ent_xxl.text() != "" else 0
                cnt = xs + s + m + l + xl + xxl
                if xs < 0 or s < 0 or m < 0 or l < 0 or xl < 0 or xxl < 0:
                    xs = int("LOL")
                name = None
                if self.var == 0:
                    name = "Футболок"
                elif self.var == 1:
                    name = "Свитшотов"
                elif self.var == 2:
                    name = "Худи"
                self.cost(cnt, self.var, name)

            elif self.var == 3 or self.var == 4:
                hat = int(self.ent_hat.text()) if self.ent_hat.text() != "" else 0
                cnt = hat
                name = None
                if self.var == 3:
                    name = "Панамок"
                elif self.var == 4:
                    name = "Кепок"
                self.cost(cnt, self.var, name)
        except ValueError:
            self.showWarning("Проверьте ввод!")

        except Exception:
            self.showWarning("Непонятная ошибка.")

        self.btn2.setEnabled(True)
        self.btn3.setEnabled(True)

    def showWarning(self, warning_text):
        msgBox = QMessageBox(self)
        msgBox.setIcon(QMessageBox.Warning)
        msgBox.setText(warning_text)
        msgBox.setWindowTitle("Warning")
        msgBox.exec_()

    def showInfo(self, infType):
        msgBox = QMessageBox(self)
        msgBox.setIcon(QMessageBox.Information)
        if infType == 1:
            info_text = "Программа позволяет вам рассчитать стоимость партий товаров в магазине CorgiShop"
            title = "О программе"
        else:
            info_text = "Чтобы поддержать автора, вы можете перевести деньги по номеру\
 +7-900-602-43-10 на Сбербанк или Тинькофф. Он очень хороший человек и любит корги"
            title = "Об авторе"
        msgBox.setText(info_text)
        msgBox.setWindowTitle(title)
        msgBox.exec_()

    def cost(self, cnt, var, name):
        """ Поиск корней линейного и квадратного уравнения """
        cst = 0
        match var:
            case 0:  # футболка
                if cnt >= 500:
                    cst = 600
                elif cnt >= 300:
                    cst = 800
                elif cnt >= 150:
                    cst = 1000
                elif cnt >= 50:
                    cst = 1100
                else:
                    cst = 1300
            case 1:  # свитшот
                if cnt >= 500:
                    cst = 1600
                elif cnt >= 300:
                    cst = 1800
                elif cnt >= 150:
                    cst = 2100
                elif cnt >= 50:
                    cst = 2300
                else:
                    cst = 2500
            case 2:  # худи
                if cnt >= 500:
                    cst = 1600
                elif cnt >= 300:
                    cst = 1800
                elif cnt >= 150:
                    cst = 2100
                elif cnt >= 50:
                    cst = 2300
                else:
                    cst = 2500
            case 3:  # панамка
                if cnt >= 500:
                    cst = 250
                elif cnt >= 300:
                    cst = 280
                elif cnt >= 150:
                    cst = 320
                elif cnt >= 50:
                    cst = 370
                else:
                    cst = 400
            case 4:  # кепка
                if cnt >= 500:
                    cst = 250
                elif cnt >= 300:
                    cst = 280
                elif cnt >= 150:
                    cst = 320
                elif cnt >= 50:
                    cst = 370
                else:
                    cst = 400
        txt = f"Цена для {cnt} {name}: {cst * cnt} рублей"
        self.lbl_result.setText(txt)
        self.txt_sf = self.txt_sf + "\n" + txt

    def mousePressEvent(self, event):
        if self.flag:
            if event.button() == Qt.LeftButton:
                self.data_clear()

    def closeEvent(self, e):
        result = QMessageBox.question(self, "Подтверждение закрытия окна",
                                                "Вы действительно хотите закрыть окно?",
                                                QMessageBox.Yes | QMessageBox.No,
                                                QMessageBox.No)
        if result == QMessageBox.Yes:
            e.accept()
            QWidget.closeEvent(self, e)
        else:
            e.ignore()

    def data_clear(self):
        """ Очистка полей для ввода и поля с ценой """
        self.flag = False
        self.ent_xs.clear()
        self.ent_s.clear()
        self.ent_m.clear()
        self.ent_l.clear()
        self.ent_xl.clear()
        self.ent_xxl.clear()
        self.ent_hat.clear()
        self.lbl_result.setText("")
        self.btn2.setEnabled(False)
        self.btn3.setEnabled(False)

    def open_second_form(self, txt):
        self.second_form = SecondForm(self.txt_sf)
        self.second_form.show()


class SecondForm(QWidget):
    def __init__(self, txt):

        super(SecondForm, self).__init__()
        self.setGeometry(1200, 250, 600, 600)
        self.setFixedSize(600, 600)
        self.setWindowTitle('Полная история рассчётов')

        self.lbl = QLabel(self)
        self.lbl.resize(560, 560)
        self.lbl.move(20, 20)
        self.lbl.setStyleSheet("background-color: white; border: 2px solid black")
        self.lbl.setText(txt)
        self.lbl.setAlignment(Qt.AlignTop | Qt.AlignLeft)


def application():
    app = QApplication(sys.argv)
    win = Window()

    win.show()
    sys.exit(app.exec_())


if __name__ == "__main__":
    application()
