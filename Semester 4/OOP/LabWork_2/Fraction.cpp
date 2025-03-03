#include "Fraction.hpp"

#include <iostream>
#include <string>

// Конструктор
Fraction::Fraction(int _numerator, int _denominator, int _sign)
{
    sign = (_sign == 1);
    if (_numerator < 0)
    {
        sign = not(sign);
        _numerator = _numerator * (-1);
    }
    if (_denominator < 0)
    {
        sign = not(sign);
        _denominator = _denominator * (-1);
    }
    normalize(_numerator, _denominator);
    numerator = _numerator;
    denominator = _denominator;
    
    
}

// Геттеры

int Fraction::get_numerator()
{
    return numerator;
}

int Fraction::get_denominator()
{
    return denominator;
}

bool Fraction::get_sign()
{
    return sign;
}

// Сеттеры

void Fraction::set_numerator(int num)
{
    numerator = num;
}

void Fraction::set_denominator(int num)
{
    denominator = num;
}

void Fraction::set_sign(int new_sign)
{
    sign = (new_sign == 1) ? true : false;
}

// Наибольший общий делитель для двух чисел
int Fraction::greatest_common_divisor(int num1, int num2) 
{
	while (num1 * num2 != 0) 
	{
		if (num1 > num2) 
		{
			num1 = num1 % num2;
		} else 
		{
			num2 = num2 % num1;
		}
	}
	return num1 + num2;
}

// Наибольший общий делитель для трёх чисел
int Fraction::greatest_common_divisor_three_num(int num1, int num2, int num3) 
{
	return greatest_common_divisor(greatest_common_divisor(num1, num2), num3);
}

// Функция нормализации дроби
void Fraction::normalize(int& num1, int& num2)
{
    int gcd = greatest_common_divisor(num1, num2);
	num1 /= gcd;
	num2 /= gcd;
}

// Метод сложения дробей
void Fraction::add_frac(Fraction f1, Fraction f2)
{
    int n1 = f1.get_numerator(), n2 = f2.get_numerator();
    int d1 = f1.get_denominator(), d2 = f2.get_denominator();
    n1 = f1.get_sign()? n1 : -n1;
    n2 = f2.get_sign()? n2 : -n2;
    int _numerator = n1 * d2 + n2 * d1;
    int _denominator = d1 * d2;

    if (_numerator < 0)
    {
        sign = not(sign);
        _numerator = _numerator * (-1);
    }
    if (_denominator < 0)
    {
        sign = not(sign);
        _denominator = _denominator * (-1);
    }

    normalize(_numerator, _denominator);
    numerator = _numerator;
    denominator = _denominator;
}
