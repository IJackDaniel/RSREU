n = int(input())
num = n
res = ()
i = 2
flag = True
while num != 1:
    while num % i == 0:
        res += (str(i),)
        num //= i
    else:
        i += 1
    if (i > int(num**0.5) + 1) and len(res) == 0:
        flag = False
        break
if flag:
    print(f"{n} = {' * '.join(res)}")
else:
    print(f"{n} = {n}")
