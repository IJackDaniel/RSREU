def funk(start, end):
    if start <= end:
        return
    if start % 2 == 0 and start//2>end:
        print(':2')
        return funk(start//2, end)
    print('-1')
    return funk(start - 1, end)


a = int(input())
b = int(input())
funk(a, b)