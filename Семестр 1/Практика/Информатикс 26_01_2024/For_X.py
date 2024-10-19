a = int(input())
b = int(input())
for num in range(a, b+1):
    num = str(num)
    l = list(set(num))
    if len(l) == 2 and (num.count(l[0]) == 3 or num.count(l[-1]) == 3):
        print(num)
