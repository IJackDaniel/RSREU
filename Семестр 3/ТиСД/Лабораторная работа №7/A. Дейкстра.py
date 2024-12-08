import heapq


def dijkstra(graph, start, end):
    dist = {node: float('inf') for node in graph}
    dist[start] = 0

    pq = [(0, start)]
    heapq.heapify(pq)

    while pq:
        cur_dist, cur_vertex = heapq.heappop(pq)

        if cur_vertex == end:
            return dist[end]

        if dist[cur_vertex] < cur_dist:
            continue

        for neighbor, weight in graph[cur_vertex].items():
            d = cur_dist + weight
            if d < dist[neighbor]:
                dist[neighbor] = d
                heapq.heappush(pq, (d, neighbor))

    return -1


n, s, f = map(int, input().split())

graph = {i: {} for i in range(1, n + 1)}
for i in range(1, n + 1):
    weights = list(map(int, input().split()))
    for j, w in enumerate(weights):
        if w != -1:
            graph[i][j + 1] = w

print(dijkstra(graph, s, f))
