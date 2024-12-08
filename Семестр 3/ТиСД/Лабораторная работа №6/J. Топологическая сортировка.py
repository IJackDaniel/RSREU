def is_topological_sort(n, edges, permutation):
    graph = [[] for _ in range(n)]

    for u, v in edges:
        graph[u - 1].append(v - 1)

    position = [0] * n
    for idx, node in enumerate(permutation):
        position[node - 1] = idx

    for u in range(n):
        for v in graph[u]:
            if position[u] > position[v]:
                return "NO"

    return "YES"


n, m = map(int, input().split())
edges = [tuple(map(int, input().split())) for _ in range(m)]
permutation = list(map(int, input().split()))

result = is_topological_sort(n, edges, permutation)
print(result)
