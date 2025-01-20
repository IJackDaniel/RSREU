#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#include "Fraction_Module.h"



void show_menu_frac() 
{
	printf("\n���� �������\n");
	printf("1. �������� �����;\n");
	printf("2. �������� ������;\n");
	printf("3. ��������� ������;\n");
	printf("4. ��������� ������;\n");
	printf("5. ������� ������;\n");
	printf("6. ���������� ������ � ������ �����������;\n");
	printf("7. ��������� ������;\n");
	printf("8. ����� �����;\n");
	printf("9. ����� �� ���������;\n");
}

int greatest_common_divisor(int num1, int num2) 
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

int greatest_common_divisor_three_num(int num1, int num2, int num3) 
{
	return greatest_common_divisor(greatest_common_divisor(num1, num2), num3);
}

void normalize(Fraction* frac) 
{
	int gcd = greatest_common_divisor(frac->numerator, frac->denominator);
	frac->numerator /= gcd;
	frac->denominator /= gcd;
}

Fraction CreateFraction(int n, int d, int sign) 
{
	if (sign > 1 || sign < 0) 
	{
		printf("������! ������������ ����� ����� �����\n");
		exit(1);
	}
	if (d == 0) 
	{
		printf("������! ����������� �� ����� ��������� ����\n");
		exit(2);
	}
	if (d < 0) 
	{
		d = -d;
		sign = sign == 1 ? 0 : 1;
	}
	if (n < 0) 
	{
		n = -n;
		sign = sign == 1 ? 0 : 1;
	}
	Fraction frac;
	frac.denominator = d;
	frac.numerator = n;
	frac.sign = sign == 1 ? true : false;
	normalize(&frac);
	return frac;
}

Fraction AddingFractions(const Fraction* frac1,const Fraction* frac2) 
{
	int n1 = frac1->sign == 1 ? frac1->numerator : -frac1->numerator;
	int n2 = frac2->sign == 1 ? frac2->numerator : -frac2->numerator;
	
	int denominator = frac1->denominator * frac2->denominator;
	int numerator = n1 * frac2->denominator + n2 * frac1->denominator;
	return CreateFraction(numerator, denominator, 1); 
}

Fraction Subtraction_of_fractions(const Fraction* frac1, const Fraction* frac2) 
{
	int n1 = frac1->sign == 1 ? frac1->numerator : -frac1->numerator;
	int n2 = frac2->sign == 1 ? frac2->numerator : -frac2->numerator;
	
	int denominator = frac1->denominator * frac2->denominator;
	int numerator = n1 * frac2->denominator - n2 * frac1->denominator;
	return CreateFraction(numerator, denominator, 1); 
}

Fraction Multiplication_of_fractions(const Fraction* frac1, const Fraction* frac2) 
{
	int n1 = frac1->sign == 1 ? frac1->numerator : -frac1->numerator;
	int n2 = frac2->sign == 1 ? frac2->numerator : -frac2->numerator;

	int denominator = frac1->denominator * frac2->denominator;
	int numerator = n1 * n2;
	return CreateFraction(numerator, denominator, 1); 
}

Fraction Division_of_fractions(const Fraction* frac1, const Fraction* frac2) 
{
	int n1 = frac1->sign == 1 ? frac1->numerator : -frac1->numerator;
	int n2 = frac2->sign == 1 ? frac2->numerator : -frac2->numerator;
	
	int denominator = frac1->denominator * n2;
	int numerator = n1 * frac2->denominator;
	return CreateFraction(numerator, denominator, 1); 
}

void CommonDenominator(Fraction *frac1, Fraction *frac2)
{
	if (frac1->denominator != frac2->denominator)
	{
		int common = frac1->denominator * frac2->denominator;
		frac1->numerator = frac1->numerator * frac2->denominator;
		frac2->numerator = frac2->numerator * frac1->denominator;
		frac1->denominator = common;
		frac2->denominator = common;
		
		int gcd = greatest_common_divisor_three_num(frac1->numerator, frac2->numerator, frac1->denominator);
		frac1->denominator /= gcd;
		frac1->numerator /= gcd;
		frac2->denominator /= gcd;
		frac2->numerator /= gcd;
	}
}

int Comparing_fractions(const Fraction* frac1, const Fraction* frac2) 
{
	int num1 = frac1->numerator * frac2->denominator;
	int num2 = frac2->numerator * frac1->denominator;
	if (!frac1->sign) 
	{
		num1 *= -1;
	}
	if (!frac2->sign) 
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

void PrintFraction(Fraction *frac)
{
	printf("\n%c %d / %d\n", (frac->sign ? '+' : '-'), frac->numerator, frac->denominator);
}
