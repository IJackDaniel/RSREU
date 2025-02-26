from Determination_of_Matrix import determination_of_matrix
from Matrix_and_number_multiplication import Matrix_and_number_multiplication
from Matrix_and_number_division import Matrix_and_number_division
from Matrix_num_add_sub import Matrix_num_add_sub
from Matrix_square import Matrix_square
from TestProduct_of_Matrix import Product_of_Matrix
from TestMatrix_addition import Matrix_addition
from TestMatrix_subtraction import Matrix_subtraction
from Transposing_the_Matrix import Transposing_the_Matrix
from Matrix_minor import Matrix_minor


def OPI(n, m, matrixInp, choice, n2=None, m2=None, matrixInpTwo=None, num=None, mini=None, minj=None):
    # Определяем матрицу
    try:
        n, m = int(n), int(m)
    except ValueError:
        return "Error 1"

    if n * m == 1:
        return "Error 2"

    if n > 50 or m > 50:
        return "Error 3"

    matrix = []

    # Ввод матрицы построчно
    for i in range(n):
        s = []
        for j in range(m):
            try:
                s.append(float(matrixInp[i][j]))
            except ValueError:
                return "Error 6"
        if len(s) > m:
            s = [s[i] for i in range(m)]
        elif len(s) < m:
            while len(s) != m:
                s.append(0)
        matrix.append(s)

    try:
        choice = int(choice)
    except ValueError:
        return "Error 4"

    match choice:
        case 1:
            if n != m:
                return "Error 7"
            else:
                result = determination_of_matrix(matrix, n)

        case 2:
            result = Matrix_and_number_multiplication(matrix, num)

        case 3:
            result = Matrix_and_number_division(matrix, num)

        case 4:
            result = Matrix_num_add_sub(matrix, num)

        case 5:
            if n != m:
                return "Error 7"
            else:
                result = Matrix_square(matrix)

        case 6:
            result = Product_of_Matrix(matrix, matrixInpTwo, n2, m2)

        case 7:
            result = Matrix_addition(matrix, matrixInpTwo, n2, m2)

        case 8:
            result = Matrix_subtraction(matrix, matrixInpTwo, n2, m2)

        case 9:
            result = Transposing_the_Matrix(matrix)

        case 10:
            if n != m:
                return "Error 7"
            else:
                result = (Matrix_minor(matrix, mini, minj))

        case _:
            return "Error 5"

    return result

