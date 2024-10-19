class Stack:
    def __init__(self):
        self.items = []

    def is_empty(self):
        if not self.items:
            return True
        return False

    def push(self, number):
        self.items.append(number)
        return "ok"

    def pop(self):
        if self.is_empty():
            return "error"
        return self.items.pop()

    def back(self):
        if self.is_empty():
            return "error"
        return self.items[-1]

    def size(self):
        return len(self.items)

    def clear(self):
        self.items = []
        return "ok"


st = Stack()
data = input()
flag = True
for el in data:
    if not flag:
        break
    if el in "(){}[]":
        if el == "(":
            st.push(el)
        elif el == "{":
            st.push(el)
        elif el == "[":
            st.push(el)
        elif el == ")":
            if st.back() == "(":
                st.pop()
            else:
                flag = False
        elif el == "}":
            if st.back() == "{":
                st.pop()
            else:
                flag = False
        elif el == "]":
            if st.back() == "[":
                st.pop()
            else:
                flag = False

if not st.is_empty():
    flag = False

result = "yes" if flag else "no"
print(result)
