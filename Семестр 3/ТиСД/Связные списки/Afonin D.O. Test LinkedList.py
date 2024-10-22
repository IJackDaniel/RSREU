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

    def is_empty(self):  # проверка списка на отсутствие значений
        return self.head is None

    def size(self):  # вывод размера списка
        current = self.head
        cnt = 0
        while current is not None:
            cnt += 1
            current = current.get_next()
        return cnt

    def push_front(self, data):  # добавить в конец (слева)
        temp = Node(data)
        temp.set_next(self.head)
        self.head = temp

    def push_back(self, data):  # добавить в начало (справа перед None)
        if self.is_empty():
            self.push_front(data)
        else:
            temp = Node(data)
            current = self.head
            while current.get_next() is not None:
                current = current.get_next()
            current.set_next(temp)

    def pop_front(self):  # удалить последний элемент (первый слева)
        if self.head is None:
            return

        data = self.head.data
        self.head = self.head.next

    def remove_all_chet(self):  # Задание 1. Удалить из списка все чётные элементы
        current = self.head
        while not self.is_empty() and current.get_data() % 2 == 0:
            self.pop_front()
            current = self.head
        prev = None
        while current is not None:
            if current.get_data() % 2 == 0:
                if prev:
                    temp = current.get_next()
                    prev.set_next(temp)
                    current = temp
                else:
                    self.head = current.get_next()
            else:
                prev = current
                current = current.get_next()

    def insert_before_every_kth(self, k, key):  # Задание 2. Вставить элемент
        # с ключом key перед
        # каждым k-ом элементом
        # исходного массива
        if k > 1:
            current = self.head
            prev = None
            i = 1
            while current is not None:
                if i == k:
                    i = 0
                    temp = Node(key)
                    temp.set_next(current)
                    prev.set_next(temp)
                else:
                    prev = current
                    current = current.get_next()
                    i += 1
        if k == 1:
            current = self.head
            prev = None
            i = 0
            while current is not None:
                if i == k:
                    i = 0
                    temp = Node(key)
                    temp.set_next(current)
                    prev.set_next(temp)
                else:
                    prev = current
                    current = current.get_next()
                    i += 1
            self.push_front(key)

    def reverse(self):  # Задание 3. Реверсировать список
        prev = None
        current = self.head
        while current is not None:
            next = current.get_next()
            current.set_next(prev)
            prev = current
            current = next
        self.head = prev

    def __str__(self):
        return str(self.head)


linked_list = LinkedList()
linked_list.push_back(0)
linked_list.push_back(2)
linked_list.push_back(3)
linked_list.push_back(4)
linked_list.push_back(5)
linked_list.push_back(6)
linked_list.push_back(7)
linked_list.push_back(8)
linked_list.push_back(9)
print(f"Было : {linked_list}")
linked_list.remove_all_chet()
print(f"Стало: {linked_list}")
