from heapq import heappop, heappush, heapify

n = int(input())
heap = list(map(int, input().split()))
heapify(heap)

cost = 0
while len(heap) != 1:
    num1 = heappop(heap)
    num2 = heappop(heap)
    num = num1 + num2
    cost += num * 0.05
    heappush(heap, num)
print(cost)
