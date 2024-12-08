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


n = int(input())
costs = list(map(int, input().split()))
m = int(input())

graph = {i: {} for i in range(1, n + 1)}
for i in range(m):
    s, f = list(map(int, input().split()))
    graph[s][f] = costs[s - 1]
    graph[f][s] = costs[f - 1]

print(dijkstra(graph, 1, n))


"""
5
3 6 1 7 6 
8
1 2
5 4
5 1
3 4
5 2
2 4
2 3
3 1

5
3 7 2 9 4
4
1 2
1 3
2 3
4 5
"""