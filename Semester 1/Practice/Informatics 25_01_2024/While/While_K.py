i = 1
lst = []
while i != 0:
    i = int(input())
    if i != 0:
        lst.append(i)

cnt1 = 1
cnt2 = 1
mx = 1
for i in range(0, len(lst)-1):
    if lst[i] < lst[i+1]:
        cnt1 += 1
        mx = max(mx, cnt1)
    else:
        cnt1 = 1
    if lst[i] > lst[i+1]:
        cnt2 += 1
        mx = max(mx, cnt2)
    else:
        cnt2 = 1
print(mx)