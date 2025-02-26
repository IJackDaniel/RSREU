data = [int(i) for i in input().split()]
n = data[0]
arr = data[1:]
print(sum([i for i in range(1, n+1)]) - sum(arr))
