n = int(input())
i = 0
while True:
    r = 2**i
    if r > n:
        break
    print(r, end=" ")
    i += 1