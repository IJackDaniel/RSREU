def Product_of_Matrix(matrixIn1, matrixIn2, n2, m2):
    # Определяем матрицу
    matrix2 = []

    # Ввод матрицы построчно
    for i in range(n2):
        s = []
        for j in range(m2):
            try:
                s.append(float(matrixIn2[i][j]))
            except ValueError:
                return "Error 6"
        if len(s) > m2:
            s = [s[i] for i in range(m2)]
        elif len(s) < m2:
            while len(s) != m2:
                s.append(0)
        matrix2.append(s)

    # Дальнейшая программа
    n1 = len(matrixIn1)
    m1 = len(matrixIn1[0])
    if m1 != n2:
        return "Error 8"

    matrix_res = []
    for i in range(n1):
        matrix_res.append([0]*m2)
    for i in range(n1):
        for j in range(m2):
            for k in range(m1):
                matrix_res[i][j] += matrixIn1[i][k] * matrix2[k][j]

    return matrix_res

