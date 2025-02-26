def is_prime(n):
    if n == 1:
        return False
    for r in range(2, int(n**0.5)+1):
        if n % r == 0:
            return False
    return True


def findel(n):
    result = set()
    for i in range(1, int(n**0.5)+1):
        if n % i == 0:
            result.add(i)
            result.add(n//i)
    return result

n = int(input())
i = 2
arr = sorted(findel(n))
for el in arr:
    if is_prime(el):
        print(el)
        exit()