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


string = input()
find = input()
result = pref_func(find + "#" + string)
ind = []
for i, val in enumerate(result[len(find):]):
    if val == len(find):
        ind.append(i - len(find))
print(*ind)
