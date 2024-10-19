n, k = list(map(int, input().split()))
arr1 = list(map(int, input().split()))
arr2 = list(map(int, input().split()))

for el in arr2:
    flag = True
    l = 0
    r = len(arr1) - 1
    while l <= r and flag:
        m = (r + l) // 2
        if arr1[m] == el:
            print("YES")
            flag = False
        elif arr1[m] < el:
            l = m + 1
        else:
            r = m - 1
    if flag:
        print("NO")
