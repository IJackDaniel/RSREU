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


num = int(input())
for _ in range(num):
    n, m = map(int, input().split())

    graph = {i: {} for i in range(0, n)}
    for i in range(m):
        s, f, w = list(map(int, input().split()))
        graph[s][f] = w
        graph[f][s] = w

    vertex = int(input())

    print(*dijkstra(graph, vertex).values())
