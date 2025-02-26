from math import factorial


n = int(input())
k = int(input())
print(int((factorial(n)) / ((factorial(k)) * (factorial(n-k)))))
