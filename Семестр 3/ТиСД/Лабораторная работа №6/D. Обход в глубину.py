from sys import setrecursionlimit
setrecursionlimit(100000)


def dfs(start):
    visited[start] = True
    for v in graph[start]:
        if not visited[v]:
            dfs(v)


n, s = map(int, input().split())
matrix = []
for _ in range(n):
    inp = input().split()
    matrix.append(inp)

# n, s = 3, 1
# matrix = [["0", "1", "1"],
#           ["1", "0", "0"],
#           ["1", "0", "0"]]

visited = [False] * (n + 1)

graph = dict()
for i in range(n):
    result = []
    for j in range(n):
        if matrix[i][j] == "1":
            result.append(j + 1)
    graph[i + 1] = set(result)

dfs(s)
print(visited.count(True))
