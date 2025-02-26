#include <iostream>

using namespace std;

// Структура дроби
struct  Fraction
{
	int numerator;
	int denominator;
	bool sign;
};

// Наибольший общий делитель для двух чисел
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

// Наибольший общий делитель для трёх чисел
int greatest_common_divisor_three_num(int num1, int num2, int num3) 
{
	return greatest_common_divisor(greatest_common_divisor(num1, num2), num3);
}

// Функция нормализации дроби
void normalize(Fraction* frac)
{
    int gcd = greatest_common_divisor(frac->numerator, frac->denominator);
	frac->numerator /= gcd;
	frac->denominator /= gcd;
}

// Проверка корректности вводимых значений
bool correct_fraction(int d, int sign)
{
    if (sign > 1 || sign < 0) 
	{
		cout << "Error! Incorrect sign selection\n" << endl;
		return false;
	}
	if (d == 0) 
	{
		cout << "Error! The denominator cannot be zero!" << endl;
		return false;
	}
    return true;
}

Fraction create_fraction(int n, int d, int sign)
{
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
	return create_fraction(numerator, denominator, 1); 
}

Fraction Subtraction_of_fractions(const Fraction* frac1, const Fraction* frac2) 
{
	int n1 = frac1->sign == 1 ? frac1->numerator : -frac1->numerator;
	int n2 = frac2->sign == 1 ? frac2->numerator : -frac2->numerator;
	
	int denominator = frac1->denominator * frac2->denominator;
	int numerator = n1 * frac2->denominator - n2 * frac1->denominator;
	return create_fraction(numerator, denominator, 1); 
}

Fraction Multiplication_of_fractions(const Fraction* frac1, const Fraction* frac2) 
{
	int n1 = frac1->sign == 1 ? frac1->numerator : -frac1->numerator;
	int n2 = frac2->sign == 1 ? frac2->numerator : -frac2->numerator;

	int denominator = frac1->denominator * frac2->denominator;
	int numerator = n1 * n2;
	return create_fraction(numerator, denominator, 1); 
}

Fraction Division_of_fractions(const Fraction* frac1, const Fraction* frac2) 
{
	int n1 = frac1->sign == 1 ? frac1->numerator : -frac1->numerator;
	int n2 = frac2->sign == 1 ? frac2->numerator : -frac2->numerator;
	
	int denominator = frac1->denominator * n2;
	int numerator = n1 * frac2->denominator;
	return create_fraction(numerator, denominator, 1); 
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
	string s = frac->sign == 1? "+" : "-";
	cout << "[" << s << "] " << frac->numerator << "/" << frac->denominator << endl;
}

void show_menu() 
{
	cout << "Select a function:" << endl;
	cout << "1. Creating a fraction;" << endl;
	cout << "2. Adding fractions;" << endl;
	cout << "3. Subtraction of fractions;" << endl;
	cout << "4. Multiplication of fractions;" << endl;
	cout << "5. Division of fractions;" << endl;
	cout << "6. Bringing to a common denominator;" << endl;
	cout << "7. Comparing fractions;" << endl;
	cout << "8. Fraction output;" << endl;
	cout << "9. Exiting the program;" << endl;
}

