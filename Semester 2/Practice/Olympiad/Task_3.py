def anogramma(w1, w2):
    w1 = w1.lower()
    w2 = w2.lower()
    if set(w1) == set(w2):
        for q in set(w1):
            if w1.count(q) != w2.count(q):
                return False
        return True
    return False


sentence = input().replace(".", "").replace(",", "").replace("!", "").replace("?", "").replace(":", "").replace(". ", "")
arr = sentence.split()
flag = True
for i in range(len(arr)-1):
    for j in range(i+1, len(arr)):
        if anogramma(arr[i], arr[j]) and flag and arr[i] != arr[j]:
            print(arr[i], arr[j])
            flag = False
