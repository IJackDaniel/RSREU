def f(matrix, n):
    dist = [[float('inf')] * n for _ in range(n)]
    for i in range(n):
        dist[i][i] = 0
        for j in range(n):
            dist[i][j] = matrix[i][j]

    for k in range(n):
        for i in range(n):
            for j in range(n):
                if dist[i][k] != float('inf') and dist[k][j] != float('inf'):
                    dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])

    min_dist = float('inf')
    for i in range(n):
        for j in range(n):
            if i != j:
                min_dist = min(min_dist, dist[i][j])

    for k in range(n):
        for i in range(n):
            for j in range(n):
                if dist[i][k] != float('inf') and dist[k][j] != float('inf'):
                    if dist[i][j] > dist[i][k] + dist[k][j]:
                        return -1

    if min_dist == float('inf'):
        return -1
    else:
        return min_dist


n = int(input())
adjacency_matrix = []
for _ in range(n):
    adjacency_matrix.append(list(map(int, input().split())))

result = f(adjacency_matrix, n)
print(result)
