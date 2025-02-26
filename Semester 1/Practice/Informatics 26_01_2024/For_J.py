n = int(input())
arr = [int(i) for i in input().split()]
mx = 0
cnt = 0
for el in arr:
    if el > 0:
        cnt += 1
        mx = max(mx, cnt)
    else:
        cnt = 0
print(mx)
