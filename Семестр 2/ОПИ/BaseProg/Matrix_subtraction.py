def Matrix_subtraction(matrix1):
    # Определяем матрицу
    n2, m2 = map(int, input("Введите размерность матрицы: ").split())
    matrix2 = []

    # Ввод матрицы построчно
    for i in range(n2):
        try:
            s = [a for a in map(float, input(f"Введите строку {i}: ").split())]
        except ValueError:
            print("\nОшибка ввода значения элемента матрицы")
            exit(9)
        if len(s) > m2:
            s = [s[i] for i in range(m2)]
        elif len(s) < m2:
            while len(s) != m2:
                s.append(0)
        matrix2.append(s)

    n1 = len(matrix1)
    m1 = len(matrix1[0])

    if (n1 != m2) or (m1 != n2):
        print('Некорректный ввод размерности матриц')
        exit(8)

    matrix_res = []
    for i in range(n1):
        matrix_res.append([0] * m1)

    for i in range(n1):
        for j in range(m1):
            matrix_res[i][j] = matrix1[i][j] - matrix2[i][j]
    return matrix_res
