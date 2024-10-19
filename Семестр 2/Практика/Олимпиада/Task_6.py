file = open("input.txt", "r")

mx = 0
mx_st = ""
res_a = []
for s in file:
    s = s.replace("\n", "")
    cnt_num = 0
    cnt_alp = 0
    razn = 0
    alp = []
    for sym in s:
        if sym.isalpha():
            cnt_alp += 1
            alp.append(sym)
        elif sym.isdigit():
            cnt_num += 1
    if cnt_num != 0 and cnt_alp != 0:
        razn = abs(cnt_alp - cnt_num)
        if razn > mx:
            mx = razn
            mx_st = s
            res_a = alp

mx_a = 0
result1 = "NO"
for a in sorted(res_a):
    if mx_st.count(a) >= mx_a:
        mx_a = mx_st.count(a)
        result1 = a

result2 = "NO"
mx_n = 0
number = ""
arr = []
flag = False
for n in mx_st:
    if n.isdigit():
        if int(n) % 2 == 0:
            number = number + n
            flag = True
        else:
            if number != "" and number[0] != "0":
                arr.append(int(number))
            number = ""
    else:
        if number != "" and number[0] != "0":
            arr.append(int(number))
        number = ""
if flag:
    result2 = max(arr)
print(result1, result2)
