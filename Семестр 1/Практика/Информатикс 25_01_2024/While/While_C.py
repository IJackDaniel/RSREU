from math import log2
n = int(input())
r = 1
while r < n:
    r *= 2
print(int(log2(r)))