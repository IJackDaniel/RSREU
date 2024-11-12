# Предмет: Основы систем искусственного интеллекта
# Предметная область: робот в лабиринте
# Работу выполнил: студент группы 3413 - Афонин Даниил Олегович
# Дата начала выполнения работы: 05.10.2024

# Добавление необходимых библиотек
from time import sleep
from functools import cmp_to_key


class Situation:
    def __init__(self, maze, current_coord, target):
        self.maze = maze  # Лабиринт, представленный в виде матрицы
        self.coord = current_coord  # Действующие координаты робота в виде массива из двух значений
        self.target = target  # Целевая координата в виде массива из двух значений
        self.path = []  # Пусть по лабиринту до целевой координаты
        self.current_move = 0

    # Функция проверки целевой ситуации
    def target_situation(self, arr):  # На вход подаётся ситуация
        x, y = arr[0], arr[1]
        return x == self.target[0] and y == self.target[1]  # Выход - результат проверки

    # Функция определения тупика/стенки
    def wall(self, arr):  # На вход подаётся ситуация
        return self.maze[arr[0]][arr[1]] == "2"  # Выход - результат проверки

    # Функция отображения действующей ситуации
    def show_current_situation(self, arr):  # на вход подаётся ситуация
        matrix = self.maze  # Лабиринт
        n = len(matrix)  # Длина лабиринта
        m = len(matrix[0])  # Ширина лабиринта

        for i in range(n):
            for j in range(m):
                current = [i, j]
                if current == arr:  # Проверка координаты на координаты робота
                    print("R", end=" ")
                elif current == self.target:  # Проверка координаты на цель
                    print("x", end=" ")
                elif self.maze[i][j] == "2":  # Проверка координаты на стенку
                    print(chr(9608), end=" ")
                elif current in self.path:
                    print("1", end=" ")
                else:
                    print(self.maze[i][j], end=" ")
            print()
        print()

    def valuation_method(self, *situations):
        situation_1, situation_2 = situations[0], situations[1]
        if ((situation_1[0][0] - self.target[0]) ** 2 + (situation_1[0][1] - self.target[1]) ** 2 <=
                (situation_2[0][0] - self.target[0]) ** 2 + (situation_2[0][1] - self.target[1]) ** 2):
            return -1  # Если первая ситуация хуже
        else:
            return 1  # Если первая ситуация лучше или такая же

    def generate_method(self, arr):
        i, j, n = arr[0][0], arr[0][1], arr[1][0]
        vars = []
        for el in [[i, j + 1], [0]], [[i + 1, j], [0]], [[i - 1, j], [0]], [[i, j - 1], [0]]:
            if el[0][0] < 0 or el[0][0] > 9 or el[0][1] < 0 or el[0][1] > 9 or self.wall([el[0][0], el[0][1]]):
                continue
            else:
                vars.append(el)

        vars.sort(key=cmp_to_key(self.valuation_method))

        if n < len(vars):
            return vars[n]
        else:
            return []  # Нового шага нет

    def dfs(self):
        stack = [[self.coord, [0]]]
        while stack:
            success = self.target_situation(stack[-1][0])
            self.show_current_situation(stack[-1][0])
            sleep(0.5)

            if success:
                for i in range(len(stack)):
                    self.path.append(stack[i][0])
                print(f"Пройденный путь:\n{self.path}")
                print("Путь:")
                self.show_current_situation(stack[-1][0])
                return

            # Формирование новой ситуации
            new_situation = self.generate_method(stack[-1])
            if not new_situation:
                stack[-1][1][0] += 1
                continue
            print(new_situation)

            if any(situation[0][0:2] == new_situation[0][0:2] for situation in stack):
                stack[-1][1][0] += 1
                continue

            if len(new_situation) == 0:
                stack.pop()
                if len(stack) == 0:
                    break
                stack[-1][1][0] += 1
            else:
                stack.append(new_situation)
        print("Решения нет")
        return


if __name__ == "__main__":
    first_maze = [
            ["0", "0", "0", "0", "0", "0", "0", "0", "2", "0"],
            ["0", "2", "2", "2", "2", "2", "2", "0", "2", "0"],
            ["0", "0", "0", "0", "0", "0", "2", "0", "2", "0"],
            ["2", "2", "2", "2", "2", "0", "2", "0", "2", "0"],
            ["2", "0", "0", "0", "2", "0", "2", "0", "2", "0"],
            ["0", "0", "2", "0", "2", "0", "2", "0", "2", "0"],
            ["0", "2", "2", "0", "2", "0", "2", "0", "2", "0"],
            ["0", "2", "0", "0", "2", "0", "2", "0", "2", "0"],
            ["0", "2", "2", "2", "2", "0", "2", "0", "2", "0"],
            ["0", "0", "0", "0", "0", "0", "2", "0", "0", "0"]]
    second_maze = [
            ["0", "2", "0", "2", "0", "2", "0", "0", "0", "0"],
            ["0", "2", "0", "0", "0", "2", "2", "2", "0", "2"],
            ["0", "0", "0", "2", "0", "2", "0", "2", "0", "2"],
            ["2", "2", "2", "2", "0", "2", "0", "2", "0", "0"],
            ["0", "2", "0", "0", "0", "2", "0", "0", "0", "2"],
            ["0", "2", "0", "2", "2", "2", "2", "2", "0", "2"],
            ["0", "0", "0", "2", "0", "2", "0", "0", "0", "2"],
            ["2", "2", "0", "0", "0", "0", "0", "0", "2", "2"],
            ["0", "2", "0", "2", "2", "0", "2", "0", "2", "0"],
            ["0", "0", "0", "2", "0", "0", "2", "0", "0", "0"]]
    third_maze = [
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"],
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"],
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"],
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"],
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"],
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"],
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"],
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"],
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"],
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]]

    print("""Выбор лабиринта:
    1. Коридорный лабиринт
    2. Первый стандартный лабиринт вариант 1
    3. Первый стандартный лабиринт вариант 2
    4. Второй стандартный лабиринт\n""")
    choice_maze = int(input("Ввод: "))

    match choice_maze:
        case 1:
            maze = first_maze
            start_pos = [0, 9]
            target_pos = [7, 2]
        case 2:
            maze = second_maze
            start_pos = [2, 0]
            target_pos = [2, 6]
        case 3:
            maze = third_maze
            start_pos = [0, 0]
            target_pos = [2, 4]
        case _:
            ...

    situation = Situation(maze, start_pos, target_pos)

    situation.dfs()

'''
[
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"],
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"],
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"],
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"],
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"],
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"],
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"],
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"],
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"],
            ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0"]]
'''