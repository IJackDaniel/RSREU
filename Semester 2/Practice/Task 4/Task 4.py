import sys
from PyQt5.QtWidgets import QApplication, QMainWindow, QWidget, QMessageBox
from CorgiShopCalc import Ui_Window
from history import Ui_history
from win_about_author import Ui_winAuthor
from win_about_proggram import Ui_winProg
from win_change_color import Ui_win_change_color
import os
import tempfile


storage_path = os.path.join(tempfile.gettempdir(), 'historyComputing.txt')


txt = ""
col = "black"

class Window(QMainWindow, Ui_Window):
    def __init__(self):
        global txt, col

        super().__init__()
        self.setupUi(self)
        self.setFixedSize(570, 600)

        #
        self.flag = False

        # Все дополнительные окна
        self.win_history = WinHistory(txt)
        self.win_author = WinAuthor()
        self.win_prog = WinProg()
        self.win_color = WinChangeColor()
        self.win_history.setFixedSize(550, 400)
        self.win_author.setFixedSize(500, 150)
        self.win_prog.setFixedSize(500, 150)
        self.win_history.setFixedSize(400, 300)
        self.win_color.setFixedSize(600, 300)
        self.open_history.triggered.connect(self.open_history_func)
        self.open_author.triggered.connect(self.open_author_func)
        self.open_prog.triggered.connect(self.open_prog_func)
        self.open_color.triggered.connect(self.open_color_func)

        #
        self.close.triggered.connect(self.closeEvent)

        #
        self.btn_calc.clicked.connect(self.run_solution)
        self.btn_save.clicked.connect(self.save_history)

        #
        self.var = 1
        self.rbt_sh.clicked.connect(self.var1)
        self.rbt_sw.clicked.connect(self.var2)
        self.rbt_hd.clicked.connect(self.var3)
        self.rbt_pn.clicked.connect(self.var4)
        self.rbt_cp.clicked.connect(self.var5)

    def var1(self):
        self.var = 1
        self.var_change()

    def var2(self):
        self.var = 2
        self.var_change()

    def var3(self):
        self.var = 3
        self.var_change()

    def var4(self):
        self.var = 4
        self.var_change()

    def var5(self):
        self.var = 5
        self.var_change()

    def run_solution(self):
        """ Считывание параметров """
        self.flag = True
        try:
            if self.var == 1 or self.var == 2 or self.var == 3:
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
                if self.var == 1:
                    name = "Футболок"
                elif self.var == 2:
                    name = "Свитшотов"
                elif self.var == 3:
                    name = "Худи"
                self.cost(cnt, self.var, name)

            elif self.var == 4 or self.var == 5:
                hat = int(self.ent_hat.text()) if self.ent_hat.text() != "" else 0
                cnt = hat
                name = None
                if self.var == 4:
                    name = "Панамок"
                elif self.var == 5:
                    name = "Кепок"
                self.cost(cnt, self.var, name)
            self.btn_save.setEnabled(True)
        except ValueError:
            self.showWarning("Проверьте ввод!")

        except Exception:
            self.showWarning("Непонятная ошибка.")

    def cost(self, cnt, var, name):
        """ Поиск корней линейного и квадратного уравнения """
        global txt
        cst = 0
        match var:
            case 1:  # футболка
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
            case 2:  # свитшот
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
            case 3:  # худи
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
            case 4:  # панамка
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
            case 5:  # кепка
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
        txt_r = f"Цена для {cnt} {name}: {cst * cnt} рублей"
        self.lbl_result.setText(txt_r)
        # self.txt_sf = self.txt_sf + "\n" + txt

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
        self.btn_save.setEnabled(False)

    def var_change(self):
        self.data_clear()
        if self.var <= 3:
            self.ent_xs.setEnabled(True)
            self.ent_s.setEnabled(True)
            self.ent_m.setEnabled(True)
            self.ent_l.setEnabled(True)
            self.ent_xl.setEnabled(True)
            self.ent_xxl.setEnabled(True)
            self.ent_xxxl.setEnabled(True)
            self.ent_hat.setEnabled(False)
        else:
            self.ent_xs.setEnabled(False)
            self.ent_s.setEnabled(False)
            self.ent_m.setEnabled(False)
            self.ent_l.setEnabled(False)
            self.ent_xl.setEnabled(False)
            self.ent_xxl.setEnabled(False)
            self.ent_xxxl.setEnabled(False)
            self.ent_hat.setEnabled(True)

    def save_history(self):
        global txt
        file = open(storage_path, "a")
        txt = txt + '\n' + self.lbl_result.text()
        file.write(txt)
        file.close()

    def showWarning(self, warning_text):
        msgBox = QMessageBox(self)
        msgBox.setIcon(QMessageBox.Warning)
        msgBox.setText(warning_text)
        msgBox.setWindowTitle("Warning")
        msgBox.exec_()

    def open_history_func(self):
        self.win_history = WinHistory(txt)
        self.win_history.show()

    def open_author_func(self):
        self.win_author.show()

    def open_prog_func(self):
        self.win_prog.show()

    def open_color_func(self):
        self.win_color.show()

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


class WinHistory(QWidget, Ui_history):
    def __init__(self, text):
        super().__init__()
        self.setupUi(self)

        self.lbl_history.setText(text)
        self.lbl_history.setStyleSheet(f"color: {col}")

        self.pushButton.clicked.connect(self.history_clear)

    def history_clear(self):
        """ Очистка многострочного текстового поля """
        global txt
        txt = ""
        self.lbl_history.setText("")
        f = open(storage_path, "w")
        f.close()


class WinAuthor(QWidget, Ui_winAuthor):
    def __init__(self):
        super().__init__()
        self.setupUi(self)


class WinProg(QWidget, Ui_winProg):
    def __init__(self):
        super().__init__()
        self.setupUi(self)


class WinChangeColor(QWidget, Ui_win_change_color):
    def __init__(self):
        super().__init__()
        self.setupUi(self)

        self.rbt_black.clicked.connect(self.col1)
        self.rbt_red.clicked.connect(self.col2)
        self.rbt_blue.clicked.connect(self.col3)
        self.rbt_orange.clicked.connect(self.col4)
        self.rbt_green.clicked.connect(self.col5)

        self.ncolor = ""

        self.pushButton.clicked.connect(self.color_ch)

    def color_ch(self):
        global col
        col = self.ncolor
        self.win_history = WinHistory(txt)

    def col1(self):
        self.label_2.setStyleSheet('color: black')
        self.ncolor = "black"

    def col2(self):
        self.label_2.setStyleSheet('color: red')
        self.ncolor = "red"

    def col3(self):
        self.label_2.setStyleSheet('color: blue')
        self.ncolor = "blue"

    def col4(self):
        self.label_2.setStyleSheet('color: orange')
        self.ncolor = "orange"

    def col5(self):
        self.label_2.setStyleSheet('color: green')
        self.ncolor = "green"


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = Window()
    window.show()
    sys.exit(app.exec_())
