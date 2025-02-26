n, m = map(int, input().split())
matrix = [[0 for q in range(m)] for r in range(n)]

i = 0
j = 0
num = 1
faze = True
iteration = 0
while iteration != m*n:
    matrix[i][j] = num
    num += 1
    if faze:
        j += 1
    else:
        j -= 1

    if j > m-1:
        i += 1
        j = m-1
        faze = False
    elif j < 0:
        j = 0
        i += 1
        faze = True
    iteration += 1

for a in range(n):
    for b in range(m):
        print("{:4d}".format(matrix[a][b]), end="")
    print()
