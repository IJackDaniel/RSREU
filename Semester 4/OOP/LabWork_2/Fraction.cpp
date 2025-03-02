#include "Fraction.hpp"

#include <iostream>
#include <string>

// Конструктор
Fraction::Fraction(int _numerator, int _denominator, int _sign)
{
    numerator = _numerator;
    denominator = _denominator;
    sign = (_sign == 1);
}

// Геттеры

int Fraction::get_numerator()
{
    return numerator;
}
