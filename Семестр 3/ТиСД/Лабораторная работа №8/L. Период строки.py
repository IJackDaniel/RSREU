def pref_func(s):
    v = [0] * len(s)
    for i in range(1, len(s)):
        k = v[i - 1]
        while k > 0 and s[k] != s[i]:
            k = v[k - 1]
        if s[k] == s[i]:
            k = k + 1
        v[i] = k
    return v


inp = input()
n = len(inp)
pref = pref_func(inp)
print(*pref)
len_t = n - pref[-1]
if n % len_t == 0:
    print(n // len_t)
else:
    print(1)
