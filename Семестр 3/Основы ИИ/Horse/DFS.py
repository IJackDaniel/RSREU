class Knight:
    def __init__(self):
        # Все возможные ходы коня
        self.directions = [
            (2, 1), (2, -1), (-2, 1), (-2, -1),
            (1, 2), (1, -2), (-1, 2), (-1, -2)
        ]

    def is_valid(self, x, y):
        return 0 <= x < 8 and 0 <= y < 8

    def dfs(self, curr_pos, target_pos, path, visited):
        if curr_pos == target_pos:
            return path

        x, y = curr_pos
        visited.add(curr_pos)

        for dx, dy in self.directions:
            next_pos = (x + dx, y + dy)
            if self.is_valid(*next_pos) and next_pos not in visited:
                result = self.dfs(next_pos, target_pos, path + [next_pos], visited)
                if result:
                    return result

        visited.remove(curr_pos)
        return None

    def find_knight_path(self, start, target):
        if not self.is_valid(*start) or not self.is_valid(*target):
            return None
        return self.dfs(start, target, [start], set())


# Пример использования
start_position = tuple(map(int, input("Ввод начальной позиции: ").split()))
target_position = tuple(map(int, input("Ввод начальной позиции: ").split()))

knight = Knight()
path = knight.find_knight_path(start_position, target_position)

if path:
    print("Путь коня:", path)
else:
    print("Нет пути коня.")
