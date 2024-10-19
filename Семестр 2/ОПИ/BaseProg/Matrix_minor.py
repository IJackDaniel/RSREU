from Determination_of_Matrix import determination_of_matrix


def Matrix_minor(matrix):
    n = len(matrix)
    m = len(matrix[0])
    try:
        minor_i, minor_j = map(int, input("Ввод минора: ").split())
    except ValueError:
        print('Некорректный ввод значений минора')
        exit(9)

    if minor_i > n or minor_j > m:
        print('Введенные значения минорных строк превышают размеры матрицы')
        exit(10)

    del matrix[minor_i-1]
    for i in range(n-1):
        del matrix[i][minor_j-1]
    return determination_of_matrix(matrix, n-1)
