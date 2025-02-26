def rev(num):
    num = str(num)
    if len(num) == 1:
        res = num
    else:
        res = int(num[-1] + num[1:-2] + num[0])
    return res
