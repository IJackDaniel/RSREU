alph = "ABCDEFGHIJKLMNOPQRS"
num = int(input("Введите количество задач: "))
for i in range(num):
    name_raw = input("Введите название задачи: ")
    name = f"{alph[i]}. {name_raw}"
    file = open(f"{name}.py", "w")
    file.close()
