from heapq import heappop, heappush, heapify
from sys import stdin

heap = []
heapify(heap)

strings = stdin.readlines()
for inp in strings:
    inp = inp.split()
    cmd = inp[0]
    if cmd == "CLEAR":
        heap.clear()
    elif cmd == "ADD":
        num = int(inp[1])
        heappush(heap, -num)
    else:
        if len(heap) != 0:
            outp = -heappop(heap)
            print(outp)
        else:
            print("CANNOT")
