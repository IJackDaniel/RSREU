class Stack:
    def __init__(self):
        self.items = []

    def is_empty(self):
        if not self.items:
            return True
        return False

    def push(self, number):
        self.items.append(number)

    def pop(self):
        if self.is_empty():
            return "error"
        else:
            return self.items.pop()

    def back(self):
        if self.is_empty():
            return "error"
        else:
            return self.items[-1]


way1 = Stack()
way2 = Stack()
blind_alley = Stack()

n = int(input())
train = list(map(int, input().split()))

for i in range(n - 1, -1, -1):
    way1.push(train[i])

while not way1.is_empty():
    cur = way1.pop()
    if blind_alley.is_empty() or blind_alley.back() > cur:
        blind_alley.push(cur)
    else:
        way2.push(blind_alley.pop())
        while (not blind_alley.is_empty()) and (not way2.is_empty()) and (blind_alley.back() - 1 == way2.back()):
            way2.push(blind_alley.pop())
        blind_alley.push(cur)

while not blind_alley.is_empty():
    way2.push(blind_alley.pop())

result = "YES" if way2.items == sorted(train) else "NO"
print(result)
