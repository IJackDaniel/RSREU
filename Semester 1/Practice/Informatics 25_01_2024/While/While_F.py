i = 1
lst = []
while i != 0:
    i = int(input())
    lst.append(i)

cnt = 0
for i in range(len(lst)-1):
    if lst[i+1] > lst[i]:
        cnt += 1
print(cnt)