def prime_power(n):
    pow = [1] * (n + 1)
    for i in range(1, n + 1):
        pow[i] = (pow[i - 1] * PRIME) % MOD
    return pow


def hash_func(s):
    hashes = [0] * (len(s) + 1)
    for i in range(len(s)):
        hashes[i + 1] = (hashes[i] * PRIME + (ord(s[i]) - ord('a') + 1)) % MOD
    return hashes


def get_hash(hashes, l, r):
    return (hashes[r] - hashes[l - 1] * pow[r - l + 1]) % MOD


PRIME, MOD = 31, 10**9 + 7

string = input()
find = input()

hashes = hash_func(string)
pow = prime_power(len(hashes) - 1)

find_hash = hash_func(find)[-1]

for i in range(len(string) - len(find) + 2):
    if get_hash(hashes, i, i + len(find) - 1) == find_hash:
        print(i - 1, end=" ")
