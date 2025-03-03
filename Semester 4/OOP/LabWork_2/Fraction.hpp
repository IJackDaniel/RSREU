#ifndef FRACTION_HPP
#define FRACTION_HPP

class Fraction
{
    private:
        int numerator;
        int denominator;
        bool sign;

        int greatest_common_divisor(int num1, int num2);
        int greatest_common_divisor_three_num(int num1, int num2, int num3);
        void normalize(int& num1, int& num2);
        
    public:
        // Конструктор класса
        Fraction(int _numerator, int _denominator, int _sign);

        // Геттеры
        int get_numerator();
        int get_denominator();
        bool get_sign();

        // Сеттеры
        void set_numerator(int num);
        void set_denominator(int num);
        void set_sign(int new_sign);

        // Методы
        void add_frac(Fraction f1, Fraction f2);
};
#endif
