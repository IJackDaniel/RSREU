# Предмет: Основы систем искусственного интеллекта
# Предметная область: робот в лабиринте
# Работу выполнил: студент группы 3413 - Афонин Даниил Олегович
# Дата начала выполнения работы: 05.10.2024
# Рассмотренные методы:
# Поиск в глубину (dfs)
# Поиск в глубину с градиентом
# Поиск в ширину (bfs)

# Добавление необходимых библиотек
from time import sleep
from functools import cmp_to_key
from collections import deque
from prettytable import PrettyTable


class Situation:
    def __init__(self, maze, current_coord, target):
        self.maze = maze                # Лабиринт, представленный в виде матрицы
        self.coord = current_coord      # Действующие координаты робота в виде массива из двух значений
        self.target = target            # Целевая координата в виде массива из двух значений

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
                elif current in path:  # Проверки на путь
                    print("1", end=" ")
                else:
                    print(self.maze[i][j], end=" ")
            print()
        print()

    # Порождающая функция для dfs с оценкой ситуации
    # На вход подаётся ситуация и номер хода
    # На выход передаётся новый шаг, если такой существует
    def generate_method_dfs(self, arr):
        i, j, n = arr[0][0], arr[0][1], arr[1][0]
        variants = []
        for el in [[i, j + 1], [0]], [[i + 1, j], [0]], [[i - 1, j], [0]], [[i, j - 1], [0]]:
            if el[0][0] < 0 or el[0][0] > 9 or el[0][1] < 0 or el[0][1] > 9 or self.wall([el[0][0], el[0][1]]):
                continue
            else:
                variants.append(el)

        if n < len(variants):
            return variants[n]  # Возврат одного хода
        else:
            return []  # Нового шага нет

    # Оценочная функция по градиенту (производит сравнение ситуаций).
    # На вход подаётся набор ситуаций
    # На выход передаётся результат проверки:
    # -1 - если первая ситуация хуже
    #  1 - если первая ситуация лучше или такая же
    def valuation_method(self, *situations):
        situation_1, situation_2 = situations[0], situations[1]
        if ((situation_1[0][0] - self.target[0]) ** 2 + (situation_1[0][1] - self.target[1]) ** 2 <=
                (situation_2[0][0] - self.target[0]) ** 2 + (situation_2[0][1] - self.target[1]) ** 2):
            return -1
        else:
            return 1

    # Порождающая функция для dfs с оценкой ситуации
    # На вход подаётся ситуация и номер хода
    # На выход передаётся новый шаг, если такой существует
    def generate_method_dfs_with_gradient(self, arr):
        i, j, n = arr[0][0], arr[0][1], arr[1][0]
        variants = []
        for el in [[i, j + 1], [0]], [[i + 1, j], [0]], [[i - 1, j], [0]], [[i, j - 1], [0]]:
            if el[0][0] < 0 or el[0][0] > 9 or el[0][1] < 0 or el[0][1] > 9 or self.wall([el[0][0], el[0][1]]):
                continue
            else:
                variants.append(el)

        variants.sort(key=cmp_to_key(self.valuation_method))  # Сортировка ходов оценочной функцией

        if n < len(variants):
            return variants[n]                                # Возврат одного хода
        else:
            return []                                         # Нового шага нет

    # Порождающая функция для bfs
    # Вход: ситуация
    # Промежуточные данные:
    # variants - возможные ходы
    # Выход: новые ходы
    def generate_method_bfs(self, arr):
        i, j = arr[0], arr[1]
        variants = []
        for el in [i, j + 1], [i + 1, j], [i - 1, j], [i, j - 1]:
            if el[0] < 0 or el[0] > 9 or el[1] < 0 or el[1] > 9 or self.wall([el[0], el[1]]):
                continue
            else:
                variants.append(el)  # добавление в вариации ходов нового хода

        # Возвращение всех возможны ходов
        return variants

    # Метод поиска решения для BFS
    # Вход: дерево ситуаций
    # Выход: путь, который проделал робот при достижении цели
    def found_solution_method(self, res_sol):
        step = self.target                      # начальная ситуация - целевая
        path = []                               # инициализация пути
        while step is not None:                 # пока данная ситуация не None
            path.append(step)                   # добавление в путь ситуацию
            step = res_sol[tuple(step)]         # новая ситуация - это ключ в словаре
        path = path[::-1]                       # реверсирование пути

        # Вывод
        print("Путь: ")
        print(path)
        m = self.maze                           # Запись из ситуации лабиринта
        n1 = len(m)                             # Длина лабиринта
        n2 = len(m[0])                          # Ширина лабиринта

        for i in range(n1):
            for j in range(n2):
                if tuple([i, j]) in path:       # Если ситуация в пути
                    print("1", end=" ")
                elif self.maze[i][j] == "2":    # Проверка координаты на стенку
                    print(chr(9608), end=" ")
                elif [i, j] == self.target:     # Проверка координаты на цель
                    print("x", end=" ")
                else:                           # Вывод в остальных случаях
                    print(self.maze[i][j], end=" ")
            print()
        print()

    def print_table(self, from_dfs, from_grad, from_bfs, from_equal, from_meeting):
        accuracy = 2
        D_DFS, L_DFS, N_DFS = from_dfs
        R_DFS = round(N_DFS / L_DFS, accuracy)
        W_DFS = round((N_DFS**(1 / L_DFS)), accuracy)

        D_GRAD, L_GRAD, N_GRAD = from_grad
        R_GRAD = round(N_GRAD / L_GRAD, accuracy)
        W_GRAD = round((N_GRAD ** (1 / L_GRAD)), accuracy)

        D_BFS, L_BFS, N_BFS = from_bfs
        R_BFS = round(N_BFS / L_BFS, accuracy)
        W_BFS = round((N_BFS ** (1 / L_BFS)), accuracy)

        D_EQU, L_EQU, N_EQU = from_equal
        R_EQU = round(N_EQU / L_EQU, accuracy)
        W_EQU = round((N_EQU ** (1 / L_EQU)), accuracy)

        D_MEET, L_MEET, N_MEET = from_meeting
        R_MEET = round(N_MEET / L_MEET, accuracy)
        W_MEET = round((N_MEET ** (1 / L_MEET)), accuracy)

        mytable = PrettyTable()
        mytable.field_names = ["Метод", "Глубина поиска", "Длина пути", "Общее число порожденных вершин",
                               "Разветвленность дерева", "Направленность поиска"]
        mytable.add_row(["В глубину", D_DFS, L_DFS, N_DFS, R_DFS, W_DFS])
        mytable.add_row(["По градиенту", D_GRAD, L_GRAD, N_GRAD, R_GRAD, W_GRAD])
        mytable.add_row(["В ширину", D_BFS, L_BFS, N_BFS, R_BFS, W_BFS])
        mytable.add_row(["Равных цен", D_EQU, L_EQU, N_EQU, R_EQU, W_EQU])
        mytable.add_row(["Двунаправленный", D_MEET, L_MEET, N_MEET, R_MEET, W_MEET])
        print(mytable)

    # Метод поиска в глубину
    # Вход: начальная ситуация
    # Промежуточные данные:
    # stack - стек со всеми ситуациями на пути
    # success - булева переменная, которая хранит результат проверки ситуации на целевую
    # new_situation - новая порождаемая ситуация
    # Выход: путь, который проделал робот для достижения цели
    def dfs(self):
        stack = [[self.coord, [0]]]  # Стек для хранения ситуаций
        while stack:  # Цикл, пока стек не пустой
            success = self.target_situation(stack[-1][0])       # Проверка на достижение целевой ситуации
            # self.show_current_situation(stack[-1][0], False)  # Отображение лабиринта, робота и цели
            # sleep(0.5)                                        # Задержка вывода на 0,5 секунд

            if success:                                         # Проверка на достижение цели
                path = []
                for i in range(len(stack)):
                    path.append(stack[i][0])

                print(f"Пройденный путь:\n{path}")              # Вывод пройденного пути
                print("Путь:")
                self.show_current_situation(stack[-1][0], True, path)
                return

            new_situation = self.generate_method_dfs(stack[-1])  # Формирование новой ситуации

            try:                                                # Проверка: попадает ли порождённая ситуация в стек
                if any(last_situation[0][0:2] == new_situation[0][0:2] for last_situation in stack):
                    stack[-1][1][0] += 1
                    continue
            except IndexError:
                pass

            if len(new_situation) == 0:                         # Если новых ходов больше нет
                stack.pop()
                if len(stack) == 0:                             # Если стек оказался пустым
                    break
                stack[-1][1][0] += 1                            # Увеличиваем номер хода на 1
            else:                                               # Если ходы есть
                stack.append(new_situation)                     # Добавление новой ситуации в стек
        print("Решения нет")                                    # Вывод сообщения, если решение не найдено
        return

    # Метод поиска в глубину с оценочной функцией
    # Вход: начальная ситуация
    # Промежуточные данные:
    # stack - стек со всеми ситуациями на пути
    # success - булева переменная, которая хранит результат проверки ситуации на целевую
    # new_situation - новая порождаемая ситуация
    # Выход: путь, который проделал робот для достижения цели
    def dfs_with_gradient(self):
        stack = [[self.coord, [0]]]                             # Стек для хранения ситуаций
        while stack:                                            # Цикл, пока стек не пустой
            success = self.target_situation(stack[-1][0])       # Проверка на достижение целевой ситуации
            # self.show_current_situation(stack[-1][0], False)  # Отображение лабиринта, робота и цели
            # sleep(0.5)                                        # Задержка вывода на 0,5 секунд

            if success:                                         # Проверка на достижение цели
                path = []
                for i in range(len(stack)):
                    path.append(stack[i][0])

                print(f"Пройденный путь:\n{path}")              # Вывод пройденного пути
                print("Путь:")
                self.show_current_situation(stack[-1][0], True, path)
                return

            new_situation = self.generate_method_dfs_with_gradient(stack[-1])  # Формирование новой ситуации

            try:        # Проверка: попадает ли порождённая ситуация в стек
                if any(last_situation[0][0:2] == new_situation[0][0:2] for last_situation in stack):
                    stack[-1][1][0] += 1
                    continue
            except IndexError:
                pass

            if len(new_situation) == 0:         # Если новых ходов больше нет
                stack.pop()
                if len(stack) == 0:             # Если стек оказался пустым
                    break
                stack[-1][1][0] += 1            # Увеличиваем номер хода на 1
            else:                               # Если ходы есть
                stack.append(new_situation)     # Добавление новой ситуации в стек
        print("Решения нет")                    # Вывод сообщения, если решение не найдено
        return

    # Метод поиска в ширину
    # Вход: начальная ситуация
    # Выход: путь, который проделал робот при достижении цели
    def bfs(self):
        available_path = deque()                    # область памяти, содержащая направления, которые можно посетить
        available_path.append(self.coord)           # добавление ситуации в область памяти
        result_path = {tuple(self.coord): None}     # выделяется память под каждую ветку дерева

        while available_path:                                                   # Пока deque не пуст
            success = self.target_situation(available_path[0])                  # Проверка на достижение целевой ситуации
            # self.show_current_situation(available_path[0], False)             # отображение расположения элементов
            # sleep(0.5)                                                        # Задержка вывода на 0,5 секунд

            if success:                                                         # Если цель достигнута
                self.found_solution_method(result_path)
                return                                                          # выход из функции

            new_moves = self.generate_method_bfs(available_path[0])             # генерация новых шагов
            for move in new_moves:                                              # прохождение по всем новым шагам
                if all(list(move) != list(key) for key in result_path.keys()):  # если шага не было в очереди
                    available_path.append(move)                                 # добавление новой ситуации в путь
                    result_path[tuple(move)] = tuple(available_path[0])         # добавление шага в дерево

            available_path.popleft()                                            # удалить использованную ситуацию

        print("Нет решения!")                                                   # Если решение не найдено
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
    # print("""Выбор лабиринта:
    # 1. Коридорный лабиринт
    # 2. Первый стандартный лабиринт вариант 1
    # 3. Первый стандартный лабиринт вариант 2
    # 4. Пустой лабиринт\n""")
    # choice_maze = int(input("Ввод: "))
    choice_maze = 2

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

    # Запуск метода поиска в глубину
    # situation.dfs()

    # Запуск метода поиска в глубину с оценочной функцией
    # situation.dfs_with_gradient()

    # Запуск метода поиска в ширину
    # situation.bfs()

    sleep(5)

    situation.print_table([9, 9, 22], [9, 9, 9], [10, 10, 17], [10, 9, 20], [11, 11, 18])

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
