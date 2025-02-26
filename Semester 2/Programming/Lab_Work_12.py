class Flower:
    def __init__(self):
        self.name = "Роза"
        self.type = "Соцветие"
        self.country = "Голландия"

    def getInfo(self):
        print(f"Название цветка: {self.name}")
        print(f"Тип цветка: {self.type}")
        print(f"Страна происхождения цветка: {self.country}")

    def changeName(self, newName):
        self.name = newName

    def changeType(self, newType):
        self.type = newType

    def changeCountry(self, newCountry):
        self.country = newCountry

    def flowerIsExist(self):
        print(f"Цветок {self.name} существует")


# Основная программа
flower1 = Flower()
flower2 = Flower()
flower3 = Flower()
flower4 = Flower()
flower5 = Flower()

print("Функция getInfo:")
flower1.getInfo()

print("\nФункция ChangeName:")
print(f"Старое название: {flower2.name}")
flower2.changeName("Тюльпан")
print(f"Новое название: {flower2.name}")

print("\nФункция ChangeType:")
print(f"Старый тип: {flower3.type}")
flower3.changeType("Одиночный цветок")
print(f"Новый тип: {flower3.type}")

print("\nФункция ChangeCountry:")
print(f"Старая страна происхождения: {flower4.country}")
flower4.changeCountry("Япония")
print(f"Новая страна происхождения: {flower4.country}")

print("\nФункция flowerIsExist:")
flower5.flowerIsExist()
