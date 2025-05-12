#ifndef VEC2_H
#define VEC2_H

#include <iostream>
#include <cmath>

struct Vec2 {
    float x;
    float y;

    // ����� �������
    float length() const {
        return sqrt(x*x + y*y);
    }

    // ���������� ��������� << ��� ������ Vec2
    friend std::ostream& operator<<(std::ostream& os, const Vec2& vec) {
        os << "(" << vec.x << ", " << vec.y << ")";
        return os;
    }

    // ���������� ���������� ���������
    bool operator==(const Vec2& other) const {
        return x == other.x && y == other.y;
    }

    bool operator<(const Vec2& other) const {
        return length() < other.length();
    }

    bool operator>(const Vec2& other) const {
        return length() > other.length();
    }

    bool operator<=(const Vec2& other) const {
        return length() <= other.length();
    }

    bool operator>=(const Vec2& other) const {
        return length() >= other.length();
    }
};

#endif // VEC2_H