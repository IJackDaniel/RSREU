class Deque:
    def __init__(self):
        self.items = []

    def is_empty(self):
        if not self.items:
            return True
        return False

    def push_front(self, number):
        self.items = [number] + self.items
        return "ok"

    def push_back(self, number):
        self.items.append(number)
        return "ok"

    def pop_front(self):
        if self.is_empty():
            return "error"
        return self.items.pop(0)

    def pop_back(self):
        if self.is_empty():
            return "error"
        return self.items.pop()

    def back(self):
        if self.is_empty():
            return "error"
        return self.items[-1]

    def front(self):
        if self.is_empty():
            return "error"
        return self.items[0]

    def size(self):
        return len(self.items)

    def clear(self):
        self.items = []
        return "ok"


st = Deque()
while True:
    command = input()
    if command == "exit":
        print("bye")
        break
    elif "push_front" in command:
        n = command.split()[-1]
        print(st.push_front(n))
    elif "push_back" in command:
        n = command.split()[-1]
        print(st.push_back(n))
    elif command == "pop_back":
        print(st.pop_back())
    elif command == "pop_front":
        print(st.pop_front())
    elif command == "back":
        print(st.back())
    elif command == "front":
        print(st.front())
    elif command == "size":
        print(st.size())
    elif command == "clear":
        print(st.clear())