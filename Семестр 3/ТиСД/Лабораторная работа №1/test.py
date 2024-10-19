arr = [[5, 155], [2, 156], [2, 158], [5, 178], [4, 180], [4, 182], [3, 190], [2, 210]]


n = len(arr)
for i in range(n - 1):
    for j in range(n - i - 1):
        if arr[j][0] > arr[j + 1][0]:
            arr[j], arr[j + 1] = arr[j + 1], arr[j]

print(arr)
