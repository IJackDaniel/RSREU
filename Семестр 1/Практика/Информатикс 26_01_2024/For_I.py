n = int(input())
arr = []
for i in range(n):
    arr.append(int(input()))
lst1 = [el1 for el1 in arr if el1 > 0]
lst2 = [el2 for el2 in arr if el2 < 0]
print(arr.count(0), len(lst1), len(lst2))
