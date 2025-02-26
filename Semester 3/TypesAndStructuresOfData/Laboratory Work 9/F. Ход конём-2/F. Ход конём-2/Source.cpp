#include <iostream>
#include <vector>

using namespace std;

void f(int i, int j, vector<vector<int>>& dp) {
    if (i - 2 >= 0 && j - 1 >= 0 && i - 2 < dp.size() && j - 1 < dp[0].size()) {
        dp[i][j] += dp[i - 2][j - 1];
    }
    if (i - 2 >= 0 && j + 1 < dp[0].size() && i - 2 < dp.size() && j + 1 >= 0) {
        dp[i][j] += dp[i - 2][j + 1];
    }
    if (i - 1 >= 0 && j - 2 >= 0 && i - 1 < dp.size() && j - 2 < dp[0].size()) {
        dp[i][j] += dp[i - 1][j - 2];
    }
    if (i + 1 < dp.size() && j - 2 >= 0 && i + 1 >= 0 && j - 2 < dp[0].size()) {
        dp[i][j] += dp[i + 1][j - 2];
    }
}

int main() {
    int n, m;
    cin >> n >> m;

    n += 2;
    m += 2;

    vector<vector<int>> dp(n + 1, vector<int>(m + 1, 0));
    dp[2][2] = 1;

    int i = 2, j = 2;

    while (i != n - 1 || j != m - 1) {
        int x = i, y = j;
        while (x >= 2 && y < m) {
            f(x, y, dp);
            x--;
            y++;
        }
        if (i == n - 1) {
            j++;
        }
        else {
            i++;
        }
    }

    f(n - 1, m - 1, dp);
    cout << dp[n - 1][m - 1] << endl;

    return 0;
}
