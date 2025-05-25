from math import sin, atan

from My_plot_window import PlotWindow


def f2_1(x, y1, y2):
    return atan(x**2 + y2**2)


def f2_2(x, y1, y2):
    return sin(x + y1)


def runge_kutt_system(f1, f2, a, b, y1_0, y2_0, h):
    x_vals = [a]
    y1_vals = [y1_0]
    y2_vals = [y2_0]
    x = a
    y1 = y1_0
    y2 = y2_0
    while x < b:
        k1 = h * f1(x, y1, y2)
        m1 = h * f2(x, y1, y2)

        k2 = h * f1(x + h / 2, y1 + k1 / 2, y2 + m1 / 2)
        m2 = h * f2(x + h / 2, y1 + k1 / 2, y2 + m1 / 2)

        k3 = h * f1(x + h / 2, y1 + k2 / 2, y2 + m2 / 2)
        m3 = h * f2(x + h / 2, y1 + k2 / 2, y2 + m2 / 2)

        k4 = h * f1(x + h, y1 + k3, y2 + m3)
        m4 = h * f2(x + h, y1 + k3, y2 + m3)

        y1 += (k1 + 2 * k2 + 2 * k3 + k4) / 6
        y2 += (m1 + 2 * m2 + 2 * m3 + m4) / 6
        x += h

        x_vals.append(x)
        y1_vals.append(y1)
        y2_vals.append(y2)

    return x_vals, y1_vals, y2_vals


def solve_system(f1, f2, a, b, y1_0, y2_0, h):
    x1_1, y1_1, y2_1 = runge_kutt_system(f1, f2, a, b, y1_0, y2_0, h)
    x1_2, y1_2, y2_2 = runge_kutt_system(f1, f2, a, b, y1_0, y2_0, h / 2)
    x1_3, y1_3, y2_3 = runge_kutt_system(f1, f2, a, b, y1_0, y2_0, 2 * h)
    # print(y1)
    # print(y2)
    # print(y3)
    graph = PlotWindow()
    graph.add_line(x1_1, y1_1, "y1 с шагом h")
    graph.add_line(x1_1, y2_1, "y2 с шагом h")

    graph.add_line(x1_2, y1_2, "y1 с шагом h/2")
    graph.add_line(x1_2, y2_2, "y2 с шагом h/2")

    graph.add_line(x1_3, y1_3, "y1 с шагом 2h")
    graph.add_line(x1_3, y2_3, "y2 с шагом 2h")
    graph.show()


if __name__ == "__main__":
    a = 0
    b = 2
    y1_0 = 0.5
    y2_0 = 1.5
    h = 0.1
    solve_system(f2_1, f2_2, a, b, y1_0, y2_0, h)
