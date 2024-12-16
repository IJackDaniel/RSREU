n = int(input())

graph = dict()
inp1 = list(map(int, input().split()))
for i in range(1, n + 1):
    if inp1[i - 1] == 0:
        graph[i] = []
    else:
        graph[i] = [inp1[i - 1]] + graph[inp1[i - 1]]

# print(graph)

m = int(input())
for j in range(m):
    pred, child = map(int, input().split())

    result = 0
    if pred in graph[child]:
        result = 1

    print(result)

