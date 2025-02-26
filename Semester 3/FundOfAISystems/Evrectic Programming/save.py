# Предмет: Основы систем искусственного интеллекта
# Предметная область: робот в лабиринте
# Работу выполнил: студент группы 3413 - Афонин Даниил Олегович
# Дата начала выполнения работы: 05.10.2024
# Рассмотренные методы:
# Метод равных цен

# Добавление необходимых библиотек
from time import sleep
from random import uniform
import heapq


class Situation:
    def __init__(self, maze, maze_with_val, current_coord, target):
        self.maze = maze  # Лабиринт, представленный в виде матрицы
        self.maze_v = maze_with_val
        self.coord = current_coord  # Действующие координаты робота в виде массива из двух значений
        self.target = target  # Целевая координата в виде массива из двух значений

    # Функция проверки целевой ситуации
    # На вход подаётся ситуация
    # На выход поступает результат проверки ситуации на целевую
    def target_situation(self, arr):
        x, y = arr[0], arr[1]
        return x == self.target[0] and y == self.target[1]

    # Функция определения тупика/стенки
    # На вход подаётся ситуация
    # Выход - результат проверки ситуации на тупик
    def wall(self, arr):
        return self.maze[arr[0]][arr[1]] == "2"

    # Функция отображения действующей ситуации
    # На вход подаётся ситуация.
    # Функция выводит текущую ситуацию
    def show_current_situation(self, arr, flag, path=None):
        if path is None:
            path = []
        if not flag:
            print(arr[0], arr[1])
        x, y = arr[1]
        matrix = self.maze  # Лабиринт
        n = len(matrix)  # Длина лабиринта
        m = len(matrix[0])  # Ширина лабиринта

        for i in range(n):
            for j in range(m):
                current = [i, j]
                if current == [x, y]:  # Проверка координаты на координаты робота
                    print("  R  ", end=" ")
                elif current == self.target:  # Проверка координаты на цель
                    print("  x  ", end=" ")
                elif self.maze[i][j] == "2":  # Проверка координаты на стенку
                    print(f"  {chr(9608)}  ", end=" ")
                elif current in path:  # Проверки на путь
                    print("  1  ", end=" ")
                else:
                    print(f"{self.maze_v[i][j]:^5}", end=" ")
            print()
        print()

    # Порождающая функция
    # Вход: ситуация
    # Промежуточные данные:
    # variants - возможные ходы
    # Выход: новые ходы
    def generate_method(self, arr):
        i, j = arr[0], arr[1]
        variants = []
        for el in [i, j + 1], [i + 1, j], [i - 1, j], [i, j - 1]:
            if el[0] < 0 or el[0] > 9 or el[1] < 0 or el[1] > 9 or self.wall([el[0], el[1]]):
                continue
            else:
                variants.append(el)  # добавление в вариации ходов нового хода

        return variants

    # Метод поиска решения
    # Вход: дерево ситуаций
    # Выход: путь, который проделал робот при достижении цели
    def found_solution_method(self, res_sol, val):
        step = self.target
        path = []
        while step is not None:  # пока данная ситуация не None
            path.append(step)  # добавление в путь ситуации
            step = res_sol[tuple(step)]  # новая ситуация - это ключ в словаре
        path = path[::-1]

        # Вывод
        print("Сумма: ")
        print(round(val, 3))
        print("Путь: ")
        print(path)
        m = self.maze  # Запись из ситуации лабиринта
        n1 = len(m)  # Длина лабиринта
        n2 = len(m[0])  # Ширина лабиринта

        for i in range(n1):
            for j in range(n2):
                if [i, j] in path:  # Если ситуация в пути
                    print("  1  ", end=" ")
                elif self.maze[i][j] == "2":  # Проверка координаты на стенку
                    print(f"  {chr(9608)}  ", end=" ")
                elif [i, j] == self.target:  # Проверка координаты на цель
                    print("  x  ", end=" ")
                else:  # Вывод в остальных случаях
                    print(f"{self.maze_v[i][j]:^5}", end=" ")
            print()
        print()

    def method_of_equal_sums(self):
        available_path = []  # область памяти, содержащая направления, которые можно посетить
        heapq.heapify(available_path)  # превращение области памяти в кучу

        heapq.heappush(available_path, (0, self.coord))  # добавление ситуации в область памяти

        result_path = {tuple(self.coord): None}  # выделяется память под каждую ветку дерева

        while available_path:
            # self.show_current_situation(available_path[0], False)
            # sleep(0.5)
            val, curr_sit = heapq.heappop(available_path)
            success = self.target_situation(curr_sit)

            if success:
                print("Цель достигнута!")
                self.found_solution_method(result_path, val)
                return

            new_moves = self.generate_method(curr_sit)
            for move in new_moves:
                if tuple(move) not in result_path.keys():
                    heapq.heappush(available_path, (float(self.maze_v[move[0]][move[1]]) + float(val), move))
                    result_path[tuple(move)] = curr_sit

        print("Нет решения!")
        return


def randomize(maze, start, target):
    maze_with_values = [["0"] * len(maze[0]) for _ in range(len(maze))]

    for i in range(len(maze)):
        for j in range(len(maze[0])):
            if maze[i][j] == "2":
                maze_with_values[i][j] = "2"
            elif [i, j] == start or [i, j] == target:
                maze_with_values[i][j] = "0.0"
            elif maze[i][j] == "0":
                maze_with_values[i][j] = str(round(uniform(0, 0.9), 2))
    return maze_with_values


if __name__ == "__main__":
    # Лабиринты (2 - стенка; 0 - ничего)

    # Первый лабиринт - коридорный
    # В этом лабиринте нет никаких разветвлений
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
    # Второй лабиринт
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
    # Третий лабиринт
    third_maze = [
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
    # Четвёртый лабиринт - пустой
    # В этом лабиринте вообще нет стенок
    fourth_maze = [
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

    # Выбор лабиринта пользователем
    print("""Выбор лабиринта:
    1. Коридорный лабиринт
    2. Первый стандартный лабиринт вариант 1
    3. Первый стандартный лабиринт вариант 2
    4. Пустой лабиринт\n""")
    choice_maze = int(input("Ввод: "))

    # Конструкция выбора лабиринта
    match choice_maze:
        case 1:
            selected_maze = first_maze
            start_pos = [0, 9]
            target_pos = [7, 2]
        case 2:
            selected_maze = second_maze
            start_pos = [2, 0]
            target_pos = [2, 6]
        case 3:
            selected_maze = third_maze
            start_pos = [0, 9]
            target_pos = [0, 2]
        case 4:
            selected_maze = fourth_maze
            start_pos = [1, 1]
            target_pos = [5, 8]
        case _:
            print("Некорректный выбор лабиринта!")
            exit(2)

    # Определение ситуации
    maze_with_val = randomize(selected_maze, start_pos, target_pos)

    situation = Situation(selected_maze, maze_with_val, start_pos, target_pos)

    # Запуск метода равных цен
    situation.method_of_equal_sums()

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
