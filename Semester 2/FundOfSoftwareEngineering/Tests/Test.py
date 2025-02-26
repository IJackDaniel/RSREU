import unittest
from TestOPI import OPI


class TestOPI(unittest.TestCase):
    def test_matrixWithStringInNM(self):
        n = "a"
        m = 3
        matrix = [[2, 5, 6], [2, 3, 5], [1, 2, 1]]
        choice = 1
        self.assertEqual(OPI(n, m, matrix, choice), "Error 1")

    def test_matrix1x1(self):
        n = 1
        m = 1
        matrix = [[3]]
        choice = 2
        self.assertEqual(OPI(n, m, matrix, choice), "Error 2")

    def test_matrixWithNMMoreThen50(self):
        n = 55
        m = 4
        matrix = []
        choice = 5
        self.assertEqual(OPI(n, m, matrix, choice), "Error 3")

    def test_choiceNotInt(self):
        n = 2
        m = 4
        matrix = [[1, 2, 3, 4], [5, 6, 7, 8]]
        choice = "a"
        self.assertEqual(OPI(n, m, matrix, choice), "Error 4")

    def test_choiceNotInRangeFrom1To10(self):
        n = 2
        m = 4
        matrix = [[1, 2, 3, 4], [5, 6, 7, 8]]
        choice = 90
        self.assertEqual(OPI(n, m, matrix, choice), "Error 5")

    def test_matrixWithStrAlph(self):
        n = 3
        m = 3
        matrix = [[3, "a", 6], [2, 3, "l"], [3, 4, 5]]
        choice = 1
        self.assertEqual(OPI(n, m, matrix, choice), "Error 6")


# Определитель - 1
class TestDeterm(unittest.TestCase):
    def test_det_Common1(self):
        n = 3
        m = 3
        matrix = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
        choice = 1
        res = 0
        self.assertEqual(OPI(n, m, matrix, choice), res)

    def test_det_Common2(self):
        n = 5
        m = 5
        matrix = [
            [23, 5, 3, -9, 14],
            [45, 7, 3, 1, 0],
            [-7, 34, 67, 10, -2],
            [13, 17, -19, 93, 1],
            [6, 3, 98, 151, 2]
        ]
        choice = 1
        res = -350715868
        self.assertEqual(OPI(n, m, matrix, choice), res)

    def test_det_Common3(self):
        n = 2
        m = 2
        matrix = [[7, 9], [17, -3]]
        choice = 1
        res = -174
        self.assertEqual(OPI(n, m, matrix, choice), res)

    def test_det_Common4(self):
        n = 4
        m = 4
        matrix = [
            [1, 0, 0, 0],
            [0, 1, 0, 0],
            [0, 0, 1, 0],
            [0, 0, 0, 1]
        ]
        choice = 1
        res = 1
        self.assertEqual(OPI(n, m, matrix, choice), res)

    def test_det_NullMatrix(self):
        n = 3
        m = 3
        matrix = [[0, 0, 0], [0, 0, 0], [0, 0, 0]]
        choice = 1
        res = 0
        self.assertEqual(OPI(n, m, matrix, choice), res)

    def test_det_MatrixOnlyWithOnes(self):
        n = 4
        m = 4
        matrix = [[1, 1, 1, 1], [1, 1, 1, 1], [1, 1, 1, 1], [1, 1, 1, 1]]
        choice = 1
        res = 0
        self.assertEqual(OPI(n, m, matrix, choice), res)

    def test_det_ProporcMatrix(self):
        n = 3
        m = 3
        matrix = [[1, 2, 3], [3, 6, 9], [5, 10, 15]]
        choice = 1
        res = 0
        self.assertEqual(OPI(n, m, matrix, choice), res)

    def test_det_NoSquareMatrix(self):
        n = 3
        m = 5
        matrix = [[5, 4, 7, 1, 5], [3, 3, 1, 9, 53], [0, 23, 4, 76, 2]]
        choice = 1
        self.assertEqual(OPI(n, m, matrix, choice), "Error 7")


