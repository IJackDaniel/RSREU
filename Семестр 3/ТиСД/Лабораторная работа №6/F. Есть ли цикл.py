from sys import setrecursionlimit
setrecursionlimit(100000)


def dfs(start):
    global find1
    color[start] = "Grey"
    for v in graph[start]:
        if color[v] == "White":
            dfs(v)
        elif color[v] == "Grey":
            find = True
    color[start] = "Black"


n = int(input())
matrix = []
for _ in range(n):
    inp = input().split()
    matrix.append(inp)

graph = dict()
for i in range(n):
    result = []
    for j in range(n):
        if matrix[i][j] == "1":
            result.append(j + 1)
    graph[i + 1] = set(result)

find1 = False
for i in range(1, n + 1):
    color = ["White"] * (n + 1)
    dfs(i)

print(int(find1))
