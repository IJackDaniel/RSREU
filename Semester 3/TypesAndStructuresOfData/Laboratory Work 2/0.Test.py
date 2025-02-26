from collections import deque

first = deque()
second = deque()
Oarr = []

n = int(input())
for i in range(n):
    s = input().split()
    if s[0] == '-':
        k = first.popleft()
        Oarr.append(k)
    elif s[0] == '+':
        second.append(s[1])
    else:
        second.appendleft(s[1])

    if len(second) > len(first):
        x = second.popleft()
        first.append(x)

for el in Oarr:
    print(el)