#include "UTest.hpp"
#include "Fraction.hpp"

#include <iostream>
#include <string>

int UnitTest::get_count_test()
{
    return count_test;
}

int UnitTest::get_count_pass()
{
    return count_pass;
}

void UnitTest::test_add(Fraction f1, Fraction f2, Fraction result)
{
    count_test++;

    // act
    Fraction result_real = f1 + f2;

    // assert 
    std::cout << "_________________________________________" << std::endl;
    std::cout << "Fraction Sum Test" << std::endl;
    std::cout << "Input data: " << std::endl;
    f1.show();
    f2.show();

    std::cout << "Expected result: " << std::endl;
    result.show();
    std::cout << "Result of the operation: " << std::endl;
    result_real.show();
    if(result_real.get_numerator() == result.get_numerator() && result_real.get_denominator() == result.get_denominator() && result_real.get_sign() == result.get_sign() ) 
    {
        std::cout << "Test was passed" << std::endl;
        count_pass++;
    } else {
        std::cout << "Test was not passed" << std::endl;
    }
    std::cout << "_________________________________________" << std::endl;
    std::cout << std::endl;
}

void UnitTest::test_sub(Fraction f1, Fraction f2, Fraction result)
{
    count_test++;
    
    // act
    Fraction result_real = f1 - f2;

    // assert
    std::cout << "_________________________________________" << std::endl;
    std::cout << "Fraction Subtraction Test" << std::endl;
    std::cout << "Input data: " << std::endl;
    f1.show();
    f2.show();

    std::cout << "Expected result: " << std::endl;
    result.show();
    std::cout << "Result of the operation: " << std::endl;
    result_real.show();
    if(result_real.get_numerator() == result.get_numerator() && result_real.get_denominator() == result.get_denominator() && result_real.get_sign() == result.get_sign() ) 
    {
        std::cout << "Test was passed" << std::endl;
        count_pass++;
    } else {
        std::cout << "Test was not passed" << std::endl;
    }
    std::cout << "_________________________________________" << std::endl;
    std::cout << std::endl;
}

void UnitTest::test_mul(Fraction f1, Fraction f2, Fraction result)
{
    count_test++;

    // act
    Fraction result_real = f1 * f2;

    // assert 
    std::cout << "_________________________________________" << std::endl;
    std::cout << "Fraction Multiplication Test" << std::endl;
    std::cout << "Input data: " << std::endl;
    f1.show();
    f2.show();

    std::cout << "Expected result: " << std::endl;
    result.show();
    std::cout << "Result of the operation: " << std::endl;
    result_real.show();
    if(result_real.get_numerator() == result.get_numerator() && result_real.get_denominator() == result.get_denominator() && result_real.get_sign() == result.get_sign() ) 
    {
        std::cout << "Test was passed" << std::endl;
        count_pass++;
    } else {
        std::cout << "Test was not passed" << std::endl;
    }
    std::cout << "_________________________________________" << std::endl;
    std::cout << std::endl;
}

void UnitTest::test_div(Fraction f1, Fraction f2, Fraction result)
{
    count_test++;

    // act
    Fraction result_real = f1 / f2;

    // assert 
    std::cout << "_________________________________________" << std::endl;
    std::cout << "Fraction Division Test" << std::endl;
    std::cout << "Input data: " << std::endl;
    f1.show();
    f2.show();

    std::cout << "Expected result: " << std::endl;
    result.show();
    std::cout << "Result of the operation: " << std::endl;
    result_real.show();
    if(result_real.get_numerator() == result.get_numerator() && result_real.get_denominator() == result.get_denominator() && result_real.get_sign() == result.get_sign() ) 
    {
        std::cout << "Test was passed" << std::endl;
        count_pass++;
    } else {
        std::cout << "Test was not passed" << std::endl;
    }
    std::cout << "_________________________________________" << std::endl;
    std::cout << std::endl;
}

