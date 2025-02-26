import turtle


def func():
    pen.forward(180)
    pen.right(90)
    pen.forward(40)
    pen.right(90)
    pen.forward(180)
    pen.right(180)


def flag(arr_colors):
    for color in arr_colors:
        pen.color(color, color)
        pen.begin_fill()
        func()
        pen.end_fill()


pen = turtle.Turtle()


colors1 = ["black", "navy", "red"]
colors2 = ["dodger blue", "navy", "red"]
pen.speed(0)
pen.hideturtle()

pen.penup()
pen.goto(-360, 80)
pen.pendown()

# Flag DNR
flag(colors1)

pen.penup()
pen.goto(-100, 80)
pen.pendown()

# Flag LNR
flag(colors2)

pen.penup()
pen.goto(160, 80)
pen.pendown()

# Flag Novorossia
pen.color("navy", "navy")
pen.begin_fill()
for i1 in range(2):
    pen.forward(180)
    pen.right(90)
    pen.forward(120)
    pen.right(90)
pen.end_fill()

pen.goto(160, 60)

pen.color("white", "red")
pen.pensize(5)


for i2 in range(2):
    pen.pendown()
    pen.begin_fill()
    k = 80
    pen.right(30)
    pen.forward(k)
    pen.right(120)
    pen.forward(k)
    pen.end_fill()

    pen.penup()
    pen.right(30)
    pen.goto(341, -20)

pen.right(90)
pen.goto(180, 80)

for i3 in range(2):
    pen.pendown()
    pen.begin_fill()
    k = 80
    pen.left(60)
    pen.forward(k)
    pen.left(60)
    pen.forward(k)
    pen.end_fill()

    pen.penup()
    pen.left(60)
    pen.goto(320, -41)

# plus
pen.pencolor("black")
pen.goto(-140, 20)
pen.left(90)
pen.pensize(1)
pen.pendown()

for i4 in range(4):
    pen.forward(20)
    pen.backward(20)
    pen.right(90)

# ravno
pen.penup()
pen.goto(100, 30)
pen.pendown()

pen.forward(40)

pen.penup()
pen.right(90)
pen.forward(20)
pen.right(90)
pen.pendown()

pen.forward(40)

turtle.done()
