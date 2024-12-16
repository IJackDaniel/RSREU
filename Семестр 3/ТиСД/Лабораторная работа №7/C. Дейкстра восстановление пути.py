n, s, f = map(int, input().split())
s -= 1
f -= 1

graph = []
for i in range(1, n + 1):
    graph.append(list(map(int, input().split())))

visited = [False] * n
dist = [float('inf')] * n
dist[s] = 0
prev = [-1] * n

for _ in range(n):
    min_dist = float('inf')
    min_index = -1
    for i in range(n):
        if not visited[i] and dist[i] < min_dist:
            min_dist = dist[i]
            min_index = i

    if min_index == -1:
        break

    visited[min_index] = True
    for i in range(n):
        if graph[min_index][i] >= 0:
            new_dist = dist[min_index] + graph[min_index][i]
            if new_dist < dist[i]:
                dist[i] = new_dist
                prev[i] = min_index

if dist[f] == float('inf'):
    print(-1)
else:
    curr = f
    ans = [curr + 1]
    while prev[curr] + 1 > 0:
        curr = prev[curr]
        ans.append(curr + 1)
    print(*ans[::-1])
