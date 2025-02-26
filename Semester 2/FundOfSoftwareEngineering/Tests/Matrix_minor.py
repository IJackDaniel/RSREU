from Determination_of_Matrix import determination_of_matrix


def Matrix_minor(matrix, mini, minj):
    n = len(matrix)
    m = len(matrix[0])
    try:
        minor_i, minor_j = int(mini), int(minj)
    except ValueError:
        return "Error 9"

    if minor_i > n or minor_j > m:
        return "Error 10"

    del matrix[minor_i-1]
    for i in range(n-1):
        del matrix[i][minor_j-1]
    return determination_of_matrix(matrix, n-1)
