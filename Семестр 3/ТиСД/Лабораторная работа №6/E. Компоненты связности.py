from sys import setrecursionlimit

setrecursionlimit(100000)


def dfs(start):
    visited[start] = True
    component_v.append(str(start))
    for v in graph[start]:
        if not visited[v]:
            dfs(v)


n, m = map(int, input().split())
graph = dict()
for i in range(n):
    graph[i + 1] = []

for k in range(m):
    i, j = map(int, input().split())
    graph[i].append(j)
    graph[j].append(i)

visited = [False] * (n + 1)
count = 0
count_v = []
count_v_in_one_component = []
for a in range(1, n + 1):
    if not visited[a]:
        component_v = []
        dfs(a)
        count += 1
        count_v.append(len(component_v))
        count_v_in_one_component.append(component_v)

print(count)
for k in range(len(count_v)):
    print(count_v[k])
    print(" ".join(count_v_in_one_component[k]))
