from Runge_Kutt_system import *
from Runge_Kutt_first_order import *
from Runge_Kutt_second_order import *

solve_first_order_equation(0.1, 2.6, 0.1, 1)

a = 0
b = 2
y1_0 = 0.5
y2_0 = 1.5
h = 0.1
solve_system(f2_1, f2_2, a, b, y1_0, y2_0, h)

x0 = 0
x_end = 2
y1_0 = 0
y2_0 = 1
h = 0.1
a1 = -4
a2 = 4
solve_second_order_equation(f3_1, f3_2, x0, x_end, y1_0, y2_0, h, a1, a2)

"""
Какие ещё методы решения ДУ использоуются
"""