n, m = map(int, input().split())
matrix = [[0 for q in range(m)] for r in range(n)]

i = 0
j = 0
num = 1
faze = 1
fg = 0
iteration = 0
while iteration != m*n:
    matrix[i][j] = num
    num += 1

    if faze == 1:
        j += 1
        if j > m-1 - fg:
            j -= 1
            i += 1
            faze = 2
    elif faze == 2:
        i += 1
        if i > n-1 - fg:
            i -= 1
            j -= 1
            faze = 3
    elif faze == 3:
        j -= 1
        if j < 0 + fg:
            j += 1
            i -= 1
            faze = 4
            fg += 1
    elif faze == 4:
        i -= 1
        if i < 0 + fg:
            i += 1
            j += 1
            faze = 1
    iteration += 1

for a in range(n):
    for b in range(m):
        print("{:4d}".format(matrix[a][b]), end="")
    print()
