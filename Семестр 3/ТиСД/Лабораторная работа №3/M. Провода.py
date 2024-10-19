def good(ropes, x, k):
    c = 0
    for rope in ropes:
        c = c + rope // x
    return c >= k


n, k = map(int, input().split())
ropes = []
for _ in range(n):
    ropes.append(int(input()))

l = 0
r = 1e10
while r - l > 1:
    m = (r + l) // 2
    if good(ropes, m, k):
        l = m
    else:
        r = m
print(int(l))


