#include <iostream>
#include <iomanip>
#include <cmath>
#include <chrono>

int main()
{
    const unsigned long long numSteps = 10000000ULL;
    const double referencePi = 3.14159265358979323846;

    const double step = 1.0 / static_cast<double>(numSteps);

    double sum = 0.0;

    auto start = std::chrono::high_resolution_clock::now();

    for (unsigned long long i = 0; i < numSteps; ++i)
    {
        double x = (static_cast<double>(i) + 0.5) * step;
        sum += 4.0 / (1.0 + x * x);
    }

    double pi = sum * step;

    auto end = std::chrono::high_resolution_clock::now();

    std::chrono::duration<double> elapsed = end - start;

    std::cout << std::setprecision(15);

    std::cout << "CPU result: " << pi << '\n';
    std::cout << "Error:      " << std::fabs(pi - referencePi) << '\n';
    std::cout << "Time:       " << elapsed.count() << " s\n";

    return 0;
}