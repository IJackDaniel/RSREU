#include "Fraction.hpp"

#include <iostream>
#include <string>
#include <cstdlib>

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

// Конструктор со значениями из диапазона
Fraction::Fraction(int _x1, int _x2, int _y1, int _y2, int _z1, int _z2)
{
    int _numerator = rand() % (_x2 - _x1 + 1) + _x1;
    int _denominator = rand() % (_y2 - _y1 + 1) + _y1;
    int _sign = rand() % (_z2 - _z1 + 1) + _z1;

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
    this->addOp("Showing fraction");
    std::string s = (sign) ? "+ ": "- ";
    std::cout << s << numerator << "/" << denominator << std::endl;
}

// Геттеры

int Fraction::get_numerator()
{
    this->addOp("Getting numerator");
    return numerator;
}

int Fraction::get_denominator()
{
    this->addOp("Getting denominator");
    return denominator;
}

bool Fraction::get_sign()
{
    this->addOp("Getting sign");
    return sign;
}

// Сеттеры

void Fraction::set_numerator(int num)
{
    this->addOp("Setting numerator");
    numerator = num;
}

void Fraction::set_denominator(int num)
{
    this->addOp("Setting denominator");
    denominator = num;
}

void Fraction::set_sign(int new_sign)
{
    this->addOp("Setting sign");
    sign = (new_sign == 1) ? true : false;
}

// Наибольший общий делитель для двух чисел
int Fraction::greatest_common_divisor(int num1, int num2) 
{
    this->addOp("Getting greatest common divisor");
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
    this->addOp("Getting greatest common divisor for three num");
	return greatest_common_divisor(greatest_common_divisor(num1, num2), num3);
}

// Функция нормализации дроби
void Fraction::normalize(int& num1, int& num2)
{
    this->addOp("Normalization of fraction");
    int gcd = greatest_common_divisor(num1, num2);
	num1 /= gcd;
	num2 /= gcd;
}

void Fraction::common_denominator(Fraction& f)
{
    this->addOp("Reducing fractions to a common denominator");
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

int Fraction::operator == (const Fraction f) const
{
    int num1 = this->numerator * f.denominator;
	int num2 = f.numerator * this->denominator;
	if (!this->sign) 
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

Fraction Fraction::operator + (Fraction& f) const
{
    int n1 = this->numerator;
    int n2 = f.numerator;
    n1 = (this->sign)? n1 : -n1;
    n2 = (f.sign)? n2 : -n2;
    int d1 = this->denominator;
    int d2 = f.denominator;
    
    int _numerator = n1 * d2 + n2 * d1;
    int _denominator = d1 * d2;
    bool s = true;

    if (_numerator < 0)
    {
        s = not(s);
        _numerator = _numerator * (-1);
    }
    if (_denominator < 0)
    {
        s = not(s);
        _denominator = _denominator * (-1);
    }
    if (_numerator == 0)
    {
        s = 1;
    }

    f.normalize(_numerator, _denominator);
    return Fraction(_numerator, _denominator, s);
}

Fraction Fraction::operator - (Fraction& f) const
{
    int n1 = this->numerator;
    int n2 = f.numerator;
    n1 = (this->sign)? n1 : -n1;
    n2 = (f.sign)? n2 : -n2;
    int d1 = this->denominator;
    int d2 = f.denominator;
    
    int _numerator = n1 * d2 - n2 * d1;
    int _denominator = d1 * d2;
    bool s = true;

    if (_numerator < 0)
    {
        s = not(s);
        _numerator = _numerator * (-1);
    }
    if (_denominator < 0)
    {
        s = not(s);
        _denominator = _denominator * (-1);
    }
    if (_numerator == 0)
    {
        s = 1;
    }

    f.normalize(_numerator, _denominator);
    return Fraction(_numerator, _denominator, s);
}

Fraction Fraction::operator * (Fraction& f) const
{
    int n1 = this->numerator;
    int n2 = f.numerator;
    n1 = (this->sign)? n1 : -n1;
    n2 = (f.sign)? n2 : -n2;
    int d1 = this->denominator;
    int d2 = f.denominator;
    
    int _numerator = n1 * n2;
    int _denominator = d1 * d2;
    bool s = true;

    if (_numerator < 0)
    {
        s = not(s);
        _numerator = _numerator * (-1);
    }
    if (_denominator < 0)
    {
        s = not(s);
        _denominator = _denominator * (-1);
    }
    if (_numerator == 0)
    {
        s = 1;
    }

    f.normalize(_numerator, _denominator);
    return Fraction(_numerator, _denominator, s);
}

Fraction Fraction::operator / (Fraction& f) const
{
    int n1 = this->numerator;
    int n2 = f.numerator;
    n1 = (this->sign)? n1 : -n1;
    n2 = (f.sign)? n2 : -n2;
    int d1 = this->denominator;
    int d2 = f.denominator;
    
    int _numerator = n1 * d2;
    int _denominator = d1 * n2;
    bool s = true;

    if (_numerator < 0)
    {
        s = not(s);
        _numerator = _numerator * (-1);
    }
    if (_denominator < 0)
    {
        s = not(s);
        _denominator = _denominator * (-1);
    }
    if (_numerator == 0)
    {
        s = 1;
    }

    f.normalize(_numerator, _denominator);
    return Fraction(_numerator, _denominator, s);
}
