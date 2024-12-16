def floyd_warshall(graph, n):
    dist = [[float('inf')] * n for _ in range(n)]

    for i in range(n):
        for j in range(n):
            if graph[i][j] != -1:
                dist[i][j] = graph[i][j]
            if i == j:
                dist[i][j] = 0

    for k in range(n):
        for i in range(n):
            for j in range(n):
                dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])
    return dist


n, s, f = map(int, input().split())
s -= 1
f -= 1

matrix = []
for _ in range(n):
    matrix.append(list(map(int, input().split())))

result = floyd_warshall(matrix, n)[s][f] if floyd_warshall(matrix, n)[s][f] != float('inf') else -1
print(result)
