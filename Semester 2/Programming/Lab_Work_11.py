"""
Вариант 3.	На дачном участке на грядке огорода, цветочной клумбе и в палисаднике произрастают весенние,
летние и осенние цветы. Каждый цветок имеет характеристики: название, высокий или низкий, отдельный цветок или соцветие,
цвет цветка, размножается семенами или клубнями.
Составьте диаграмму и опишите характеристики объектов (цветов) на дачном участке.
"""


class Flower:
    def __init__(self, name, height, solo_many, color, razmn):
        self.FlowerName = name
        self.FlowerHeight = height
        self.FlowerCount = solo_many
        self.FlowerColor = color
        self.FlowerRazmn = razmn

    def GetInf(self):
        print(f"""Название, {self.FlowerName}
        Высокий/Низкий, {self.FlowerHeight}
        Одиночный/Соцветие, {self.FlowerCount}
        Цвет, {self.FlowerColor}
        Тип размножения, {self.FlowerRazmn}
        """)


class SpringFlower(Flower):
    def GetInfSpring(self):
        print("Это весенний цветок")


class AutumFlower(Flower):
    def GetInfAutum(self):
        print("Это осенний цветок")


class SummerFlower(Flower):
    def GetInfSummer(self):
        print("Это летний цветок")

