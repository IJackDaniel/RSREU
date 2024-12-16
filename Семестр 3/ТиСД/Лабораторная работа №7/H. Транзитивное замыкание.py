import heapq


def dijkstra(graph, start):
    dist = {node: 2009000999 for node in graph}
    dist[start] = 0

    pq = [(0, start)]
    heapq.heapify(pq)

    while pq:
        cur_dist, cur_vertex = heapq.heappop(pq)

        if dist[cur_vertex] < cur_dist:
            continue

        for neighbor, weight in graph[cur_vertex].items():
            d = cur_dist + weight
            if d < dist[neighbor]:
                dist[neighbor] = d
                heapq.heappush(pq, (d, neighbor))

    return dist


n = int(input())

matrix = []
for _ in range(n):
    inp = list(map(int, input().split()))
    matrix.append(inp)

graph = {i: {} for i in range(1, n + 1)}
for i in range(n):
    for j in range(n):
        if matrix[i][j]:
            graph[i + 1][j + 1] = 1

matrix = []
for i in range(1, n + 1):
    a = dijkstra(graph, i)
    # print(a)
    arr = [1 if a[j] != 2009000999 else 0 for j in range(1, n + 1)]
    print(*arr)
