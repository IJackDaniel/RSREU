import matplotlib.pyplot as plt


class PlotWindow:
    def __init__(self):
        self.fig, self.ax = plt.subplots()
        self.setup_ui()

    def setup_ui(self):
        self.ax.set_title("Решение дифференциальных уравнений. Метод Рунге-Кутта")
        self.ax.set_xlabel("X")
        self.ax.set_ylabel("Y")
        self.ax.grid(True)
        self.ax.legend()
        self.ax.axhline(y=0, color='black')
        self.ax.axvline(x=0, color='black')

    def set_point(self, x, y, color='g'):
        self.ax.plot(x, y, f"{color}o", markersize=4)

    def add_line(self, xs, ys, name=""):
        # for i in range(len(xs)):
        #     self.set_point(xs[i], ys[i])
        self.ax.plot(xs, ys, label=name)

    def show(self):
        self.ax.legend()
        plt.show()