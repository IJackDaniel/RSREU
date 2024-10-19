from Determination_of_Matrix import determination_of_matrix
from Matrix_and_number_multiplication import Matrix_and_number_multiplication
from Matrix_and_number_division import Matrix_and_number_division
from Matrix_num_add_sub import Matrix_num_add_sub
from Matrix_square import Matrix_square
from Product_of_Matrix import Product_of_Matrix
from Matrix_addition import Matrix_addition
from Matrix_subtraction import Matrix_subtraction
from Transposing_the_Matrix import Transposing_the_Matrix
from Matrix_minor import Matrix_minor


# Определяем матрицу
try:
    n, m = map(int, input("Введите размерность матрицы: ").split())
except ValueError:
    print("\nНекорректный ввод размерности матрицы")
    exit(1)
matrix = []

if n * m == 1:
    print("\nЭто не матрица!")
    exit(2)

if n > 50 or m > 50:
    print("\nПревышен максимально допустимый размер матрицы")
    exit(3)

# Ввод матрицы построчно
for i in range(n):
    try:
        s = [a for a in map(float, input(f"Введите строку {i}: ").split())]
    except ValueError:
        print("\nОшибка ввода значения элемента матрицы")
        exit(6)
    if len(s) > m:
        s = [s[i] for i in range(m)]
    elif len(s) < m:
        while len(s) != m:
            s.append(0)
    matrix.append(s)

try:
    choice = int(input("""\nТеперь выберите действие над матрицей:
    1 - Определитель матрицы
    2 - Умножение матрицы на число
    3 - Деление матрицы на число
    4 - Прибавление/вычитание числа
    5 - Матрица в квадрате
    6 - Произведение двух матриц
    7 - Сложение матриц
    8 - Вычитание матриц
    9 - Транспонирование матрицы
    10 - Минор\n"""))
except ValueError:
    print("\nНекорректный выбор операции")
    exit(4)

match choice:
    case 1:
        if n != m:
            print("\nОшибка входных данных. Нужна квадратная матрица.")
            exit(7)
        else:
            result = determination_of_matrix(matrix, n)

    case 2:
        result = Matrix_and_number_multiplication(matrix)

    case 3:
        result = Matrix_and_number_division(matrix)

    case 4:
        result = Matrix_num_add_sub(matrix)

    case 5:
        if n != m:
            print("\nОшибка входных данных")
            exit(7)
        else:
            result = Matrix_square(matrix)

    case 6:
        result = Product_of_Matrix(matrix)

    case 7:
        result = Matrix_addition(matrix)

    case 8:
        result = Matrix_subtraction(matrix)

    case 9:
        result = Transposing_the_Matrix(matrix)

    case 10:
        if n != m:
            print("\nОшибка входных данных")
            exit(7)
        else:
            result = Matrix_minor(matrix)

    case _:
        print("\nНеправильно выбрана операция")
        exit(5)

print()
if type(result) == list:
    for i in range(n):
        for j in range(m):
            print(result[i][j], end=" ")
        print()
else:
    print(result)
end = input("\nДля завершения программы нажмите Enter")
