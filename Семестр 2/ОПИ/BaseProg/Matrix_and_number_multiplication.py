def Matrix_and_number_multiplication(matrix):
    # Ввод числа для умножения
    try:
        num = float(input('Ввод числа: '))
    except ValueError:
        print('Некорректный ввод значения числа')
        exit(9)
    # Определение значений m и n
    m = len(matrix)
    n = len(matrix[0])

    # Умножение матрицы на число
    for i in range(m):
        for j in range(n):
            matrix[i][j] *= num

    # Вывод результата
    return matrix
