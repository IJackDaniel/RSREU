a = float(input())
b = float(input())
c = float(input())

d = float(input())
e = float(input())

if ((a <= d and b <= e) or (b <= d and a <= e)) and a*b <= d*e:
    print("YES")
elif ((a <= d and c <= e) or (c <= d and a <= e)) and a*c <= d*e:
    print("YES")
elif ((c <= d and b <= e) or (b <= d and c <= e)) and c*b <= d*e:
    print("YES")
else:
    print("NO")