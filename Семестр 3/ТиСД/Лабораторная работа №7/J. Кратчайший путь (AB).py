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


def matr_weight(data, n):
    matrix = [[-1 for _ in range(n)] for _ in range(n)]
    for i in range(n):
        matrix[i][i] = 0
    for a, b in data.keys():
        matrix[a][b] = data[(a, b)]
    return matrix


n, k = map(int, input().split())

matrix = [[-1 for _ in range(n)] for _ in range(n)]
for i in range(k):
    a, b, l = list(map(int, input().split()))
    matrix[a - 1][b - 1] = l
    matrix[b - 1][a - 1] = l

s, f = map(int, input().split())
s -= 1
f -= 1

result = floyd_warshall(matrix, n)[s][f] if floyd_warshall(matrix, n)[s][f] != float('inf') else -1
# result = floyd_warshall(graph, n)
print(result)
