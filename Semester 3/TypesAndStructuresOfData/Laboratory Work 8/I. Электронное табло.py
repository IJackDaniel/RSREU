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


string1 = input()
string2 = input()

string = string1 + "#" + string2
pref = pref_func(string)

print(len(string2) - pref[-1])