# Умножение на число - 2
class TestMulNum(unittest.TestCase):
    def test_MulNum_Common1(self):
        n = 3
        m = 3
        matrix = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
        choice = 2
        matrixRes = [[5, 10, 15], [20, 25, 30], [35, 40, 45]]
        self.assertEqual(OPI(n, m, matrix, choice, num=5), matrixRes)

    def test_MulNum_Common2(self):
        n = 4
        m = 4
        matrix = [
            [-95, 196, 42, 284],
            [-26, -146, -222, -372],
            [36, 164, -69, -176],
            [16, 32, -132, 104]]
        choice = 2
        matrixRes = [
            [-1235, 2548, 546, 3692],
            [-338, -1898, -2886, -4836],
            [468, 2132, -897, -2288],
            [208, 416, -1716, 1352]
        ]
        self.assertEqual(OPI(n, m, matrix, choice, num=13), matrixRes)

    def test_MulNum_ZeroNum(self):
        n = 3
        m = 5
        matrix = [
            [5, 2, 87, 909, -4],
            [435, 5, 24, -9, 0],
            [7, 12, 32, -5, 23]
        ]
        choice = 2
        matrixRes = [
            [0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0]
        ]
        self.assertEqual(OPI(n, m, matrix, choice, num=0), matrixRes)

    def test_MulNum_NumIsAlph(self):
        n = 3
        m = 3
        matrix = [[6, 3, -9], [0, 5, 7], [3, 8, 11]]
        choice = 2
        self.assertEqual(OPI(n, m, matrix, choice, num="a"), "Error 9")


# Деление на число - 3
class TestDivNum(unittest.TestCase):
    def test_DivNum_Common1(self):
        n = 3
        m = 3
        matrix = [[5, 10, 15], [20, 25, 30], [35, 40, 45]]
        choice = 3
        matrixRes = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
        self.assertEqual(OPI(n, m, matrix, choice, num=5), matrixRes)

    def test_DivNum_Common2(self):
        n = 4
        m = 4
        matrix = [
            [-95, 196, 42, 284],
            [-26, -146, -222, -372],
            [36, 164, -69, -176],
            [16, 32, -132, 104]]
        choice = 3
        matrixRes = [
            [-47.5, 98, 21, 142],
            [-13, -73, -111, -186],
            [18, 82, -34.5, -88],
            [8, 16, -66, 52]
        ]
        self.assertEqual(OPI(n, m, matrix, choice, num=2), matrixRes)

    def test_DivNum_NumIsAlph(self):
        n = 3
        m = 3
        matrix = [[6, 3, -9], [0, 5, 7], [3, 8, 11]]
        choice = 3
        self.assertEqual(OPI(n, m, matrix, choice, num="a"), "Error 9")


# Прибавление/вычитание числа - 4
class TestAddSubNum(unittest.TestCase):
    def test_AddSubNum_Common1(self):
        n = 3
        m = 3
        matrix = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
        choice = 4
        matrixRes1 = [[6, 7, 8], [9, 10, 11], [12, 13, 14]]
        self.assertEqual(OPI(n, m, matrix, choice, num=5), matrixRes1)
        matrixRes2 = [[-1, 0, 1], [2, 3, 4], [5, 6, 7]]
        self.assertEqual(OPI(n, m, matrix, choice, num=-2), matrixRes2)

    def test_AddSubNum_Common2(self):
        n = 3
        m = 6
        matrix = [[1, 10, 50, 72, 48, 16], [94, 65, 98, 54, 32, 35], [42, 54, 15, 13, 67, 95]]
        choice = 4
        matrixRes1 = [[14, 23, 63, 85, 61, 29], [107, 78, 111, 67, 45, 48], [55, 67, 28, 26, 80, 108]]
        self.assertEqual(OPI(n, m, matrix, choice, num=13), matrixRes1)
        matrixRes2 = [[-87, -78, -38, -16, -40, -72], [6, -23, 10, -34, -56, -53], [-46, -34, -73, -75, -21, 7]]
        self.assertEqual(OPI(n, m, matrix, choice, num=-88), matrixRes2)

    def test_numIsAlph(self):
        n = 2
        m = 2
        matrix = [[3, 7], [9, 2]]
        choice = 4
        self.assertEqual(OPI(n, m, matrix, choice, num="t"), "Error 9")


# Квадрат матрицы - 5
class TestSquare(unittest.TestCase):
    def test_Square_Common1(self):
        n = 3
        m = 3
        matrix = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
        choice = 5
        matrixRes = [[30, 36, 42], [66, 81, 96], [102, 126, 150]]
        self.assertEqual(OPI(n, m, matrix, choice), matrixRes)

    def test_Square_Common2(self):
        n = 4
        m = 4
        matrix = [[-1, -70, -138, 92], [50, -40, -150, -108], [20, -52, 54, -124], [-11, -40, 99, 48]]
        choice = 5
        matrixRes = [
            [-7271, 6366, 12294, 28996],
            [-3862, 10220, -19692, 22336],
            [-176, 2832, -4320, -5192],
            [-537, -4698, 17616, -6664]
        ]
        self.assertEqual(OPI(n, m, matrix, choice), matrixRes)

    def test_Square_NotSquare(self):
        n = 5
        m = 2
        matrix = [[0, -36], [-15, -62], [-34, 42], [-9, 12], [8, 78]]
        choice = 5
        self.assertEqual(OPI(n, m, matrix, choice), "Error 7")


