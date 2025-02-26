"""
Задание 3. Изобразите на холсте три кнопки (друг под другом) красного, желтого и зеленого цвета.
При нажатии на кнопку красного цвета на экране должно появляться слово СТОП, при нажатии
на кнопку желтого цвета – слово ВНИМАНИЕ, а при нажатии на зеленую кнопку – слово ВПЕРЕД.
"""
import turtle as tl


def bt(obj, color):
    obj.color("black", str(color))
    obj.begin_fill()

    obj.forward(200)
    obj.right(90)
    obj.forward(80)
    obj.right(90)
    obj.forward(200)
    obj.right(90)
    obj.forward(80)

    obj.end_fill()


def click(x, y):
    if -350 <= x <= -150 and 0 <= y <= 80:
        txt.clear()
        txt.goto(-40, -60)
        txt.write("CТОП", font=("Arial", 20, "bold"))
    elif -100 <= x <= 100 and 0 <= y <= 80:
        txt.clear()
        txt.goto(-80, -60)
        txt.write("ВНИМАНИЕ", font=("Arial", 20, "bold"))
    elif 150 <= x <= 350 and 0 <= y <= 80:
        txt.clear()
        txt.goto(-60, -60)
        txt.write("ВПЕРЁД", font=("Arial", 20, "bold"))


# Создание окна win
win = tl.Screen()

win.title("Светофор")
win.setup(800, 300)

button_red = tl.Turtle()
button_yellow = tl.Turtle()
button_green = tl.Turtle()

txt = tl.Turtle()
txt.hideturtle()
txt.penup()

txt.speed(0)
button_red.speed(0)
button_yellow.speed(0)
button_green.speed(0)

button_red.hideturtle()
button_yellow.hideturtle()
button_green.hideturtle()

button_red.penup()
button_yellow.penup()
button_green.penup()

button_red.goto(-350, 80)
button_yellow.goto(-100, 80)
button_green.goto(150, 80)

button_red.pendown()
button_yellow.pendown()
button_green.pendown()

bt(button_red, "red")
bt(button_yellow, "yellow")
bt(button_green, "green")

tl.listen()

tl.onscreenclick(click, 1)

# Выход из программы по нажатию на окно
tl.done()
