name_raw = "старт"
d = input("Ввод разделителя: ")
while name_raw != "":
    name_raw = input("Введите название задачи: ")
    if name_raw == "":
        continue
    name = f"{d}".join(name_raw.split()) + ".py"
    file = open(name, "w")
    file.close()
