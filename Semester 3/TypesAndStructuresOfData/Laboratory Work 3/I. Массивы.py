def quick_sort(arr):

    if len(arr) <= 1:
        return arr

    ln = len(arr) // 2
    elem = arr[ln]

    left = [i for i in arr if i < elem]
    center = [i for i in arr if i == elem]
    right = [i for i in arr if i > elem]

    return quick_sort(left) + center + quick_sort(right)


n = int(input())
arrn = list(map(int, input().split()))
m = int(input())
arrm = list(map(int, input().split()))

# n = 3
# arrn = [1, 2, 1]
# m = 4
# arrm = [0, 1, 2, 3]

arrn = quick_sort(arrn)

# print(arrn)

result = []
for el in arrm:
    ind = []

    l = -1
    r = len(arrn)
    while r - l > 1:
        x = (r + l) // 2
        if arrn[x] <= el:
            l = x
        else:
            r = x
    if l == -1 or arrn[l] != el:
        ind.append(-1)
    else:
        ind.append(l + 1)

    l = -1
    r = len(arrn)
    while r - l > 1:
        x = (r + l) // 2
        if arrn[x] < el:
            l = x
        else:
            r = x
    if r == len(arrn) or arrn[r] != el:
        ind.append(-1)
    else:
        ind.append(r)

    result.append(str(ind[0] - ind[-1]))
    # print(f"Последний {el} = {ind[0]}, первый {ind[-1]}")

print(" ".join(result))
