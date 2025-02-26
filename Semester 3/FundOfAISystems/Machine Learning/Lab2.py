# 1. Чистая нейронная сеть с несколькими входами и выходами

# Веса
#   Стресс  ВП    ФА
weights = [
    [0.01, 0.09, 0.01],
    [0.01, -0.04, 0.0],
    [0.0, 0.17, 0.03]]


# Взвешенная сумма (свертка) двух тензоров
def w_sum(a, b):
    assert (len(a) == len(b))
    output = 0
    for i in range(len(a)):
        output += a[i] * b[i]
    return output


# Векторное умножение матриц (тензоров)
def vect_mat_mul(vect, matrix):
    assert (len(vect) == len(matrix))
    output = [0, 0, 0]
    for i in range(len(vect)):
        output[i] = w_sum(vect, matrix[i])
    return output


def neural_network(input, weights):
    pred = vect_mat_mul(input, weights)
    return pred


def outer_prod(vec_a, vec_b):
    out = [[0, 0, 0],
           [0, 0, 0],
           [0, 0, 0]]
    for i in range(len(vec_a)):
        for j in range(len(vec_b)):
            out[i][j] = vec_a[i] * vec_b[j]
    return out


# 2. Прогноз: получение прогноза и вычисление среднеквадратичной ошибки и чистой ошибки
# Входные данные
stress = [5, 8, 15, 6, 30]      # Стресс
bad_habits = [8, 4, 2, 6, 1]    # Плохие привычки
phys_active = [1, 1, 2, 1, 3]   # Физическая активность

input = [stress[0], bad_habits[0], phys_active[0]]

# Верные прогноз
healthy = [0.43, 0.62, 0.64, 0.5, 0.51]     # Здравая старость
diseses = [3, 7, 5, 8, 3]                   # Хронические заболевания
hirts = [1, 2, 3, 2, 5]                     # Травмы

true = [healthy[1], diseses[1], hirts[1]]

alpha = 0.01  # альфа-коэффициент, чтобы сеть не шла в расхождение при больших значениях input

error = [0, 0, 0]
delta = [0.0, 0.0, 0.0]

for inter in range(100):
    pred = neural_network(input, weights)
    print("Pred: " + str(pred))
    for i in range(len(true)):
        error[i] = (pred[i] - true[i])**2
        delta[i] = pred[i] - true[i]
    weights_deltas = outer_prod(input, delta)

    for i in range(len(weights)):
        for j in range(len(weights[0])):
            weights[i][j] = weights[i][j] - alpha * weights_deltas[j][i]

# 3. Сравнение: вычисление каждого приращения (производной) weight_delta

# def outer_prod(vec_a, vec_b):
#     out = [[0, 0, 0],
#            [0, 0, 0],
#            [0, 0, 0]]
#     for i in range(len(vec_a)):
#         for j in range(len(vec_b)):
#             out[i][j] = vec_a[i] * vec_b[j]
#     return out
