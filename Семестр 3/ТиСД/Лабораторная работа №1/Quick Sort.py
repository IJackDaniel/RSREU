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
arr = [int(i) for i in input().split()]

arr = quick_sort(arr)
arr = [str(i) for i in arr]
arr = " ".join(arr)
print(arr)
