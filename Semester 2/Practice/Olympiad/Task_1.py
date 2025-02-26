num = input()
num_l = map(int, list(num))
s_ch = 0
s_nc = 0
flag = True  # нечётное
for n in num_l:
    if flag:
        s_nc += n
        flag = False
    else:
        s_ch += n
        flag = True
res = s_nc + 3 * s_ch
if res % 10 == 0:
    print("YES")
else:
    print("NO")
