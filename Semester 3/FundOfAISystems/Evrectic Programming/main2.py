# Предмет: Основы систем искусственного интеллекта
# Предметная область: робот в лабиринте
# Работу выполнил: студент группы 3413 - Афонин Даниил Олегович
# Дата начала выполнения работы: 05.10.2024
# Рассмотренные методы:
# Встречный поиск

# Добавление необходимых библиотек
from time import sleep
from functools import cmp_to_key
from collections import deque


class Situation:
    def __init__(self, maze, current_coord, target):
        self.maze = maze  # Лабиринт, представленный в виде матрицы
        self.coord = current_coord  # Действующие координаты робота в виде массива из двух значений
        self.target = target  # Целевая координата в виде массива из двух значений

    # Функция определения тупика/стенки
    # На вход подаётся ситуация
    # Выход - результат проверки ситуации на тупик
    def wall(self, arr):
        return self.maze[arr[0]][arr[1]] == "2"

    # Функция отображения действующей ситуации
    # На вход подаётся ситуация.
    # Функция выводит текущую ситуацию
    def show_current_situation(self, arr1, arr2, flag, path=None):
        if path is None:
            path = []
        if not flag:
            print(arr1[0], arr1[1])
            print(arr2[0], arr2[1])

        matrix = self.maze  # Лабиринт
        n = len(matrix)  # Длина лабиринта
        m = len(matrix[0])  # Ширина лабиринта

        for i in range(n):
            for j in range(m):
                current = [i, j]
                if current == arr1 or current == arr2:  # Проверка координаты на координаты робота
                    print("R", end=" ")
                elif current == self.target:  # Проверка координаты на цель
                    print("x", end=" ")
                elif self.maze[i][j] == "2":  # Проверка координаты на стенку
                    print(chr(9608), end=" ")
                elif current in path:  # Проверки на путь
                    print("1", end=" ")
                else:
                    print(self.maze[i][j], end=" ")
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

        # Возвращение всех возможны ходов
        return variants

    # Проверка ситуации на встречу
    # Вход: два графа
    # Выход: результат проверки
    def target_situation(self, first_tree, second_tree):
        for key1 in first_tree.keys():
            for key2 in second_tree.keys():
                x1, y1 = key1
                x2, y2 = key2
                if ((x1 + 1, y1) == key2 or (x1 - 1, y1) == key2
                        or (x1, y1 + 1) == key2 or (x1, y1 - 1) == key2
                        or ((x2 + 1, y2) == key1 or (x2 - 1, y2) == key1
                            or (x2, y2 + 1) == key1 or (x2, y2 - 1) == key1) or key1 == key2):
                    return True
        return False

    # Метод поиска общей вершины
    # Вход: два дерева
    # Выход: общая ситуации
    def found_common_vertex(self, tree1, tree2):
        for key1 in tree1.keys():
            for key2 in tree2.keys():
                x1, y1 = key1
                x2, y2 = key2
                if ((x1 + 1, y1) == key2 or (x1 - 1, y1) == key2
                        or (x1, y1 + 1) == key2 or (x1, y1 - 1) == key2
                        or ((x2 + 1, y2) == key1 or (x2 - 1, y2) == key1
                            or (x2, y2 + 1) == key1 or (x2, y2 - 1) == key1) or key1 == key2):
                    return key1, key2

    # Метод поиска решения
    # Вход: дерево ситуаций
    # Выход: путь, который проделал робот при достижении цели
    def found_solution_method(self, res_sol1, res_sol2):
        common_s1, common_s2 = self.found_common_vertex(res_sol1, res_sol2)
        step = common_s1

        path = []
        while step is not None:  # пока данная ситуация не None
            path.append(step)  # добавление в путь ситуации
            step = res_sol1[tuple(step)]  # новая ситуация - это ключ в словаре
        path = path[::-1]
        step = common_s2
        path.append(common_s2)
        while step is not res_sol2[tuple(self.target)]:
            path.append(res_sol2[step])
            step = res_sol2[tuple(step)]

        # Вывод
        print("Путь: ")
        print(path)
        m = self.maze  # Запись из ситуации лабиринта
        n1 = len(m)  # Длина лабиринта
        n2 = len(m[0])  # Ширина лабиринта

        for i in range(n1):
            for j in range(n2):
                if tuple([i, j]) in path:  # Если ситуация в пути
                    print("1", end=" ")
                elif self.maze[i][j] == "2":  # Проверка координаты на стенку
                    print(chr(9608), end=" ")
                elif [i, j] == self.target:  # Проверка координаты на цель
                    print("x", end=" ")
                else:  # Вывод в остальных случаях
                    print(self.maze[i][j], end=" ")
            print()
        print()

    # Метод встречного пути
    # Вход: начальная ситуация
    # Выход: путь, который проделал робот при достижении цели
    # available_path1 - для начальной ситуации
    # available_path2 - для целевой ситуации
    def meeting_method(self):
        available_path1 = deque()  # область памяти, содержащая направления, которые можно посетить
        available_path1.append(self.coord)  # добавление ситуации в область памяти
        result_path1 = {tuple(self.coord): None}  # выделяется память под каждую ветку дерева

        available_path2 = deque()  # область памяти, содержащая направления, которые можно посетить
        available_path2.append(self.target)  # добавление ситуации в область памяти
        result_path2 = {tuple(self.target): None}  # выделяется память под каждую ветку дерева

        while available_path1 and available_path2:
            success = self.target_situation(result_path1, result_path2)
            # self.show_current_situation(available_path1[0], available_path2[0], False)  # отображение расположения элементов
            # sleep(0.5)

            if success:
                self.found_solution_method(result_path1, result_path2)
                return  # выход из функции

            new_moves1 = self.generate_method(available_path1[0])  # генерация новых шагов
            for move in new_moves1:  # прохождение по всем новым шагам по отдельности
                if all(list(move) != list(key) for key in result_path1.keys()):  # если шага не было в очереди
                    available_path1.append(move)
                    result_path1[tuple(move)] = tuple(available_path1[0])

            new_moves2 = self.generate_method(available_path2[0])  # генерация новых шагов
            for move in new_moves2:  # прохождение по всем новым шагам по отдельности
                if all(list(move) != list(key) for key in result_path2.keys()):  # если шага не было в очереди
                    available_path2.append(move)
                    result_path2[tuple(move)] = tuple(available_path2[0])

            available_path1.popleft()  # удалить использованную ситуацию
            available_path2.popleft()  # удалить использованную ситуацию

        # Если очереди опустели, и к тому же решение не найдено
        print("Нет решения!")
        return


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
    situation = Situation(selected_maze, start_pos, target_pos)

    # Запуск метода встречного поиска
    situation.meeting_method()


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
