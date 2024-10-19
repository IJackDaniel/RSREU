class Node:
    def __init__(self, data):
        self.data = data
        self.next = None

    def get_data(self):
        return self.data

    def get_next(self):
        return self.next

    def set_data(self, new_data):
        self.data = new_data

    def set_next(self, new_next):
        self.next = new_next

    def __str__(self):
        return f"[{self.data}] -> {self.next}"


class LinkedList:
    def __init__(self):
        self.head = None

    def push_front(self, data):
        temp = Node(data)
        temp.set_next(self.head)
        self.head = temp

    def push_back(self, data):
        if self.is_empty():
            self.push_front(data)
        else:
            temp = Node(data)
            current = self.head
            while current.get_next() != None:
                current = current.get_next()
            current.set_next(temp)

    def find(self, x):
        current = self.head
        found = False
        while current != None and not found:
            if current.get_data() == x:
                found = True
            current = current.get_next()
        return found

    def is_empty(self):
        return self.head == None

    def size(self):
        current = self.head
        cnt = 0
        while current != None:
            cnt += 1
            current = current.get_next()
        return cnt

    def __str__(self):
        return str(self.head)


# 3___________


# 2___________
# mylist = LinkedList()
# mylist.push_front(27)
# print(mylist)
# mylist.push_front(43)
# print(mylist)
# mylist.push_front(19)
# print(mylist)

# 1___________
# node_1 = Node(13)
# node_2 = Node(666)
# node_3 = Node(999)
# node_4 = Node(5555)
#
# node_1.next = node_2
# print(node_1)
# node_2.next = node_3
# print(node_1)
# node_3.next = node_4
# print(node_1)