# Произведение матриц - 6
class TestProdM(unittest.TestCase):
    def test_ProdM_Common1(self):
        n = 3
        m = 3
        matrix = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
        choice = 6
        matrix2 = [[3, 6, 9], [5, 10, 15], [8, 9, 1]]
        matrixRes = [[37.0, 53.0, 42.0], [85.0, 128.0, 117.0], [133.0, 203.0, 192.0]]
        self.assertEqual(OPI(n, m, matrix, choice, n2=3, m2=3, matrixInpTwo=matrix2), matrixRes)

    def test_ProdM_Common2(self):
        n = 3
        m = 2
        matrix = [[1, 2], [4, 5], [7, 8]]
        choice = 6
        matrix2 = [[1, 10, -2], [3, 4, -6]]
        matrixRes = [[7, 18, -14], [19, 60, -38], [31, 102, -62]]
        self.assertEqual(OPI(n, m, matrix, choice, n2=2, m2=3, matrixInpTwo=matrix2), matrixRes)

    def test_ProdM_Common3(self):
        n = 4
        m = 2
        matrix = [[1, -2], [-45, 51], [71, -6], [2, -10]]
        choice = 6
        matrix2 = [[34, 21, -9, 37, 10], [12, 100, 9, -1, 23]]
        matrixRes = [[10, -179, -27, 39, -36], [-918, 4155, 864, -1716, 723], [2342, 891, -693, 2633, 572], [-52, -958, -108, 84, -210]]
        self.assertEqual(OPI(n, m, matrix, choice, n2=2, m2=5, matrixInpTwo=matrix2), matrixRes)

    def test_ProdM_Error1(self):
        n = 3
        m = 3
        matrix = [[1, 2, 3], ["4, 5, 6", "1", "абуа"], [7, 8, 9]]
        choice = 6
        matrix2 = [[3, 6, 9], [5, 10, 15], [8, 9, 1]]
        self.assertEqual(OPI(n, m, matrix, choice, n2=3, m2=3, matrixInpTwo=matrix2), "Error 6")

    def test_ProdM_Error2(self):
        n = 3
        m = 3
        matrix = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
        choice = 6
        matrix2 = [[3, 6], [5, 10]]
        self.assertEqual(OPI(n, m, matrix, choice, n2=2, m2=2, matrixInpTwo=matrix2), "Error 8")


# Сложение матриц - 7
class TestMAdd(unittest.TestCase):
    def test_MAdd_Common1(self):
        n = 3
        m = 3
        matrix = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
        choice = 7
        matrix2 = [[3, 6, 9], [5, 10, 15], [8, 9, 1]]
        matrixRes = [[4, 8, 12], [9, 15, 21], [15, 17, 10]]
        self.assertEqual(OPI(n, m, matrix, choice, n2=3, m2=3, matrixInpTwo=matrix2), matrixRes)

    def test_MAdd_Common2(self):
        n = 4
        m = 2
        matrix = [[77, 23], [14, 54], [79, 9], [34, 0]]
        choice = 7
        matrix2 = [[55, 34], [9, 43], [76, 12], [3, 65]]
        matrixRes = [[132, 57], [23, 97], [155, 21], [37, 65]]
        self.assertEqual(OPI(n, m, matrix, choice, n2=4, m2=2, matrixInpTwo=matrix2), matrixRes)

    def test_MAdd_AlphInMat1(self):
        n = 2
        m = 2
        matrix = [[3, "a"], ["b", 2]]
        choice = 7
        matrix2 = [[4, 7], [2, 1]]
        self.assertEqual(OPI(n, m, matrix, choice, n2=2, m2=2, matrixInpTwo=matrix2), "Error 6")

    def test_MAdd_AlphInMat2(self):
        n = 2
        m = 2
        matrix = [[6, 4], [1, 9]]
        choice = 7
        matrix2 = [[3, "a"], ["b", 2]]
        self.assertEqual(OPI(n, m, matrix, choice, n2=2, m2=2, matrixInpTwo=matrix2), "Error 6")


