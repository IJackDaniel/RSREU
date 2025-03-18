#include <iostream>
#include <string>
#include <cstdlib>
#include <ctime>
#include "Fraction.hpp"
#include "UTest.hpp"

int main()
{
    setlocale(LC_ALL, "Russian");
    srand(time(0));
    UnitTest test;

    test.test_add(Fraction(1, 2, 1), Fraction(5, 6, 1), Fraction(4, 3, 1));
    test.test_sub(Fraction(1, 2, 1), Fraction(5, 6, 1), Fraction(1, 3, 0));
    test.test_mul(Fraction(1, 2, 1), Fraction(5, 6, 1), Fraction(5, 12, 1));
    test.test_div(Fraction(1, 2, 1), Fraction(5, 6, 1), Fraction(3, 5, 1));
    test.test_common_denominator(Fraction(7, 9, 1), Fraction(4, 5, 0), Fraction(35, 45, 1, 1), Fraction(36, 45, 0, 1));
    test.test_comparing_fractions(Fraction(1, 2, 1), Fraction(1, 3, 1), 1);
    test.test_add(Fraction(1, 2, 1), Fraction(1, 2, 0), Fraction(0, 1, 1));
    test.test_sub(Fraction(7, 10, 1), Fraction(14, 20, 1), Fraction(0, 1, 1));
    test.test_mul(Fraction(19, 5, 1), Fraction(0, 6, 1), Fraction(0, 1, 1));
    test.test_comparing_fractions(Fraction(-3, 17, 1), Fraction(199, 5, 1), -1);
    test.test_comparing_fractions(Fraction(1, 3, 1), Fraction(19, 57, 1), 0);
    
    test.testRandomFraction(1, 7, 8, 16, 0, 1);
    test.testObjectCountAll(Object(), 40);
    test.testObjectCountActive(Object(), 41);
    test.testOperations(Object("Operation 1"), {"Operation 1"});
    test.testClearOperations(Object(), 0);
    

    int count = test.get_count_test();
    int pass = test.get_count_pass();
    std::cout << "Total tests completed: " << pass << "/" << count << std::endl << std::endl;
}
