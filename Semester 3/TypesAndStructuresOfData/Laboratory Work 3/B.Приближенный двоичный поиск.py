def LeftBinS(arr, x):
    l = -1
    r = len(arr)
    while r - l > 1:
        m = (l + r) // 2
        if arr[m] <= x:
            l = m
        else:
            r = m
    return l


def RightBinS(arr, x):
    l = -1
    r = len(arr)
    while r - l > 1:
        m = (l + r) // 2
        if arr[m] < x:
            l = m
        else:
            r = m
    if r == len(arr):
        return -1
    return r


n, k = list(map(int, input().split()))
arr1 = list(map(int, input().split()))
arr2 = list(map(int, input().split()))

# n, k = 5, 5
# arr1 = [1, 3, 5, 7, 9]
# arr2 = [2, 4, 8, 1, 6]

for el in arr2:
    ind_1 = LeftBinS(arr1, el)
    ind_2 = RightBinS(arr1, el)
    if ind_1 == -1:
        print(arr1[ind_2])
    elif ind_2 == -1:
        print(arr1[ind_1])
    else:
        if el - arr1[ind_1] == arr1[ind_2] - el:
            print(arr1[ind_1])
        elif el - arr1[ind_1] < arr1[ind_2] - el:
            print(arr1[ind_1])
        else:
            print(arr1[ind_2])
