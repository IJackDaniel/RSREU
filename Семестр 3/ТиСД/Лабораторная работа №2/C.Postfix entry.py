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
inp = input()
for el in inp.split():
    if el in "0123456789":
        st.push(el)
    elif el in "+-*":
        b = st.pop()
        a = st.pop()
        st.push(eval(f"{a} {el} {b}"))
print(st.back())

