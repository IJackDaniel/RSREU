from collections import deque


def is_valid_move(x, y, N):
    return 1 <= x <= N and 1 <= y <= N


def get_moves(x, y, N):
    moves = []
    for dx, dy in [(2, 1), (1, 2), (-1, 2), (-2, 1), (-2, -1), (-1, -2), (1, -2), (2, -1)]:
        new_x, new_y = x + dx, y + dy
        if is_valid_move(new_x, new_y, N):
            moves.append((new_x, new_y))
    return moves


def min_moves(N, x1, y1, x2, y2):
    visited = set()
    queue = deque([(x1, y1, 0, [])])

    while queue:
        x, y, moves, path = queue.popleft()

        if (x, y) == (x2, y2):
            return moves, path + [(x, y)]

        if (x, y) in visited:
            continue

        visited.add((x, y))

        for new_x, new_y in get_moves(x, y, N):
            queue.append((new_x, new_y, moves + 1, path + [(x, y)]))

    return -1, []


N = int(input())
x1, y1 = map(int, input().split())
x2, y2 = map(int, input().split())

min_moves_count, min_path = min_moves(N, x1, y1, x2, y2)

print(min_moves_count)
for x, y in min_path:
    print(x, y)
