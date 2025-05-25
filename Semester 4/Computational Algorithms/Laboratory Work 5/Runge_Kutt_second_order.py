import math
from My_plot_window import PlotWindow
import matplotlib.pyplot as plt


def f3_1(x, y1, y2):
    return y2


def f3_2(x, y1, y2, a1, a2):
    return -a1 * y2 - a2 * y1


def runge_kutt_for_second_order(f1, f2, x0, x_end, y1_0, y2_0, h, a1, a2):
    X = [x0]
    Y1 = [y1_0]
    Y2 = [y2_0]
    N = int((x_end - x0) / h)

    for _ in range(N):
        x = X[-1]
        y1 = Y1[-1]
        y2 = Y2[-1]

        k1_1 = h * f1(x, y1, y2)
        k1_2 = h * f2(x, y1, y2, a1, a2)

        k2_1 = h * f1(x + h / 2, y1 + k1_1 / 2, y2 + k1_2 / 2)
        k2_2 = h * f2(x + h / 2, y1 + k1_1 / 2, y2 + k1_2 / 2, a1, a2)

        k3_1 = h * f1(x + h / 2, y1 + k2_1 / 2, y2 + k2_2 / 2)
        k3_2 = h * f2(x + h / 2, y1 + k2_1 / 2, y2 + k2_2 / 2, a1, a2)

        k4_1 = h * f1(x + h, y1 + k3_1, y2 + k3_2)
        k4_2 = h * f2(x + h, y1 + k3_1, y2 + k3_2, a1, a2)

        y1_new = y1 + (k1_1 + 2 * k2_1 + 2 * k3_1 + k4_1) / 6
        y2_new = y2 + (k1_2 + 2 * k2_2 + 2 * k3_2 + k4_2) / 6
        x_new = x + h

        X.append(x_new)
        Y1.append(y1_new)
        Y2.append(y2_new)

    return X, Y1, Y2


def solve_second_order_equation(f1, f2, x0, x_end, y1_0, y2_0, h, a1, a2):
    x, y, y2 = runge_kutt_for_second_order(f1, f2, x0, x_end, y1_0, y2_0, h, a1, a2)
    graph = PlotWindow()
    graph.add_line(x, y, "Численное решение диф. уравнения второго порядка")
    graph.show()


if __name__ == "__main__":
    x0 = 0
    x_end = 2
    y1_0 = 0
    y2_0 = 1
    h = 0.1
    a1 = -4
    a2 = 4

    solve_second_order_equation(f3_1, f3_2, x0, x_end, y1_0, y2_0, h, a1, a2)
