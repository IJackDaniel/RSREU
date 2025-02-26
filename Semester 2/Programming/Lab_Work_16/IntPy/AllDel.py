def allDel(num):
    result = set()
    for i in range(1, int(num**0.5)+2):
        if num % i == 0:
            result.add(i)
            result.add(num//i)
    return sorted(result)