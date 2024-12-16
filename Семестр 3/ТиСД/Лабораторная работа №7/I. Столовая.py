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


n, m = map(int, input().split())

graph = {i: {} for i in range(1, n + 1)}
for i in range(m):
    s, f, w = list(map(int, input().split()))
    graph[s][f] = w
    graph[f][s] = w

mn = 1e20
result = None
for find in range(1, n + 1):
    arr = dijkstra(graph, find).values()
    mx = max(arr)
    if mx < mn:
        mn = min(max(arr), mn)
        result = find
print(result)