# Вычитание матриц - 8
class TestMSub(unittest.TestCase):
    def test_MSub_Common1(self):
        n = 3
        m = 3
        matrix = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
        choice = 8
        matrix2 = [[3, 6, 9], [5, 10, 15], [8, 9, 1]]
        matrixRes = [[-2, -4, -6], [-1, -5, -9], [-1, -1, 8]]
        self.assertEqual(OPI(n, m, matrix, choice, n2=3, m2=3, matrixInpTwo=matrix2), matrixRes)

    def test_MSub_Common2(self):
        n = 4
        m = 5
        matrix = [[43, 2, 5, 13, 98], [-4, 2, 85, 4, 34], [88, 12, 54, 89, 19], [13, -8, 23, 64, 90]]
        choice = 8
        matrix2 = [[1, 2, 3, 4, 5], [6, 7, 8, 9, 10], [11, 12, 13, 14, 15], [16, 17, 18, 19, 20]]
        matrixRes = [[42, 0, 2, 9, 93], [-10, -5, 77, -5, 24], [77, 0, 41, 75, 4], [-3, -25, 5, 45, 70]]
        self.assertEqual(OPI(n, m, matrix, choice, n2=4, m2=5, matrixInpTwo=matrix2), matrixRes)

    def test_MAdd_AlphInMat1(self):
        n = 2
        m = 2
        matrix = [[3, "a"], ["b", 2]]
        choice = 7
        matrix2 = [[4, 7], [2, 1]]
        self.assertEqual(OPI(n, m, matrix, choice, n2=2, m2=2, matrixInpTwo=matrix2), "Error 6")

    def test_MAdd_AlphInMat2(self):
        n = 2
        m = 2
        matrix = [[6, 4], [1, 9]]
        choice = 7
        matrix2 = [[3, "a"], ["b", 2]]
        self.assertEqual(OPI(n, m, matrix, choice, n2=2, m2=2, matrixInpTwo=matrix2), "Error 6")


# Транспонирование матрицы - 9
class TestMtr(unittest.TestCase):
    def test_Mtr_Common(self):
        n = 3
        m = 3
        matrix = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
        choice = 9
        matrixRes = [[1, 4, 7], [2, 5, 8], [3, 6, 9]]
        self.assertEqual(OPI(n, m, matrix, choice), matrixRes)

    def test_Mtr_Common2(self):
        n = 2
        m = 4
        matrix = [[56, 23, 9, 0], [13, 44, -9, 3]]
        choice = 9
        matrixRes = [[56, 13], [23, 44], [9, -9], [0, 3]]
        self.assertEqual(OPI(n, m, matrix, choice), matrixRes)

    def test_Mtr_Common3(self):
        n = 5
        m = 3
        matrix = [[34, 66, 12], [93, 13, -9], [0, 0, 0], [1, 5, 9], [77, 24, 1]]
        choice = 9
        matrixRes = [[34, 93, 0, 1, 77], [66, 13, 0, 5, 24], [12, -9, 0, 9, 1]]
        self.assertEqual(OPI(n, m, matrix, choice), matrixRes)


# Минор - 10
class TestMMin(unittest.TestCase):
    def test_MMin_Common1(self):
        n = 3
        m = 3
        matrix = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
        choice = 10
        res = -12
        self.assertEqual(OPI(n, m, matrix, choice, mini=2, minj=2), res)

    def test_MMin_Common2(self):
        n = 3
        m = 3
        matrix = [[12, 21, 30], [43, 52, 61], [72, 81, 90]]
        choice = 10
        res = -261
        self.assertEqual(OPI(n, m, matrix, choice, mini=1, minj=1), res)

    def test_MMin_Common3(self):
        n = 4
        m = 4
        matrix = [[6, 2, 1, 3], [8, 2, 2, 2], [19, 1, 19, 1], [24, 4, 3, 2]]
        choice = 10
        res = 60
        self.assertEqual(OPI(n, m, matrix, choice, mini=1, minj=3), res)

    def test_MMin_Error1(self):
        n = 4
        m = 4
        matrix = [[6, 2, 1, 3], [8, 2, 2, 2], [19, 1, 19, 1], [24, 4, 3, 2]]
        choice = 10
        self.assertEqual(OPI(n, m, matrix, choice, mini=5, minj=3), "Error 10")

    def test_MMin_Error2(self):
        n = 3
        m = 3
        matrix = [[12, 21, 30], [43, 52, 61], [72, 81, 90]]
        choice = 10
        self.assertEqual(OPI(n, m, matrix, choice, mini="ав", minj=1), "Error 9")


if __name__ == '__main__':
    unittest.main()
