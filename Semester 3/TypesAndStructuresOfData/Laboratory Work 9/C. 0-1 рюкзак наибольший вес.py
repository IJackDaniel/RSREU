n, m = map(int, input().split())
gold = map(int, input().split())

dp = [0] * 10000
dp[0] = 1

for i in gold:
    for j in range(m, -1, -1):
        if dp[j] == 1:
            dp[j + i] = 1

for i in range(m, -1, -1):
    if dp[i] == 1 and i <= m:
        print(i)
        break
