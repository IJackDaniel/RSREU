n = int(input("Количество элементов словаря: "))
print()
dictionary = {}

for i in range(n):
    dictionary[i] = float(input(f"Ввод {i} элемента: "))

print(f"\nЭхо-вывод словаря: \n{dictionary}")

prod = 1
for i in range(n):
    prod *= dictionary[i]
print(f"\nПроизведение всех элементов в словаре равно {prod}")
