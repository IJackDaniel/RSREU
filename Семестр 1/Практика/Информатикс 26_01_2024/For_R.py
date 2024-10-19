n = int(input())
a = 1
b = 2
result = ""
s = 0
for i in range(n-2):
    result += f"{a}*{b}+"
    s += a * b
    a += 1
    b += 1
s += a * b
print(f"{result}{a}*{b}={s}")