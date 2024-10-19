a = int(input())
b = int(input())
c = int(input())
d = int(input())

for num in range(a + (c-a%d)%d, b+1, d):
    print(num)
# A = 1 000 000 000
# B = 1 000 000 000
# C = 0
# D = 999 999 999
