import math

class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y


n = int(input())
points = []

for _ in range(n):
    x, y = map(int, input().split())
    points.append(Point(x, y))

# Сортируем точки по расстоянию от начала координат
points.sort(key=lambda p: math.sqrt(p.x ** 2 + p.y ** 2))

# Выводим координаты точек в порядке возрастания расстояния
for point in points:
    print(point.x, point.y)


