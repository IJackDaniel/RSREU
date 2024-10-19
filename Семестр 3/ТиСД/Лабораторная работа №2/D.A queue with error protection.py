class Queue:
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
        return self.items.pop(0)

    def front(self):
        if self.is_empty():
            return "error"
        return self.items[0]

    def size(self):
        return len(self.items)

    def clear(self):
        self.items = []
        return "ok"


st = Queue()
while True:
    command = input()
    if command == "exit":
        print("bye")
        break
    elif "push" in command:
        n = command.split()[-1]
        print(st.push(n))
    elif command == "pop":
        print(st.pop())
    elif command == "front":
        print(st.front())
    elif command == "size":
        print(st.size())
    elif command == "clear":
        print(st.clear())