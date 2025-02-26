n, m = map(int, input().split())
mt = [list(map(int, input().split())) for _ in range(n)]

dp = [[0] * m for _ in range(n)]

size_sq = 0
coord = (0, 0)

for i in range(n):
    for j in range(m):
        if mt[i][j] == 1:
            if i == 0 or j == 0:
                dp[i][j] = 1
            else:
                dp[i][j] = min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]) + 1

            if dp[i][j] > size_sq:
                size_sq = dp[i][j]
                coord = (i - size_sq + 1, j - size_sq + 1)

print(size_sq)
print(coord[0] + 1, coord[1] + 1)
