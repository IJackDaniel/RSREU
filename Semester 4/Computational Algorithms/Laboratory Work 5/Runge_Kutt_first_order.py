from My_plot_window import PlotWindow
from math import log, e


def f1_1(x, y):
    return (-y * log(y, e)) / x


def k1(h, x, y):
    return h * f1_1(x, y)


def k2(h, x, y):
    return h * f1_1(x + h / 2, y + k1(h, x, y) / 2)


def k3(h, x, y):
    return h * f1_1(x + h / 2, y + k2(h, x, y) / 2)


def k4(h, x, y):
    return h * f1_1(x + h, y + k3(h, x, y))


def runge_kutt_first_order(x0, xn, h, y0):
    xs = []
    x = x0
    while x < xn + h / 3:
        xs.append(round(x, 10))
        x += h
    ys = [y0] * len(xs)
    for i in range(len(xs) - 1):
        ys[i + 1] = ys[i] + (k1(h, xs[i], ys[i]) + 2 * k2(h, xs[i], ys[i]) +
                             2 * k3(h, xs[i], ys[i]) + k4(h, xs[i], ys[i])) / 6
    return xs, ys


def solve_first_order_equation(x0, xn, h, y0):
    x1, y1 = runge_kutt_first_order(x0, xn, h, y0)
    x2, y2 = runge_kutt_first_order(x0, xn, h * 2, y0)
    x3, y3 = runge_kutt_first_order(x0, xn, h / 2, y0)
    graph = PlotWindow()
    graph.add_line(x1, y1, "Результат численного интегрирования с шагом h")
    graph.add_line(x2, y2, "Результат численного интегрирования с шагом 2h")
    graph.add_line(x3, y3, "Результат численного интегрирования с шагом h/2")
    graph.show()


if __name__ == '__main__':
    solve_first_order_equation(0.1, 2.6, 0.1, 1)
