def Matrix_and_number_division(matrix, num):
    # Ввод числа для деления
    try:
        num = float(num)
    except ValueError:
        return "Error 9"
    # Определение значений m и n
    m = len(matrix)
    n = len(matrix[0])

    # Деление матрицы на число
    for i in range(m):
        for j in range(n):
            matrix[i][j] /= num

    # Вывод результата
    return matrix
