from random import randrange, choice


n = int(input("Введите количество строк: "))
f = open("Data.txt", "w")

for _ in range(n):
    cnt = randrange(10, 1000 + 1)
    s = "".join(choice("ABCDEF0123456789") for _ in range(cnt))
    f.write(s + "\n")
f.close()

f = open("Data.txt", "r")
mx = -1
res_mx = -1
res_str_local = ""
res_str = ""
res_line = -1
k = 1
full_str = ""
for s in f:
    long_string = ""
    for sym in s:
        if sym.isalpha() and sym != "C":
            long_string += sym
        else:
            long_string = ""

        if len(long_string) > mx:
            mx = len(long_string)
            res_str_local = long_string

    if len(res_str_local) > res_mx:
        res_line = k
        res_mx = mx
        res_str = res_str_local
        full_str = s

    k += 1
f.close()

print(f"Номер строки: {res_line}")
# print(f"Длина строки: {res_mx}")
# print(f"Строка: {res_str}")

arr = []
num = ""
for i in full_str:
    if i.isdigit():
        num += i
    else:
        if num != '':
            arr.append(int(num))
        num = ""

arr = sorted(arr)[::-1]

result = 0
for n in arr:
    if n % 3 == 0:
        result = n
        break

if result != 0:
    print(f"Искомое число = {result}")
else:
    print("В строке нет чисел, которые целочисленно делятся на 3")
