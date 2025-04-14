#include <iostream>
#include <string>
#include <vector>
#include <memory>
#include <cstdlib>
#include <ctime>
#include <iomanip>

// Класс для детали
class Part {
    string name;
    double norm;        // Норма выработки
    double actual;      // Фактическая выработка
public:
    Part(const string& name, double norm, double actual)
        : name(name), norm(norm), actual(actual) {}

    double getNorm() const { return norm; }
    double getActual() const { return actual; }
    double getCompletionRatio() const { return actual / norm; }
    const string& getName() const { return name; }
};