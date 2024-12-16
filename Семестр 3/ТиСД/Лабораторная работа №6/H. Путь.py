from collections import deque


def bfs(graph, s, n):
    INF = float('inf')
    dist = [INF] * n
    dist[s] = 0
    pred = [-1] * n
    q = deque([s])
    while q:
        node = q.popleft()
        for neighbor in graph[node]:
            if dist[neighbor] > dist[node] + 1:
                dist[neighbor] = dist[node] + 1
                pred[neighbor] = node
                q.append(neighbor)
    return pred


def reconstruct_path(pred, finish):
    path = [finish]
    ver = pred[finish]
    while ver != -1:
        path.append(ver)
        ver = pred[ver]
    return path[::-1]


n = int(input())
connections_matrix = []
for i in range(n):
    connections_matrix.append(input().split())

graph = {i: set() for i in range(n)}
for i in range(n):
    for j in range(n):
        if connections_matrix[i][j] == '1':
            graph[i].add(j)
# print(graph)

start, end = map(lambda x: int(x) - 1, input().split())
if start == end:
    print(0)
else:
    preds = bfs(graph, start, n)
    # print(preds)
    path = reconstruct_path(preds, end)

    if len(path) == 1:
        print(-1)
    else:
        print(len(path)-1)
        for vertex in path:
            print(vertex + 1, end=' ')