void UnitTest::test_common_denominator(Fraction f1, Fraction f2, Fraction result_1, Fraction result_2)
{
    count_test++;

    // assert 1
    std::cout << "_________________________________________" << std::endl;
    std::cout << "Test of reduction to a common denominator" << std::endl;
    std::cout << "Input data: " << std::endl;
    f1.show();
    f2.show();

    // act
    f1.common_denominator(f2);

    // assert 2
    std::cout << "Expected result: " << std::endl;
    result_1.show();
    result_2.show();
    std::cout << "Result of the operation: " << std::endl;
    f1.show();
    f2.show();
    if(f1.get_numerator() == result_1.get_numerator() && f1.get_denominator() == result_1.get_denominator() && f1.get_sign() == result_1.get_sign() &&
    f2.get_numerator() == result_2.get_numerator() && f2.get_denominator() == result_2.get_denominator() && f2.get_sign() == result_2.get_sign()) 
    {
        std::cout << "Test was passed" << std::endl;
        count_pass++;
    } else {
        std::cout << "Test was not passed" << std::endl;
    }
    std::cout << "_________________________________________" << std::endl;
    std::cout << std::endl;
}

void UnitTest::test_comparing_fractions(Fraction f1, Fraction f2, int result)
{
    count_test++;

    // act
    int real_val = f1 == f2;
    
    // assert
    std::cout << "_________________________________________" << std::endl;
    std::cout << "Fraction Comparison Test" << std::endl;
    std::cout << "Input data: " << std::endl;
    f1.show();
    f2.show();
    std::cout << "Expected result: " << std::endl;
    std::cout << result << std::endl;
    std::cout << "Result of the operation: " << std::endl;
    std::cout << real_val << std::endl;
    if(real_val == result) 
    {
        std::cout << "Test was passed" << std::endl;
        count_pass++;
    } else {
        std::cout << "Test was not passed" << std::endl;
    }
    std::cout << "_________________________________________" << std::endl;
    std::cout << std::endl;
}

void UnitTest::testRandomFraction(int _x1, int _x2, int _y1, int _y2, int _z1, int _z2)
{
    count_test++;

    // act
    Fraction f = Fraction(_x1, _x2, _y1, _y2, _z1, _z2);

    // assert
    std::cout << "_________________________________________" << std::endl;
    std::cout << "Test: Random Vector" << std::endl;
    if(f.get_numerator() >= _x1 && f.get_numerator() <= _x2 && f.get_denominator() >= _y1 && f.get_denominator() <= _y2 && f.get_sign() >= _z1 && f.get_sign() <= _z2) {
        std::cout << "Test was passed" << std::endl;
        count_pass++;
    } else {
        std::cout << "Test was not passed" << std::endl;
    }
    std::cout << "_________________________________________" << std::endl;
    std::cout << std::endl;
}

void UnitTest::testObjectCountAll(const Object obj, const int exp)
{
    // act
    count_test++;

    // assert
    std::cout << "_________________________________________" << std::endl;
    std::cout << "Test: Count of all elements" << std::endl;
    // std::cout << obj.getCountAllElements() << std::endl;
    if(obj.getCountAllElements() == exp) {
        std::cout << "Test was passed" << std::endl;
        count_pass++;
    } else {
        std::cout << "Test was not passed" << std::endl;
    }
    std::cout << "_________________________________________" << std::endl;
    std::cout << std::endl;
}

void UnitTest::testObjectCountActive(const Object obj, const int exp)
{
    // act
    count_test++;

    // assert
    std::cout << "_________________________________________" << std::endl;
    std::cout << "Test: Count of active elements" << std::endl;
    if(obj.getCountActiveElements() == exp) {
        std::cout << "Test was passed" << std::endl;
        count_pass++;
    } else {
        std::cout << "Test was not passed" << std::endl;
    }
    std::cout << "_________________________________________" << std::endl;
    std::cout << std::endl;
}

void UnitTest::testOperations(Object obj, const std::vector<std::string> exp)
{
    // act
    count_test++;

    // assert
    std::cout << "_________________________________________" << std::endl;
    std::cout << "Test : Operations" << std::endl;
    if(obj.getOperations() == exp) {
        std::cout << "Test was passed" << std::endl;
        count_pass++;
    } else {
        std::cout << "Test was not passed" << std::endl;
    }
    std::cout << "_________________________________________" << std::endl;
    std::cout << std::endl;
}

void UnitTest::testClearOperations(Object obj, const int exp)
{
    // act
    obj.clearOp();
    count_test++;

    // assert
    std::cout << "_________________________________________" << std::endl;
    std::cout << "Test: Clear operations" << std::endl;
    if(obj.getCountOps() == exp) {
        std::cout << "Test was passed" << std::endl;
        count_pass++;
    } else {
        std::cout << "Test was not passed" << std::endl;
    }
    std::cout << "_________________________________________" << std::endl;
    std::cout << std::endl;
}
