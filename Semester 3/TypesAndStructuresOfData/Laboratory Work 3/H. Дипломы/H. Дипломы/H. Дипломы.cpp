// H. Дипломы
#include <iostream>

int good(long long x, long long w, long long h, long long n) {
    return (x / w) * (x / h) >= n;
}

int main()
{
    long long w, h, n;
    std::cin >> w >> h >> n;
    long long l = 0;
    long long r = 1;
    while (!good(r, w, h, n)) {
        r = r * 2;
    }
    while (r - l > 1) {
        long long m = (l + r) / 2;
        if (good(m, w, h, n)) {
            r = m;
        }
        else {
            l = m;
        }
    }
    std::cout << r;
}

