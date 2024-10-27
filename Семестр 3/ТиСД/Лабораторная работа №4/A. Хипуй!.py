class MaxHeap:
    def __init__(self):
        self.heap = [0]
        self.size = 0

    def shiftUp(self, i):
        while i // 2 > 0 and self.heap[i] > self.heap[i // 2]:
            self.heap[i], self.heap[i // 2] = self.heap[i // 2], self.heap[i]
            i = i // 2

    def insert(self, x):
        self.heap.append(x)
        self.size += 1
        self.shiftUp(self.size)

    def indMaxChild(self, i):
        if i * 2 + 1 > self.size:
            return i * 2
        if self.heap[i * 2] > self.heap[i * 2 + 1]:
            return i * 2
        return i * 2 + 1

    def shiftDown(self, i):
        while i * 2 <= self.size:
            j = self.indMaxChild(i)
            if self.heap[i] < self.heap[j]:
                self.heap[i], self.heap[j] = self.heap[j], self.heap[i]
            i = j

    def delMax(self):
        if self.size == 0:
            return None
        removed = self.heap[1]
        self.heap[1] = self.heap[self.size]
        self.size = self.size - 1
        self.heap.pop()
        self.shiftDown(1)
        return removed

    def getMax(self):
        if self.size == 0:
            return None
        return self.heap[1]


# lst = [2, 7, 26, 25, 19, 17, 1, 90, 3, 36, 52]
#
# heap = MaxHeap()
#
# for item in lst:
#     heap.insert(item)
#     print(f"[+{item:3d}]: Heap: {heap.heap[1:]}")
#
# print("Maximum = ", heap.getMax())

heap = MaxHeap()

n = int(input())

for _ in range(n):
    inp = input().split()
    command = int(inp[0])
    match command:
        case 0:
            number = int(inp[1])
            heap.insert(number)
        case 1:
            number = heap.delMax()
            print(number)
        case _:
            continue
