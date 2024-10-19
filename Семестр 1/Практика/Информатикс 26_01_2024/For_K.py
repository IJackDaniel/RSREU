x, y, z = map(float, input().split())
n = int(input())
data = []
for i in range(n):
    data.append([float(i) for i in input().split()])
b = 0
j = 0
c = 0
for st in data:
    b += st[0] * st[-1]
    j += st[1] * st[-1]
    c += st[2] * st[-1]

if b+1e-10 - x > 0 and j+1e-10 - y > 0 and c+1e-10 - z > 0:
    print("YES")
else:
    print("NO")
