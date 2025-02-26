def merge_sort(arr):
    if len(arr) <= 1:
        return arr
    ind = len(arr) // 2
    left = merge_sort(arr[:ind])
    right = merge_sort(arr[ind:])
    return merge(left, right)


def merge(left, right):
    res = []
    i, j = 0, 0
    while i < len(left) and j < len(right):
        if left[i] <= right[j]:
            res.append(left[i])
            i += 1
        else:
            res.append(right[j])
            j += 1
    res += left[i:] + right[j:]
    return res


n = int(input())
arr = [i for i in map(int, input().split())]

arr = merge_sort(arr)
arr = [str(i) for i in arr]
arr = " ".join(arr)
print(arr)
