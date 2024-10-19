def substractor(num1, num2):
    nums = str(num2)
    result = ""
    for i in str(num1):
        if i not in nums:
            result += i
    return int(result)
