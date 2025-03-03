#include <iostream>
#include <string>
#include "Fraction.hpp"

int main()
{
    std::cout << "Hello, from Lavoratory_Work_2!\n";
    Fraction frac1 (4, 2, 1);
    Fraction frac2 (3, 7, 0);

    Fraction res (1, 1, 1);
    res.add_frac(frac1, frac2);
    int c = res.get_numerator();
    int d = res.get_denominator();
    std::string q = res.get_sign()? "+ " : "- ";
    std::cout << q << c << " / " << d;
}

/*
Создать квадратную матрицу и посчитать сумму элементов на главной диагонали

Потом пишем программу транспонирования матрицы
*/