#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

typedef struct
{
	int numerator;
	int denominator;
	bool sign;
} Fraction;

void show_menu() 
{
	printf("\nSelect a function:\n");
	printf("1. Creating a fraction;\n");
	printf("2. Adding fractions;\n");
	printf("3. Subtraction of fractions;\n");
	printf("4. Multiplication of fractions;\n");
	printf("5. Division of fractions;\n");
	printf("6. Bringing to a common denominator;\n");
	printf("7. Comparing fractions;\n");
	printf("8. Fraction output;\n");
	printf("9. Exiting the program;\n");
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
		printf("Error! Incorrect sign selection\n");
		exit(1);
	}
	if (d == 0) 
	{
		printf("Error! The denominator cannot be zero!\n");
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

void CommonDenominator(Fraction *frac1, Fraction *frac2){
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

void PrintFraction(Fraction *frac){
	printf("\n%c %d / %d\n", (frac->sign ? '+' : '-'), frac->numerator, frac->denominator);
}

int main() 
{
	int sign_1, sign_2;
	int n_1, n_2;
	int d_1, d_2;
	Fraction frac_1;
	Fraction frac_2;
	
	int res;
	Fraction frac_res;
	
	show_menu();
	int choice;
	do 
	{
		printf("\nChoice function: ");
		scanf("%d", &choice);	
		
		switch (choice) 
		{
			case 1:
				printf("\nEnter a fraction: \nNumerator/Denominator/Sign(0 - minus; 1 - plus): ");
				scanf("%d %d %d", &n_1, &d_1, &sign_1);
				frac_1 = CreateFraction(n_1, d_1, sign_1);
				PrintFraction(&frac_1);
				break;
			case 2:
				printf("\nEnter a first fraction: \nNumerator/Denominator/Sign(0 - minus; 1 - plus): ");
				scanf("%d %d %d", &n_1, &d_1, &sign_1);
				frac_1 = CreateFraction(n_1, d_1, sign_1);
				
				printf("\nEnter a second fraction: \nNumerator/Denominator/Sign(0 - minus; 1 - plus): ");
				scanf("%d %d %d", &n_2, &d_2, &sign_2);
				frac_2 = CreateFraction(n_2, d_2, sign_2);
				
				frac_res = AddingFractions(&frac_1, &frac_2);
				PrintFraction(&frac_res);
				break;
			case 3:
				printf("\nEnter a first fraction: \nNumerator/Denominator/Sign(0 - minus; 1 - plus): ");
				scanf("%d %d %d", &n_1, &d_1, &sign_1);
				frac_1 = CreateFraction(n_1, d_1, sign_1);
				
				printf("\nEnter a second fraction: \nNumerator/Denominator/Sign(0 - minus; 1 - plus): ");
				scanf("%d %d %d", &n_2, &d_2, &sign_2);
				frac_2 = CreateFraction(n_2, d_2, sign_2);
				
				frac_res = Subtraction_of_fractions(&frac_1, &frac_2);
				PrintFraction(&frac_res);
				break;
			case 4:
				printf("\nEnter a first fraction: \nNumerator/Denominator/Sign(0 - minus; 1 - plus): ");
				scanf("%d %d %d", &n_1, &d_1, &sign_1);
				frac_1 = CreateFraction(n_1, d_1, sign_1);
				
				printf("\nEnter a second fraction: \nNumerator/Denominator/Sign(0 - minus; 1 - plus): ");
				scanf("%d %d %d", &n_2, &d_2, &sign_2);
				frac_2 = CreateFraction(n_2, d_2, sign_2);
				
				frac_res = Multiplication_of_fractions(&frac_1, &frac_2);
				PrintFraction(&frac_res);
				break;
			case 5:
				printf("\nEnter a first fraction: \nNumerator/Denominator/Sign(0 - minus; 1 - plus): ");
				scanf("%d %d %d", &n_1, &d_1, &sign_1);
				frac_1 = CreateFraction(n_1, d_1, sign_1);
				
				printf("\nEnter a second fraction: \nNumerator/Denominator/Sign(0 - minus; 1 - plus): ");
				scanf("%d %d %d", &n_2, &d_2, &sign_2);
				frac_2 = CreateFraction(n_2, d_2, sign_2);
				
				frac_res = Division_of_fractions(&frac_1, &frac_2);
				PrintFraction(&frac_res);
				break;
			case 6:
				printf("\nEnter a first fraction: \nNumerator/Denominator/Sign(0 - minus; 1 - plus): ");
				scanf("%d %d %d", &n_1, &d_1, &sign_1);
				frac_1 = CreateFraction(n_1, d_1, sign_1);
				
				printf("\nEnter a second fraction: \nNumerator/Denominator/Sign(0 - minus; 1 - plus): ");
				scanf("%d %d %d", &n_2, &d_2, &sign_2);
				frac_2 = CreateFraction(n_2, d_2, sign_2);
				
				CommonDenominator(&frac_1, &frac_2);
				PrintFraction(&frac_1);
				PrintFraction(&frac_2);
				break;
			case 7:
				printf("\nEnter a first fraction: \nNumerator/Denominator/Sign(0 - minus; 1 - plus): ");
				scanf("%d %d %d", &n_1, &d_1, &sign_1);
				frac_1 = CreateFraction(n_1, d_1, sign_1);
				
				printf("\nEnter a second fraction: \nNumerator/Denominator/Sign(0 - minus; 1 - plus): ");
				scanf("%d %d %d", &n_2, &d_2, &sign_2);
				frac_2 = CreateFraction(n_2, d_2, sign_2);
				
				res = Comparing_fractions(&frac_1, &frac_2);
				if (res == 0) 
				{
					printf("\nFractions are equal\n");
				}
				else if (res == 1) 
				{
					printf("\nThe first fraction is larger\n");
				}
				else 
				{
					printf("\nThe first fraction is smaller\n");
				}
				break;
			case 8:
				printf("\nEnter a fraction: \nNumerator/Denominator/Sign(0 - minus; 1 - plus): ");
				scanf("%d %d %d", &n_1, &d_1, &sign_1);
				frac_1 = CreateFraction(n_1, d_1, sign_1);
				PrintFraction(&frac_1);
				break;
			case 9:
				break;
			default:
				show_menu();
				break;
		} 
	} while (choice != 9);
	
	printf("\nThe work of the program is completed.\n");
	return 0;
}
