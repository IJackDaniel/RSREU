#include <iostream> 
#include <vector> 

int main()
{
    int n;
    std::cin >> n;
    std::vector<int> dp(n), cost(n);
    for (int i = 0; i < n; i++)
        std::cin >> cost[i];

    dp[0] = cost[0];
    dp[1] = cost[1];

    for (int i = 2; i < n; i++)
        dp[i] = std::min(dp[i - 1], dp[i - 2]) + cost[i];

    std::cout << dp[n - 1] << std::endl;
    return 0;
}
