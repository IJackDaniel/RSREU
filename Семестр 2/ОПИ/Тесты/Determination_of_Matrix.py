def determination_of_matrix_2x2(matrix):
    p1 = matrix[0][0] * matrix[1][1]
    m1 = matrix[0][1] * matrix[1][0]
    determination = p1 - m1
    return determination


def determination_of_matrix_3x3(matrix):
    # Переменные p1, p2, p3: перед ними стоит +
    p1 = matrix[0][0] * matrix[1][1] * matrix[2][2]
    p2 = matrix[0][1] * matrix[1][2] * matrix[2][0]
    p3 = matrix[0][2] * matrix[1][0] * matrix[2][1]
    # Переменные m1, m2, m3: перед ними стоит -
    m1 = matrix[0][2] * matrix[1][1] * matrix[2][0]
    m2 = matrix[0][0] * matrix[1][2] * matrix[2][1]
    m3 = matrix[0][1] * matrix[1][0] * matrix[2][2]
    # Подсчёт определителя
    determination = p1 + p2 + p3 - m1 - m2 - m3
    return determination


def determination_of_matrix_nxn(matrix):
    # ln - размерность матрицы; s - значение определителя
    ln = len(matrix)
    determination = 0
    # Разложение по первой строке
    for i in range(len(matrix[0])):
        # new_matrix - это исходная матрица, но без:
        # 1) Первой строки
        new_matrix = []
        for p in range(1, len(matrix)):
            new_matrix.append([w for w in matrix[p]])

        # Определяем значение (-1)^(n+m)
        if i % 2 == 0:
            if ln == 4:
                # new_matrix - это исходная матрица, но без:
                # 2) Элементов того столбца, в котором находится актуальный элемент разложения
                for e in range(len(new_matrix)):
                    del new_matrix[e][i]
                determination = determination + matrix[0][i]*determination_of_matrix_3x3(new_matrix)
            else:
                # new_matrix - это исходная матрица, но без:
                # 2) Элементов того столбца, в котором находится актуальный элемент разложения
                for e in range(len(new_matrix)):
                    del new_matrix[e][i]
                determination = determination + matrix[0][i]*determination_of_matrix_nxn(new_matrix)
        else:
            if ln == 4:
                # new_matrix - это исходная матрица, но без:
                # 2) Элементов того столбца, в котором находится актуальный элемент разложения
                for e in range(len(new_matrix)):
                    del new_matrix[e][i]
                determination = determination - matrix[0][i]*determination_of_matrix_3x3(new_matrix)
            else:
                # new_matrix - это исходная матрица, но без:
                # 2) Элементов того столбца, в котором находится актуальный элемент разложения
                for e in range(len(new_matrix)):
                    del new_matrix[e][i]
                determination = determination - matrix[0][i]*determination_of_matrix_nxn(new_matrix)
    return determination


def determination_of_matrix(matrix, n):
    # Проверка размерности матрицы и выбор нужной расчётной функции
    match n:
        case 1:
            # Матрица 1x1
            result = matrix[0][0]
        case 2:
            # Матрица 2x2
            result = determination_of_matrix_2x2(matrix)
        case 3:
            # Матрица 3x3
            result = determination_of_matrix_3x3(matrix)
        case _:
            # Матрица NxN, где N > 3
            result = determination_of_matrix_nxn(matrix)
    return result

