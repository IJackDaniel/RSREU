#ifndef FRACTION_MODULE
#define FRACTION_MODULE

// ��������� �����
typedef struct
{
	int numerator;
	int denominator;
	bool sign;
} Fraction;

// ����� ���� ������ ��� �����
void show_menu_frac();

// �������� �����
Fraction CreateFraction(int n, int d, int sign);

// �������� ������
Fraction AddingFractions(const Fraction* frac1,const Fraction* frac2);

// ��������� ������
Fraction Subtraction_of_fractions(const Fraction* frac1, const Fraction* frac2);

// ��������� ������
Fraction Multiplication_of_fractions(const Fraction* frac1, const Fraction* frac2);

// ������� ������
Fraction Division_of_fractions(const Fraction* frac1, const Fraction* frac2);

// ���������� ������ � ������ �����������
void CommonDenominator(Fraction *frac1, Fraction *frac2);

// ��������� ������
int Comparing_fractions(const Fraction* frac1, const Fraction* frac2);

// ����� �����
void PrintFraction(Fraction *frac);

#endif
