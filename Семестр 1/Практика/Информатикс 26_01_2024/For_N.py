from itertools import product


n = int(input())
cnt = 0
for i in product("123456", repeat=6):
    s = int(''.join(i))
    i = [int(el) for el in i]
    if i[0]+i[5] == 7 and i[1]+i[4] == 7 and i[2]+i[3] == 7 and s < n:
        cnt += 1
print(cnt)
