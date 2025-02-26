from sys import stdin


class Node:
    def __init__(self, key):
        self.data = key
        self.left = None
        self.right = None


class BinarySearchTree:
    def __init__(self):
        self.root = None

    def insert(self, key):
        self.root = self._insert(self.root, key)

    def _insert(self, node, key):
        if node is None:
            return Node(key)
        if key < node.data:
            node.left = self._insert(node.left, key)
        elif key > node.data:
            node.right = self._insert(node.right, key)
        return node

    def search(self, key):
        return self._search(self.root, key)

    def _search(self, node, key):
        if node is None:
            return False
        if node.data == key:
            return True
        if key < node.data:
            return self._search(node.left, key)
        return self._search(node.right, key)

    def find_max(self):
        if self.root is None:
            return f"{str(None)}"
        return self._max_node(self.root).data

    def _max_node(self, node):
        current = node
        while current.right is not None:
            current = current.right
        return current

    def find_min(self):
        if self.root is None:
            return f"{str(None)}"
        return self._min_node(self.root).data

    def _min_node(self, node):
        current = node
        while current.left is not None:
            current = current.left
        return current

    def delete(self, key):
        self.root = self._delete(self.root, key)

    def _delete(self, node, key):
        if node is None:
            return node
        if key < node.data:
            node.left = self._delete(node.left, key)
        elif key > node.data:
            node.right = self._delete(node.right, key)
        else:
            if node.left is None:
                return node.right
            elif node.right is None:
                return node.left
            max_node = self._max_node(node.left)
            node.data = max_node.data
            node.left = self._delete(node.left, max_node.data)
        return node

    def print_tree(self, node, level):
        if node is None:
            return None
        self.print_tree(node.left, level + 1)
        for _ in range(level):
            print(".", end="")
        print(node.data)
        self.print_tree(node.right, level + 1)


b_tree = BinarySearchTree()
strings = stdin.readlines()
for inp in strings:
    inp = inp.split()
    command = inp[0]
    if command == "ADD":
        num = int(inp[-1])
        if b_tree.search(num):
            print("ALREADY")
        else:
            b_tree.insert(num)
            print("DONE")
    elif command == "DELETE":
        num = int(inp[-1])
        if b_tree.search(num):
            b_tree.delete(num)
            print("DONE")
        else:
            print("CANNOT")
    elif command == "SEARCH":
        num = int(inp[-1])
        result = "YES" if b_tree.search(num) else "NO"
        print(result)
    elif command == "PRINTTREE":
        b_tree.print_tree(b_tree.root, 0)
