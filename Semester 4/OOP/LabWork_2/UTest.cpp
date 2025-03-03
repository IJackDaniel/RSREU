#include "UTest.hpp"
#include "Fraction.hpp"

#include <iostream>
#include <string>

void UnitTest::test_add(Fraction f1, Fraction f2, Fraction result)
{
    // assert 1
    std::cout << "_________________________________________" << std::endl;
    std::cout << "Fraction Sum Test" << std::endl;
    std::cout << "Input data: " << std::endl;
    f1.show();
    f2.show();

    // act
    f1.add_frac(f2);

    // assert 2
    std::cout << "Expected result: " << std::endl;
    result.show();
    std::cout << "Result of the operation: " << std::endl;
    f1.show();
    if(f1.get_numerator() == result.get_numerator() && f1.get_denominator() == result.get_denominator() && f1.get_sign() == result.get_sign() ) 
    {
        std::cout << "Test was passed" << std::endl;
    } else {
        std::cout << "Test was not passed" << std::endl;
    }
    std::cout << "_________________________________________" << std::endl;
    std::cout << std::endl;
}

void UnitTest::test_sub(Fraction f1, Fraction f2, Fraction result)
{
    // assert 1
    std::cout << "_________________________________________" << std::endl;
    std::cout << "Fraction Subtraction Test" << std::endl;
    std::cout << "Input data: " << std::endl;
    f1.show();
    f2.show();

    // act
    f1.sub_frac(f2);

    // assert 2
    std::cout << "Expected result: " << std::endl;
    result.show();
    std::cout << "Result of the operation: " << std::endl;
    f1.show();
    if(f1.get_numerator() == result.get_numerator() && f1.get_denominator() == result.get_denominator() && f1.get_sign() == result.get_sign() ) 
    {
        std::cout << "Test was passed" << std::endl;
    } else {
        std::cout << "Test was not passed" << std::endl;
    }
    std::cout << "_________________________________________" << std::endl;
    std::cout << std::endl;
}

void UnitTest::test_mul(Fraction f1, Fraction f2, Fraction result)
{
    // assert 1
    std::cout << "_________________________________________" << std::endl;
    std::cout << "Fraction Multiplication Test" << std::endl;
    std::cout << "Input data: " << std::endl;
    f1.show();
    f2.show();

    // act
    f1.mul_frac(f2);

    // assert 2
    std::cout << "Expected result: " << std::endl;
    result.show();
    std::cout << "Result of the operation: " << std::endl;
    f1.show();
    if(f1.get_numerator() == result.get_numerator() && f1.get_denominator() == result.get_denominator() && f1.get_sign() == result.get_sign() ) 
    {
        std::cout << "Test was passed" << std::endl;
    } else {
        std::cout << "Test was not passed" << std::endl;
    }
    std::cout << "_________________________________________" << std::endl;
    std::cout << std::endl;
}

void UnitTest::test_div(Fraction f1, Fraction f2, Fraction result)
{
    // assert 1
    std::cout << "_________________________________________" << std::endl;
    std::cout << "Fraction Division Test" << std::endl;
    std::cout << "Input data: " << std::endl;
    f1.show();
    f2.show();

    // act
    f1.div_frac(f2);

    // assert 2
    std::cout << "Expected result: " << std::endl;
    result.show();
    std::cout << "Result of the operation: " << std::endl;
    f1.show();
    if(f1.get_numerator() == result.get_numerator() && f1.get_denominator() == result.get_denominator() && f1.get_sign() == result.get_sign() ) 
    {
        std::cout << "Test was passed" << std::endl;
    } else {
        std::cout << "Test was not passed" << std::endl;
    }
    std::cout << "_________________________________________" << std::endl;
    std::cout << std::endl;
}

void UnitTest::test_common_denominator(Fraction f1, Fraction f2, Fraction result_1, Fraction result_2)
{
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
    } else {
        std::cout << "Test was not passed" << std::endl;
    }
    std::cout << "_________________________________________" << std::endl;
    std::cout << std::endl;
}

void UnitTest::test_comparing_fractions(Fraction f1, Fraction f2, int result)
{
    // act
    int real_val = f1.comparing_fractions(f2);
    
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
    } else {
        std::cout << "Test was not passed" << std::endl;
    }
    std::cout << "_________________________________________" << std::endl;
    std::cout << std::endl;
}
