#include <iostream> 
#include <string> 
#include <vector> 

std::vector<int> manacher(const std::string& s) {
    int n = s.length();
    std::vector<int> p(n);
    int c = 0, r = 0;
    for (int i = 0; i < n; ++i) {
        int mirror = 2 * c - i;

        if (i < r) {
            p[i] = std::min(r - i, p[mirror]);
        }

        int l = i - 1 - p[i], r_new = i + 1 + p[i];
        while (l >= 0 && r_new < n && s[l] == s[r_new]) {
            p[i]++;
            l--;
            r_new++;
        }

        if (i + p[i] > r) {
            c = i;
            r = i + p[i];
        }
    }
    return p;
}

int main() {
    std::string s;
    std::cin >> s;

    std::string t = "#";
    for (char c : s) {
        t += c;
        t += "#";
    }

    std::vector<int> p = manacher(t);

    for (int i = 1; i < p.size(); i += 2) {
        std::cout << p[i] << (i == p.size() - 1 ? "" : " ");
    }

    return 0;
}
