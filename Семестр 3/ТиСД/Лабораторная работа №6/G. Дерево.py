from sys import setrecursionlimit

setrecursionlimit(100000)


def basic_dfs(start):
    global visited
    visited[start] = True
    for v in graph[start]:
        if not visited[v]:
            basic_dfs(v)


def paint_dfs(vertex, parent):
    global cycle
    color[vertex] = "Gray"
    for v in graph[vertex]:
        if v == parent:
            continue
        if color[v] == "White":
            paint_dfs(v, vertex)
        if color[v] == "Gray":
            cycle = True
    color[vertex] = "Black"


n, m = map(int, input().split())

if n == m + 1:
    graph = dict()

    for a in range(n):
        graph[a + 1] = []

    for _ in range(m):
        inp = input().split()
        graph[int(inp[0])] = graph[int(inp[0])] + [int(inp[1])]
        graph[int(inp[1])] = graph[int(inp[1])] + [int(inp[0])]

    color = ["White"] * (n + 1)
    cycle = False
    for i in range(1, n + 1):
        if color[i] == "White":
            paint_dfs(i, 0)
            if cycle:
                break

    visited = [False] * (n + 1)
    basic_dfs(1)

    print("YES" if not cycle and all(visited[1:]) else "NO")
else:
    print("NO")
