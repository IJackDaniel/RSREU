from math import sin, pi, cos

from matplotlib import pyplot as plt


class PlotWindow:
    def __init__(self, points_x, points_y):
        super().__init__()
        self.points_x = points_x
        self.points_y = points_y
        self.ax = ''
        self.setup_ui()

    def setup_ui(self):
        arg = []
        x0, hx, xn = self.points_x[0] - 0.05, 0.0005, self.points_x[-1] + 0.05
        x = x0
        while x < xn + hx / 3:
            arg.append(round(x, 10))
            x += hx
        y1 = [f(a) for a in arg]
        y2 = [backwards_interpolation(a, self.points_x, self.points_y) for a in arg]

        plt.figure()
        plt.subplot(2, 1, 1)
        plt.plot(arg, y1, label="Исходная функция", color="black")
        plt.plot(arg, y2, label="Интерполирующая функция", color="red")
        plt.legend()
        plt.grid()
        plt.suptitle('Интерполирование функции')
        self.ax = plt.gca()

        for i in range(len(self.points_x)):
            self.set_point(self.points_x[i], self.points_y[i])

        plt.subplot(2, 1, 2)
        inaccuracy = []
        for i in range(len(arg)):
            inaccuracy.append(abs(y1[i] - y2[i]))
        plt.plot(arg, inaccuracy, label="абсолютная погрешность", color="red")
        plt.legend()
        plt.grid()
        plt.show()

    def set_point(self, x, y, color='b'):
        self.ax.plot(x, y, f"{color}o")


def make_points_x(x0, xn, n):
    res = []
    h = (xn - x0) / n
    x = x0
    while x < xn + h / 3:
        res.append(round(x, 10))
        x += h
    return res


def make_points_y(xs):
    return [round(f(x), 10) for x in xs]


def f(x):
    res = sin(pi * cos(pi * x))
    return res


def divided_difference(points_x, points_y):
    n = len(points_x)
    res = 0
    for k in range(n):
        tmp = points_y[k]
        prod = 1
        for j in range(n):
            if j != k:
                prod *= points_x[k] - points_x[j]
        tmp /= prod
        res += tmp
    return res


def backwards_interpolation(current, points_x, points_y):
    n = len(points_x)
    res = 0
    for i in range(n - 1, -1, -1):
        x = points_x[i:]
        y = points_y[i:]
        temp = divided_difference(x, y)
        for j in range(i + 1, n):
            temp *= current - points_x[j]
        res += temp
    return res


if __name__ == '__main__':
    x_start = 0
    x_end = 1
    quantity = 5
    args = make_points_x(x_start, x_end, quantity)
    ys = make_points_y(args)
    PlotWindow(args, ys)
