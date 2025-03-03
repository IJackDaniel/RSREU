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

// Конструктор для Utit тестов
Fraction::Fraction(int _numerator, int _denominator, int _sign, int for_test)
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
    numerator = _numerator;
    denominator = _denominator;
}

// Вывод
void Fraction::show()
{
    std::string s = (sign) ? "+ ": "- ";
    std::cout << s << numerator << "/" << denominator << std::endl;
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
void Fraction::add_frac(Fraction f)
{
    int n = f.get_numerator();
    int d = f.get_denominator();
    numerator = (sign)? numerator : -numerator;
    n = (f.get_sign())? n : -n;
    int _numerator = numerator * d + n * denominator;
    int _denominator = denominator * d;

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
    if (_numerator == 0)
    {
        sign = 1;
    }

    normalize(_numerator, _denominator);
    numerator = _numerator;
    denominator = _denominator;
}

// Метод вычитания дробей
void Fraction::sub_frac(Fraction f)
{
    int n = f.get_numerator();
    int d = f.get_denominator();
    numerator = (sign)? numerator : -numerator;
    n = (f.get_sign())? n : -n;
    int _numerator = numerator * d - n * denominator;
    int _denominator = denominator * d;

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
    if (_numerator == 0)
    {
        sign = 1;
    }

    normalize(_numerator, _denominator);
    numerator = _numerator;
    denominator = _denominator;
}

// Метод умножения дробей
void Fraction::mul_frac(Fraction f)
{
    int n = f.get_numerator();
    int d = f.get_denominator();
    numerator = (sign)? numerator : -numerator;
    n = (f.get_sign())? n : -n;
    int _numerator = numerator * n;
    int _denominator = denominator * d;

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
    if (_numerator == 0)
    {
        sign = 1;
    }

    normalize(_numerator, _denominator);
    numerator = _numerator;
    denominator = _denominator;
}

// Метод деления дробей
void Fraction::div_frac(Fraction f)
{
    int n = f.get_numerator();
    int d = f.get_denominator();
    numerator = (sign)? numerator : -numerator;
    n = (f.get_sign())? n : -n;
    int _numerator = numerator * d;
    int _denominator = denominator * n;

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
    if (_numerator == 0)
    {
        sign = 1;
    }

    normalize(_numerator, _denominator);
    numerator = _numerator;
    denominator = _denominator;
}

void Fraction::common_denominator(Fraction& f)
{
    if (f.denominator != denominator)
    {
        int common = denominator * f.denominator;
        numerator = numerator * f.denominator;
        f.numerator = f.numerator * denominator;
        denominator = common;
        f.denominator = common;

        int gcd = greatest_common_divisor_three_num(numerator, denominator, f.numerator);
        denominator /= gcd;
        f.denominator /= gcd;
        numerator /= gcd;
        f.numerator /= gcd;
    }
}

int Fraction::comparing_fractions(Fraction f)
{
    int num1 = numerator * f.denominator;
	int num2 = f.numerator * denominator;
	if (!sign) 
	{
		num1 *= -1;
	}
	if (!f.sign) 
	{
		num2 *= -1;
	}
	
	if (num1 == num2) 
	{
		return 0;
	}
	else if (num1 > num2) 
	{
		return 1;
	}
	else 
	{
		return -1;	
	}
}
