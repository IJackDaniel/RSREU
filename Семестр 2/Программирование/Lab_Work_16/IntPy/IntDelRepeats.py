def delRepeats(num):
    num = str(num)
    nums = []
    for i in num:
        if i not in nums:
            nums.append(i)
    numRes = ""
    for n in nums:
        if n not in numRes:
            numRes += n
    return int(numRes)

