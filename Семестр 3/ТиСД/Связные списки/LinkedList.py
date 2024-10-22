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

    def insert_on_index(self, data, index):  # вставка элемента после элемента с индексом [index]
        if index == 0:
            self.push_front(data)
        elif self.size() >= index:
            cur = self.head
            prev = None
            i = 0
            while i < index:
                prev = cur
                cur = cur.get_next()
                i += 1
            temp = Node(data)
            temp.set_next(cur)
            prev.set_next(temp)

    def pop_front(self):  # удалить последний элемент (первый слева)
        if self.head is None:
            return

        data = self.head.data
        self.head = self.head.next

    def pop_back(self):  # удалить первый элемент (первый справа)
        if not self.is_empty():
            cur = self.head
            prev = None
            while cur.get_next() is not None:
                prev = cur
                cur = cur.get_next()
            if prev:
                prev.set_next(cur.get_next())
            else:
                self.head = cur.get_next()

    def find(self, x):  # найти элемент в списке (Есть или нет)
        current = self.head
        found = False
        while current is not None and not found:
            if current.get_data() == x:
                found = True
            current = current.get_next()
        return found

    def remove_index(self, index):  # удалить элемент с индексом [index]. Индексация начинается с 0
        if self.size() > index:
            cur = self.head
            prev = None
            i = 0
            while i < index:
                prev = cur
                cur = cur.get_next()
                i += 1
            if prev:
                prev.set_next(cur.get_next())
            else:
                self.head = cur.get_next()

    def get_on_index(self, index):  # получить элемент по индексу. Индексация начинается с 0
        if self.size() > index:
            cur = self.head
            i = 0
            while i < index:
                cur = cur.get_next()
                i += 1
            return cur.get_data()

    def is_empty(self):  # проверка списка на отсутствие значений
        return self.head is None

    def size(self):  # вывод размера списка
        current = self.head
        cnt = 0
        while current is not None:
            cnt += 1
            current = current.get_next()
        return cnt

    def reverse(self):  # реверс списка
        prev = None
        cur = self.head
        while cur is not None:
            nxt = cur.get_next()
            cur.set_next(prev)
            prev = cur
            cur = nxt
        self.head = prev

    def swap_first_and_last(self):  # поменять местами первый и последний элемент
        if self.size() >= 2:
            current = self.head
            fst = current
            fst_data = fst.get_data()
            while current.get_next() is not None:
                current = current.get_next()
            lst = current
            lst_data = lst.get_data()

            fst.set_data(lst_data)
            lst.set_data(fst_data)

    def swap_pairs(self):
        if self.size() >= 2:
            cur = self.head
            prev = None
            while cur.get_next() is not None:
                prev = cur
                cur = cur.get_next()

                prev_data = prev.get_data()
                cur_data = cur.get_data()

                prev.set_data(cur_data)
                cur.set_data(prev_data)

                if cur.get_next() is not None:
                    prev = cur
                    cur = cur.get_next()

    def insert_after_every_kth(self, k, data):  # вставить элемент data после каждого k-того элемента
        if k > 0:
            cur = self.head
            prev = None
            i = 0
            while cur is not None:
                if i == k:
                    i = 0
                    temp = Node(data)
                    temp.set_next(cur)
                    prev.set_next(temp)
                else:
                    prev = cur
                    cur = cur.get_next()
                    i += 1

    def insert_before_every_kth(self, k, data):  # вставить элемент data перед каждым k-тым элементом
        if k > 1:
            cur = self.head
            prev = None
            i = 1
            while cur is not None:
                if i == k:
                    i = 0
                    temp = Node(data)
                    temp.set_next(cur)
                    prev.set_next(temp)
                else:
                    prev = cur
                    cur = cur.get_next()
                    i += 1

    def delete_every_kth_node(self, k):  # удалить каждый k-тый элемент
        if k == 1:
            self.head = None
        elif k > 1:
            cur = self.head
            prev = None
            i = 1
            while cur is not None:
                if i == k:
                    prev.set_next(cur.get_next())
                    cur = cur.get_next()
                    i = 1
                else:
                    prev = cur
                    cur = cur.get_next()
                    i += 1

    def remove_all_chet(self):
        cur = self.head
        while not self.is_empty() and cur.get_data() % 2 == 0:
            self.pop_front()
            cur = self.head
        prev = None
        while cur is not None:
            if cur.get_data() % 2 == 0:
                if prev:
                    temp = cur.get_next()
                    prev.set_next(temp)
                    cur = temp
                else:
                    self.head = cur.get_next()
            else:
                prev = cur
                cur = cur.get_next()

    def remove_all_positives(self):  # удалить все положительные числа
        cur = self.head
        while not self.is_empty() and cur.get_data() > 0:
            self.pop_front()
            cur = self.head
        prev = None
        while cur is not None:
            if cur.get_data() > 0:
                if prev:
                    temp = cur.get_next()
                    prev.set_next(temp)
                    cur = temp
                else:
                    self.head = cur.get_next()
            else:
                prev = cur
                cur = cur.get_next()

    def remove_all_negatives(self):  # удалить все отрицательные числа
        cur = self.head
        while not self.is_empty() and cur.get_data() < 0:
            self.pop_front()
            cur = self.head
        prev = None
        while cur is not None:
            if cur.get_data() < 0:
                if prev:
                    temp = cur.get_next()
                    prev.set_next(temp)
                    cur = temp
                else:
                    self.head = cur.get_next()
            else:
                prev = cur
                cur = cur.get_next()

    def sorted_list(self):  # сортировка по возрастанию (выбирается знаком < >)
        current = self.head
        if current is None:
            return
        else:
            while current is not None:
                min_node = current
                temp = current.get_next()
                while temp is not None:
                    if temp.get_data() < min_node.get_data():
                        min_node = temp
                    temp = temp.get_next()
                cd = current.get_data()
                md = min_node.get_data()
                min_node.set_data(cd)
                current.set_data(md)
                current = current.get_next()

    def swap_i_j_index(self, i, j):  # Поменять i-й элемент с j-м
        if i == j:
            return

        if i < 0 or j < 0 or i > self.size() or j > self.size():
            return -1

        current = self.head
        node_i = None
        node_j = None
        counter = 0

        while current is not None:
            if counter == i:
                node_i = current
            if counter == j:
                node_j = current
            current = current.get_next()
            counter += 1

        if node_i and node_j:
            temp_data = node_i.get_data()
            node_i.set_data(node_j.get_data())
            node_j.set_data(temp_data)

    def __str__(self):
        return str(self.head)


linked_list = LinkedList()
linked_list.push_back(1)
linked_list.push_back(5)
linked_list.push_back(3)
print(linked_list)
linked_list.remove_all_chet()
print(linked_list)
