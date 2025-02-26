def z_func(s):
    z = [0] * len(s)
    z[0] = len(s)
    left, right = 0, 0
    for i in range(1, len(s)):
        k = max(0, min(z[i - left], right - i))
        while i + k < len(s) and s[k] == s[i + k]:
            k += 1
        z[i] = k
        if i + k > right:
            left, right = i, i + k
    return z


string = input()
result = z_func(string)
print(*result)