int main()
{
	int sign_1, sign_2;
	int n_1, n_2;
	int d_1, d_2;
	Fraction frac_1;
	Fraction frac_2;

	int result_cmp;
	Fraction res_frac;
    
	show_menu();
	int choice;
	do
	{
		cout << "Choice function: ";
		cin >> choice;
		
		switch (choice)
		{
		case 1:
			cout << endl << "Enter a fraction:" << endl << "Numerator/Denominator/Sign(0 - minus; 1 - plus): ";
			cin >> n_1 >> d_1 >> sign_1;
			frac_1 = create_fraction(n_1, d_1, sign_1);
			PrintFraction(&frac_1);
			break;
		
		case 2:
			cout << endl << "Enter a first fraction:" << endl << "Numerator/Denominator/Sign(0 - minus; 1 - plus): ";
			cin >> n_1 >> d_1 >> sign_1;
			frac_1 = create_fraction(n_1, d_1, sign_1);

			cout << "Enter a ssecond fraction:" << endl << "Numerator/Denominator/Sign(0 - minus; 1 - plus): ";
			cin >> n_2 >> d_2 >> sign_2;
			frac_2 = create_fraction(n_2, d_2, sign_2);
			
			res_frac = AddingFractions(&frac_1, &frac_2);
			PrintFraction(&res_frac);
			break;
		
		case 3:
			cout << endl << "Enter a first fraction:" << endl << "Numerator/Denominator/Sign(0 - minus; 1 - plus): ";
			cin >> n_1 >> d_1 >> sign_1;
			frac_1 = create_fraction(n_1, d_1, sign_1);

			cout << "Enter a ssecond fraction:" << endl << "Numerator/Denominator/Sign(0 - minus; 1 - plus): ";
			cin >> n_2 >> d_2 >> sign_2;
			frac_2 = create_fraction(n_2, d_2, sign_2);
			
			res_frac = Subtraction_of_fractions(&frac_1, &frac_2);
			PrintFraction(&res_frac);
			break;
		
		case 4:
			cout << endl << "Enter a first fraction:" << endl << "Numerator/Denominator/Sign(0 - minus; 1 - plus): ";
			cin >> n_1 >> d_1 >> sign_1;
			frac_1 = create_fraction(n_1, d_1, sign_1);

			cout << "Enter a ssecond fraction:" << endl << "Numerator/Denominator/Sign(0 - minus; 1 - plus): ";
			cin >> n_2 >> d_2 >> sign_2;
			frac_2 = create_fraction(n_2, d_2, sign_2);
			
			res_frac = Multiplication_of_fractions(&frac_1, &frac_2);
			PrintFraction(&res_frac);
			break;

		case 5:
			cout << endl << "Enter a first fraction:" << endl << "Numerator/Denominator/Sign(0 - minus; 1 - plus): ";
			cin >> n_1 >> d_1 >> sign_1;
			frac_1 = create_fraction(n_1, d_1, sign_1);

			cout << "Enter a ssecond fraction:" << endl << "Numerator/Denominator/Sign(0 - minus; 1 - plus): ";
			cin >> n_2 >> d_2 >> sign_2;
			frac_2 = create_fraction(n_2, d_2, sign_2);
			
			res_frac = Division_of_fractions(&frac_1, &frac_2);
			PrintFraction(&res_frac);
			break;
		
		case 6:
			cout << endl << "Enter a first fraction:" << endl << "Numerator/Denominator/Sign(0 - minus; 1 - plus): ";
			cin >> n_1 >> d_1 >> sign_1;
			frac_1 = create_fraction(n_1, d_1, sign_1);

			cout << "Enter a ssecond fraction:" << endl << "Numerator/Denominator/Sign(0 - minus; 1 - plus): ";
			cin >> n_2 >> d_2 >> sign_2;
			frac_2 = create_fraction(n_2, d_2, sign_2);
			
			CommonDenominator(&frac_1, &frac_2);
			PrintFraction(&frac_1);
			PrintFraction(&frac_2);
			break;

		case 7:
			cout << endl << "Enter a first fraction:" << endl << "Numerator/Denominator/Sign(0 - minus; 1 - plus): ";
			cin >> n_1 >> d_1 >> sign_1;
			frac_1 = create_fraction(n_1, d_1, sign_1);

			cout << "Enter a ssecond fraction:" << endl << "Numerator/Denominator/Sign(0 - minus; 1 - plus): ";
			cin >> n_2 >> d_2 >> sign_2;
			frac_2 = create_fraction(n_2, d_2, sign_2);

			result_cmp = Comparing_fractions(&frac_1, &frac_2);
			if (result_cmp == 0)
			{
				cout << "\nFractions are equal\n";
			}
			else if (result_cmp == 1) 
			{
				cout << "\nThe first fraction is larger\n";
			}
			else 
			{
				cout << "\nThe first fraction is smaller\n";
			}
			break;
		
		case 8:
			cout << endl << "Enter a first fraction:" << endl << "Numerator/Denominator/Sign(0 - minus; 1 - plus): ";
			cin >> n_1 >> d_1 >> sign_1;
			frac_1 = create_fraction(n_1, d_1, sign_1);
			PrintFraction(&frac_1);
			break;

		case 9:
			break;

		default:
			show_menu();
			break;
		}
	} 
	while (choice != 9);
	


	 
}