def backpack(v, c, W):
    dp = [[0] * (W + 1) for i in range(n + 1)]
    for i in range(1, n + 1):
        for w in range(W + 1):
            if w < v[i]:
                dp[i][w] = dp[i - 1][w]
            else:
                dp[i][w] = max(dp[i - 1][w], dp[i - 1][w - v[i]] + c[i])
    result = []
    i, w = n, W
    while i > 0 and w > 0:
        if dp[i][w] > dp[i - 1][w]:
            result.append(i)
            w -= v[i]
        i -= 1

    return result[::-1]


n, capacity = map(int, input().split())
weight = [0] + list(map(int, input().split()))
cost = [0] + list(map(int, input().split()))
print(*backpack(weight, cost, capacity), sep='\n')
