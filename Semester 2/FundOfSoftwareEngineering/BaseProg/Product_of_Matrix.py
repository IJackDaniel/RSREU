def Product_of_Matrix(matrix1):
    # Определяем матрицу
    n2, m2 = map(int, input("Введите размерность матрицы: ").split())
    matrix2 = []

    # Ввод матрицы построчно
    for i in range(n2):
        try:
            s = [a for a in map(float, input(f"Введите строку {i}: ").split())]
        except ValueError:
            print("\nОшибка ввода значения элемента матрицы")
            exit(6)
        if len(s) > m2:
            s = [s[i] for i in range(m2)]
        elif len(s) < m2:
            while len(s) != m2:
                s.append(0)
        matrix2.append(s)

    # Дальнейшая программа
    n1 = len(matrix1)
    m1 = len(matrix1[0])
    if m1 != n2:
        print('Кол-во строк 1й матрицы и кол-во столбцов 2й матрицы не совпадают')
        exit(8)

    matrix_res = []
    for i in range(n1):
        matrix_res.append([0]*m2)
    for i in range(n1):
        for j in range(m2):
            for k in range(m1):
                matrix_res[i][j] += matrix1[i][k] * matrix2[k][j]

    return matrix_res
