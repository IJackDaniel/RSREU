from collections import deque


def is_within_board(x, y):
    return 0 <= x < 8 and 0 <= y < 8


def knight_moves(start, end):
    # Все возможные ходы коня
    moves = [
        (2, 1), (2, -1), (-2, 1), (-2, -1),
        (1, 2), (1, -2), (-1, 2), (-1, -2)
    ]

    # Очередь для BFS
    queue = deque([(start, [start])])
    print(queue)
    visited = {start}  # Множество для хранения посещённых клеток

    while queue:
        (current, path) = queue.popleft()

        # Если достигли цели, возвращаем путь
        if current == end:
            return path

        # Пробуем все возможные ходы коня
        for move in moves:
            next_position = (current[0] + move[0], current[1] + move[1])

            if is_within_board(next_position[0], next_position[1]) and next_position not in visited:
                visited.add(next_position)
                queue.append((next_position, path + [next_position]))

    return None  # Если путь не найден, хотя в этой задаче это невозможно


# Ввод координат
start_horse = tuple(map(int, input("Ввод начальной позиции: ").split()))
end_horse = tuple(map(int, input("Ввод начальной позиции: ").split()))

# Получение кратчайшего пути
path = knight_moves(start_horse, end_horse)

# Вывод результата
if path:
    for position in path:
        print(position)
else:
    print("Путь не найден!")
