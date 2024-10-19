n = int(input())
for num in range(100, 1000):
    if sum([int(i) for i in list(str(num))]) == n:
        print(num)
