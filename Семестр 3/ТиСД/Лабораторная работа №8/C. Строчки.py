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


kirill = input()
dima = input()

if kirill == dima:
    print(0)
    exit()

if len(kirill) != len(dima):
    print(-1)
    exit()

result = pref_func(dima + "#" + kirill + kirill)
for i, val in enumerate(result[len(dima) + 1:]):
    if val == len(dima):
        print(2 * len(dima) - i - 1)
        exit()

print(-1)
