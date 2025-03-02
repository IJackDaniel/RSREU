#ifndef FRACTION_HPP
#define FRACTION_HPP

class Fraction
{
    private:
        int numerator;
        int denominator;
        bool sign;
        
    public:
        // Конструктор класса
        Fraction(int _numerator, int _denominator, int _sign);

        // Геттеры
        int get_numerator();

        // Сеттеры

        // Методы
};
#endif