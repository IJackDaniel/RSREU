#include "Fraction.hpp"

#ifndef UTEST_HPP
#define UTEST_HPP

class UnitTest
{
public:
    void test_add(Fraction f1, Fraction f2, Fraction result);
    void test_sub(Fraction f1, Fraction f2, Fraction result);
    void test_mul(Fraction f1, Fraction f2, Fraction result);
    void test_div(Fraction f1, Fraction f2, Fraction result);

    void test_common_denominator(Fraction f1, Fraction f2, Fraction result_1, Fraction result_2);
    void test_comparing_fractions(Fraction f1, Fraction f2, int result);
};

#endif