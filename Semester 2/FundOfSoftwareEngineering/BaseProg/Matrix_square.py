def Matrix_square(matrix):
    m = len(matrix)
    n = len(matrix[0])
    matrix_d = []
    for i in range(n):
        matrix_d.append([0]*m)

    for i in range(n):
        for l in range(n):
            s = 0
            for j in range(n):
                s += matrix[i][j] * matrix[j][l]
            matrix_d[i][l] += s

    return matrix_d
