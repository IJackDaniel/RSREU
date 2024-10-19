def Matrix_num_add_sub(matrix, num):
    n = len(matrix)
    m = len(matrix[0])

    try:
        num = float(num)
    except ValueError:
        return "Error 9"

    for i in range(n):
        for j in range(m):
            matrix[i][j] += num

    return matrix
