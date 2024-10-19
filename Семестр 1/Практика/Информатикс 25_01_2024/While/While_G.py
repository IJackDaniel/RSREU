i = 1
lst = []
while i != 0:
    i = int(input())
    lst.append(i)

mx1 = max(lst)
lst = [el for el in lst if el != mx1]
print(max(lst))