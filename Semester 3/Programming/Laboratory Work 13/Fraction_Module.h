#ifndef FRACTION_MODULE
#define FRACTION_MODULE

// Структура дроби
typedef struct
{
	int numerator;
	int denominator;
	bool sign;
} Fraction;

// Вывод меню выбора для дроби
void show_menu_frac();

// Создание дроби
Fraction CreateFraction(int n, int d, int sign);

// Сложение дробей
Fraction AddingFractions(const Fraction* frac1,const Fraction* frac2);

// Вычитание дробей
Fraction Subtraction_of_fractions(const Fraction* frac1, const Fraction* frac2);

// Умножение дробей
Fraction Multiplication_of_fractions(const Fraction* frac1, const Fraction* frac2);

// Деление дробей
Fraction Division_of_fractions(const Fraction* frac1, const Fraction* frac2);

// Приведение дробей к общему знаменателю
void CommonDenominator(Fraction *frac1, Fraction *frac2);

// Сравнение дробей
int Comparing_fractions(const Fraction* frac1, const Fraction* frac2);

// Вывод дроби
void PrintFraction(Fraction *frac);

#endif
