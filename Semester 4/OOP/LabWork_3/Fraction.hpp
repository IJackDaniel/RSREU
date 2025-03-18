#ifndef FRACTION_HPP
#define FRACTION_HPP

#include "Object.hpp"

class Fraction : public Object
{
    private:
        int greatest_common_divisor(int num1, int num2);
        int greatest_common_divisor_three_num(int num1, int num2, int num3);
        void normalize(int& num1, int& num2);

    protected:
        int numerator;
        int denominator;
        bool sign;
        
    public:
        // Конструктор класса
        Fraction(int _numerator, int _denominator, int _sign);
        // Конструктор класса для юнит тестов
        Fraction(int _numerator, int _denominator, int _sign, int for_test);
        // Конструктор со значениями из диапазона
        Fraction(int _x1, int _x2, int _y1, int _y2, int _z1, int _z2);

        // Вывод 
        void show();

        // Геттеры
        int get_numerator();
        int get_denominator();
        bool get_sign();

        // Сеттеры
        void set_numerator(int num);
        void set_denominator(int num);
        void set_sign(int new_sign);

        // Методы
        Fraction operator + (Fraction& v) const;
        Fraction operator - (Fraction& v) const;
        Fraction operator * (Fraction& v) const;
        Fraction operator / (Fraction& v) const;

        void common_denominator(Fraction& f);
        int operator == (const Fraction v) const;
};
#endif
