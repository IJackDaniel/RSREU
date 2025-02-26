def manacher(text):
    n = len(text)
    count = 0
    c = 0
    r = 0
    p = [0] * n

    for i in range(n):
        mirror = 2 * c - i
        if i < r:
            p[i] = min(r - i, p[mirror])

        while i + p[i] + 1 < n and i - p[i] - 1 >= 0 and text[i + p[i] + 1] == text[i - p[i] - 1]:
            p[i] += 1

        if i + p[i] > r:
            c = i
            r = i + p[i]

        count += (p[i] + 1) // 2

    return count


text = input()

text2 = '#' + '#'.join(text) + '#'
print(manacher(text2))
