import numpy as np


def add_two_vec(vect1, vect2):
    if len(vect1) == len(vect2):
        result = [vect1[i] + vect2[i] for i in range(len(vect1))]
        return result
    return None


def mull_two_vec(vect1, vect2):
    if len(vect1) == len(vect2):
        result = [vect1[i] * vect2[i] for i in range(len(vect1))]
        return result
    return None


def sum_vec(vect):
    return sum(vect)


def sr_val_vect(vect):
    sm = sum(vect)
    n = len(vect)
    return sm / n


def w_sum(a, b):
    if len(a) == len(b):
        sm = 0
        for i in range(len(a)):
            sm += a[i]*b[i]
        return round(sm, 3)
    return None


# def vect_mat_mul(vect, matrix):
#     # реализация без использования библиотеки numpy
#     if len(vect) == len((matrix[0])):
#         result = []
#         for i in range(len(matrix)):
#             vect_col = [matrix[j][i] for j in range(len(matrix))]
#             result.append(w_sum(vect, vect_col))
#         return result
#     return None


def vect_mat_mul(vect, matrix):
    # реализация с использованием библиотеки numpy
    vector_np = np.array(vect)
    matrix_np = np.array(matrix)

    if matrix_np.shape[1] != vector_np.shape[0]:
        raise ValueError("Количество столбцов в матрице должно совпадать с количеством элементов в векторе")
    result = np.dot(vector_np, matrix_np)
    return result


def neural_network1(inp, weight):
    prediction = vect_mat_mul(inp, weight)
    return prediction


def neural_network2(inp, weight):
    prediction_hid = vect_mat_mul(inp, weight[0])
    prediction_out = vect_mat_mul(prediction_hid, weight[1])
    return prediction_out


# ---------Задание 1--------------
# a = [1, 2, 3]
# b = [5, 2, 10]
# matrix = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
# print(add_two_vec(a, b))
# print(mull_two_vec(a, b))
# print(sum_vec(a))
# print(sr_val_vect(a))
# print(w_sum(a, b))
# print(vect_mat_mul(a, matrix))

# ---------Задание 2--------------
# weights = [[0.01, 0.09, 0.01],
#            [0.01, -0.04, 0.00],
#            [0.00, 0.17, 0.03]]
#
# stress = [5, 8, 15, 6, 30]
# bad_habits = [8, 4, 2, 6, 1]
# phys_active = [1, 1, 2, 1, 3]
#
# for i in range(len(stress)):
#     inp = [stress[i], bad_habits[i], phys_active[i]]
#     pred = neural_network1(inp, weights)
#     print(pred)

# ---------Задание 3--------------
weights1 = [[0.01, 0.09, 0.01],
            [0.01, -0.04, 0.00],
            [0.00, 0.17, 0.03]]
weights2 = [[0.06, 0.02, -0.01],
            [0.00, 0.10, 0.00],
            [0.02, -0.17, 0.04]]

weights = [weights1, weights2]

stress = [5, 8, 15, 6, 30]
bad_habits = [8, 4, 2, 6, 1]
phys_active = [1, 1, 2, 1, 3]

for i in range(len(stress)):
    inp = [stress[i], bad_habits[i], phys_active[i]]
    pred = neural_network2(inp, weights)
    print(pred)
