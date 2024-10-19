# Генератор возможных ходов
def move_creator(posx, posy):
    global path
    moves = [(-1, 2), (1, 2), (2, 1), (2, -1), (1, -2), (-1, -2), (-2, -1), (-2, 1)]
    result = []
    for move in moves:
        dx, dy = move
        if 0 <= posx + dx <= 7 and 0 <= posy + dy <= 7 and (posx + dx, posy + dy) not in path:
            result.append(move)
    return result


# Функция вывода текущей ситуации
def situation():
    for i in range(len(field)):
        line = "  ".join(field[i])
        print(line)


def knights_move(current, target):
    global path, field

    x, y = current

    print("current situation", (x, y))
    # print("path ", path)
    # print("field:")
    # situation()
    print()

    moves = move_creator(x, y)

    for move in moves:
        dx, dy = move

        path.append((x + dx, y + dy))

        field[x][y] = "1"
        field[x + dx][y + dy] = "H"

        if (x + dx, y + dy) == target:
            return 1
        else:
            a = knights_move((x + dx, y + dy), target)
            if a == 0:
                field[path[-1][0]][path[-1][1]] = "0"
                path = path[:-1]

            elif a == 1:
                return 1
    return 0


# start_position = tuple(map(int, input("Ввод начальной позиции: ").split()))
# target_position = tuple(map(int, input("Ввод конечной позиции: ").split()))
start_position = (1, 1)
target_position = (1, 5)

path = [start_position]

field = []
for _ in range(8):
    field.append(["+"] * 8)
field[start_position[0]][start_position[1]] = "H"  # Робот
field[target_position[0]][target_position[1]] = "x"  # Цель

r = knights_move(start_position, target_position)
print(f"Путь элемента {path}")
