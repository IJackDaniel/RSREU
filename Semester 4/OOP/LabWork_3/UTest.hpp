#include "Fraction.hpp"

#ifndef UTEST_HPP
#define UTEST_HPP

class UnitTest
{
private:
    int count_test = 0;
    int count_pass = 0;
public:
    // Геттеры
    int get_count_test();
    int get_count_pass();

    void test_add(Fraction f1, Fraction f2, Fraction result);
    void test_sub(Fraction f1, Fraction f2, Fraction result);
    void test_mul(Fraction f1, Fraction f2, Fraction result);
    void test_div(Fraction f1, Fraction f2, Fraction result);
    void test_common_denominator(Fraction f1, Fraction f2, Fraction result_1, Fraction result_2);
    void test_comparing_fractions(Fraction f1, Fraction f2, int result);

    void testRandomFraction(int _x1, int _x2, int _y1, int _y2, int _z1, int _z2);
    void testObjectCountAll(const Object obj, const int exp);
    void testObjectCountActive(const Object obj, const int exp);
    void testOperations(const Object obj, const std::vector<std::string> exp);
    void testClearOperations(const Object obj, const int exp);
};

#endif