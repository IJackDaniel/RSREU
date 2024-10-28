class Heap:
    def __init__(self):
        self.heap = [0]
        self.size = 0

    def shiftUp(self, i):
        while i // 2 > 0 and self.heap[i] < self.heap[i // 2]:
            self.heap[i], self.heap[i // 2] = self.heap[i // 2], self.heap[i]
            i = i // 2

    def Insert(self, x):
        self.heap.append(x)
        self.size += 1
        self.shiftUp(self.size)

    def show(self):
        return self.heap

    def shiftDown(self, i):
        while i * 2 <= self.size:
            j = self.indMixChild(i)
            if self.heap[i] > self.heap[j]:
                self.heap[i], self.heap[j] = self.heap[j], self.heap[i]
            i = j

    def indMixChild(self, i):
        if i * 2 + 1 > self.size:
            return i * 2
        if self.heap[i * 2] < self.heap[i * 2 + 1]:
            return i * 2
        return i * 2 + 1

    def Extract(self):
        if self.size == 0:
            return None
        removed = self.heap[1]
        self.heap[1] = self.heap[self.size]
        self.size = self.size - 1
        self.heap.pop()
        self.shiftDown(1)
        return removed


heap = Heap()

n = int(input())
arr = input().split()
# arr = "8 4 349 3 5 8 34 48 54 345".split()

for elem in arr:
    heap.Insert(int(elem))

while heap.size > 0:
    print(heap.Extract(), end=" ")

