n, m = map(int, input().split())
price = [list(map(int, input().split())) for _ in range(n)]

dp = [[0] * m for _ in range(n)]
dp[0][0] = price[0][0]
for i in range(1, n):
    dp[i][0] = dp[i - 1][0] + price[i][0]
for j in range(1, m):
    dp[0][j] = dp[0][j - 1] + price[0][j]
for a in range(1, n):
    for b in range(1, m):
        dp[a][b] = max(dp[a - 1][b], dp[a][b - 1]) + price[a][b]
print(dp[-1][-1])

i, j = n - 1, m - 1
path = []
while i > 0 or j > 0:
    if i != 0 and (j == 0 or dp[i - 1][j] > dp[i][j - 1]):
        path.append('D')
        i -= 1
    else:
        path.append('R')
        j -= 1

print(' '.join(path[::-1]))
