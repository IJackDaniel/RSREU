from collections import deque

first = deque()
second = deque()
winners = []

n = int(input())

for _ in range(n):
    command = input().split()
    if command[0] == "-":
        winners.append(first.popleft())
    elif command[0] == "+":
        second.append(command[1])
    else:
        second.appendleft(command[1])

    if len(second) > len(first):
        first.append(second.popleft())

for goblin in winners:
    print(goblin)
