from copy import deepcopy


########################################################################
########################################################################
########################################################################
def difference_between_vectors(x1, x2):
    if len(x1) != len(x2):
        raise ValueError("Размеры векторов не совпадают.")
    diff = 0
    for i in range(len(x1)):
        diff += abs(x1[i] - x2[i])
    return diff


def vectors_sum(x1, x2):
    if len(x1) != len(x2):
        raise ValueError("Размеры векторов не совпадают.")
    res = [x1[i] + x2[i] for i in range(len(x1))]
    return res


def vectors_dif(x1, x2):
    if len(x1) != len(x2):
        raise ValueError("Размеры векторов не совпадают.")
    res = [x1[i] - x2[i] for i in range(len(x1))]
    return res


def vector_num_mul(v, n):
    return [round(t * n, 9) for t in v]


def matrix_vector_multiply(A, v):
    if len(A[0]) != len(v):
        raise ValueError("Размеры матрицы и вектора не позволяют их перемножить.")

    result = [sum(A[i][j] * v[j] for j in range(len(v))) for i in range(len(A))]
    return result


def solve_with_iterations(matrix, vec_b):
    matrix, vec_b = gauss(matrix, vec_b)

    alpha = [[0] * len(matrix[0]) for _ in range(len(matrix))]
    beta = [0] * len(vec_b)
    for i in range(len(matrix)):
        beta[i] = round(vec_b[i] / matrix[i][i], 10)

    for i in range(len(matrix)):
        for j in range(len(matrix[0])):
            if i != j:
                alpha[i][j] = - round(matrix[i][j] / matrix[i][i], 10)
            else:
                alpha[i][j] = 0

    x = [0] * len(matrix)
    inaccuracy = float("inf")
    while inaccuracy >= eps:
        x_prev = deepcopy(x)
        x = vectors_sum(beta, matrix_vector_multiply(alpha, x))
        inaccuracy = difference_between_vectors(x, x_prev)

    return x, inaccuracy


def gauss(matrix, vec_b):
    if len(matrix) != len(vec_b):
        raise ValueError("Размеры матрицы и вектора не совпадают.")

    for i in range(len(matrix)):
        matrix[i] += [vec_b[i]]

    for i in range(len(matrix)):
        max_el = abs(matrix[i][i])
        max_row = i
        for k in range(i + 1, len(matrix)):
            if abs(matrix[k][i]) > max_el:
                max_el = abs(matrix[k][i])
                max_row = k
        matrix[i], matrix[max_row] = matrix[max_row], matrix[i]

        mat_str = [x for x in matrix[i]]
        for j in range(i + 1, len(matrix)):
            if matrix[i][i] != 0:
                cur = vector_num_mul(mat_str, matrix[j][i] / matrix[i][i])
                matrix[j] = vectors_dif(matrix[j], cur)

    for i in range(len(matrix)):
        vec_b[i] = matrix[i].pop()

    return matrix, vec_b
########################################################################
########################################################################
########################################################################

def gauss_jordan(matrix):
    n = len(matrix)

    E = [[0] * len(matrix[0]) for _ in range(n)]
    for i in range(len(E)):
        E[i][i] = 1

    for i in range(n):
        matrix[i] += E[i]

    for i in range(n):
        max_el = abs(matrix[i][i])
        max_row = i
        for k in range(i + 1, n):
            if abs(matrix[k][i]) > max_el:
                max_el = abs(matrix[k][i])
                max_row = k

        matrix[i], matrix[max_row] = matrix[max_row], matrix[i]

        pivot = matrix[i][i]
        if pivot == 0:
            raise ValueError("Матрица необратима.")
        matrix[i] = [element / pivot for element in matrix[i]]

        for j in range(n):
            if j != i:
                factor = matrix[j][i]
                matrix[j] = [matrix[j][k] - factor * matrix[i][k] for k in range(2 * n)]

    inverse_matrix = [row[n:] for row in matrix]
    return inverse_matrix


def calculate_cond(matrix):
    inverse_mat = gauss_jordan(deepcopy(matrix))
    a_norm = float("-inf")
    for row in matrix:
        s = 0
        for item in row:
            s += abs(item)
        a_norm = max(a_norm, s)

    inverse_norm = float("-inf")
    for row in inverse_mat:
        s = 0
        for item in row:
            s += abs(item)
        inverse_norm = max(inverse_norm, s)
    cond = a_norm * inverse_norm
    return round(cond, 5)


def det(matrix):
    matrix, v = gauss(matrix, [0] * len(matrix))
    res = 1
    for i in range(len(matrix)):
        res *= matrix[i][i]
    return round(res, 5)


if __name__ == '__main__':
    eps = 1e-3

    mat = [[1.7, -1.8, 1.9, -57.4],
           [1.1, -4.3, 1.5, -1.7],
           [1.2, 1.4, 1.6, 1.8],
           [7.1, -1.3, -4.1, 5.2]]
    b = [10, 19, 20, 10]
    # 5.81058  -0.234238  8.04169 0.271408

    # matrix = [[-2, 0, 6],
    #           [8, -4, -1],
    #           [0, 7, -2]]
    # vec_b = [-22, 33, 58]
    # matrix = [[6.25, -1, 0.5]  ,
    #           [-1, 5, 2.12],
    #           [0.5, 2.12, 3.6]]
    # vec_b = [7.5, -8.68, -0.24]

    print(solve_with_iterations(deepcopy(mat), b))
    print(gauss_jordan(deepcopy(mat)))
    print(det(deepcopy(mat)))
    print(calculate_cond(deepcopy(mat)))

    print('----------')