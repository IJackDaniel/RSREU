from collections import deque


deq = deque()
n, k = map(int, input().split())
inp = list(map(int, input().split()))

for i in range(n):
    if deq and i - deq[0][0] >= k:
        deq.popleft()
    while deq and deq[-1][1] > inp[i]:
        deq.pop()
    deq.append((i, inp[i]))
    if i + 1 >= k:
        print(deq[0][1])
