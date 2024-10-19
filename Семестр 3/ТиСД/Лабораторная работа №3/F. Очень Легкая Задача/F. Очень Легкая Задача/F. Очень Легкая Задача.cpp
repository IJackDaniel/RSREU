// F. Очень Легкая Задача
#include <iostream>

int good(int cnt, int q, int s, int t) {
    return t / q + t / s >= cnt;
}

int main()
{
    int n, quick, slow;
    std::cin >> n >> quick >> slow;
    if (quick > slow) {
        std::swap(quick, slow);
    }
    int l = 0;
    int r = (n - 1) * slow;
    while (r - l > 1) {
        int m = (l + r) / 2;
        if (!good(n - 1, quick, slow, m)) {
            l = m;
        }
        else {
            r = m;
        }
    }
    std::cout << r + quick;
}

