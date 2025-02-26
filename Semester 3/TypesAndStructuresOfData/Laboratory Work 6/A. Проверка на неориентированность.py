n = int(input())
matrix = []
for _ in range(n):
    inp = input().split()
    matrix.append(inp)

flag = True
for i in range(n):
    for j in range(1 + i):
        if matrix[i][j] != matrix[j][i] or (i == j and matrix[i][j] == "1"):
            flag = False
            break


result = "YES" if flag else "NO"
print(result)
