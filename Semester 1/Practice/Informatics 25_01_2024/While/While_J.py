i = 1
lst = []
while i != 0:
    i = int(input())
    lst.append(i)

cnt = 1
mx = 1
for i in range(len(lst)-1):
    if lst[i] == lst[i+1]:
        cnt += 1
        mx = max(mx, cnt)
    else:
        cnt = 1
print(mx)