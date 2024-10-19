def Matrix_num_add_sub(matrix):
    n = len(matrix)
    m = len(matrix[0])

    try:
        num = float(input('Ввод числа: '))
    except ValueError:
        print('Некорректный ввод значения числа')
        exit(9)

    for i in range(n):
        for j in range(m):
            matrix[i][j] += num

    return matrix
