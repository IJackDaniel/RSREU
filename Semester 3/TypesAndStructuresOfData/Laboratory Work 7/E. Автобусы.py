import heapq


def dijkstra(graph, start, end):
    pq = []

    dist = {node: 1e20 for node in graph}
    dist[start] = 0

    heapq.heappush(pq, (0, start))

    while pq:
        cur_time, cur_vertex = heapq.heappop(pq)

        if cur_time > dist[cur_vertex]:
            continue

        for dep_time, arr_time, neighbor in graph[cur_vertex]:
            if dep_time >= cur_time:
                if arr_time < dist[neighbor]:
                    dist[neighbor] = arr_time
                    heapq.heappush(pq, (arr_time, neighbor))

    return dist[end] if dist[end] != 1e20 else -1


n = int(input())  # общее число деревень
d, v = map(int, input().split())  # начало и конец
r = int(input())  # количество рейсов

graph = {i: [] for i in range(1, n + 1)}
for _ in range(r):
    a, time_a, b, time_b = map(int, input().split())  # откуда/во сколько отъезд/куда/во сколько приезд
    graph[a].append((time_a, time_b, b))

result = dijkstra(graph, d, v)
print(result)
