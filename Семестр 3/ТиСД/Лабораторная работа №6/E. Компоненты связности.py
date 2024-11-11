from sys import setrecursionlimit
setrecursionlimit(100000)


def dfs(start):
    visited[start] = True
    for v in graph[start]:
        if not visited[v]:
            dfs(v)


n, m = map(int, input().split())
graph = dict()
for a in range(n):
    graph[a + 1] = []

for _ in range(m):
    inp = input().split()
    graph[int(inp[0])] = graph[int(inp[0])] + [int(inp[1])]
    graph[int(inp[1])] = graph[int(inp[1])] + [int(inp[0])]

already_inp = [False] * (n + 1)
cnt = 0
result1 = []
result2 = []
for i in range(1, n + 1):
    if not already_inp[i]:
        visited = [False] * (n + 1)
        dfs(i)
        cnt += 1
        result1.append(visited.count(True))
        result2.append([str(i) for i in range(1, n + 1) if visited[i]])
        for j in range(1, n + 1):
            if visited[j]:
                already_inp[j] = True

print(cnt)
for k in range(len(result1)):
    print(result1[k])
    print(" ".join(result2[k]))

'''
6 4
3 1
1 2
5 4
2 3
'''