#include <iostream>
#include <string>
#include <vector>
#include <memory>
#include <cstdlib>
#include <ctime>
#include <iomanip>
#include <limits> 
#include <unordered_map>

using namespace std;

#include "Part.hpp"

Part::Part(const string& name, double norm, double actual)
    : name(name), norm(norm), actual(actual) {}

double Part::getNorm() const { return norm; }
double Part::getActual() const { return actual; }
double Part::getCompletionRatio() const { return actual / norm; }
const string& Part::getName() const { return name; }
