def good(a, b, w, h, d, n):
    a_d = a + 2 * d
    b_d = b + 2 * d
    res1 = (w // a_d) * (h // b_d)
    res2 = (w // b_d) * (h // a_d)
    res = max(res1, res2)
    return res >= n


n, a, b, w, h = map(int, input().split())
l = 0
r = 1
while good(a, b, w, h, r, n):
    r *= 2

while r - l > 1:
    m = (l + r) // 2
    if good(a, b, w, h, m, n):
        l = m
    else:
        r = m
print(l)
