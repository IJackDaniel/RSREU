x = int(input()) * 100
p = int(input())
y = int(input()) * 100
year = 0
while x < y:
    x = int(x * (1 + p/100))
    year += 1
print(year)