// G. Веревочки
#include <iostream>
#include <vector>

int good(int x, std::vector<int>& a, int k) {
    int cnt = 0;
    for (int j = 0; j < a.size(); j++) {
        cnt = cnt + (a[j] / x);
    }
    return cnt >= k;
}

int main()
{
    int n, k;
    std::cin >> n >> k;

    int q;
    std::vector<int> arr;
    for (int a = 0; a < n; a++) {
        std::cin >> q;
        arr.push_back(q);
    }

    int l = 0, r = 10000000 + 1;
    while (r - l > 1) {
        int m = (r + l) / 2;
        if (good(m, arr, k)) {
            l = m;
        }
        else {
            r = m;
        }
    }
    std::cout << l;
}
