n1 = int(input())
n2 = int(input())
cnt = 0
if n1 != 0 and n2 != 0:
    n3 = int(input())
    while n3 != 0:
        if n1 < n2 and n2 > n3:
            cnt += 1
        n1, n2 = n2, n3
        n3 = int(input())
print(cnt)