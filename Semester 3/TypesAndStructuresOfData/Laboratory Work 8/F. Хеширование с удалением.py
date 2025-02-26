class HashSet:
    def __init__(self):
        self.size = 2 ** 20
        self.table = [[] for _ in range(self.size)]

    def make_hash(self, s):
        hash_value = 0
        for char in s:
            hash_value += (hash_value * 31 + ord(char)) % self.size
        return hash_value % self.size

    def add_elem(self, s):
        index = self.make_hash(s)
        bucket = self.table[index]
        if s not in bucket:
            bucket.append(s)

    def delete_elem(self, s):
        index = self.make_hash(s)
        bucket = self.table[index]
        if s in bucket:
            bucket.remove(s)

    def check_elem(self, s):
        index = self.make_hash(s)
        bucket = self.table[index]
        return "YES" if s in bucket else "NO"


data = HashSet()
while True:
    line = input()
    if line == '#':
        break
    op, s = line.split()
    if op == '+':
        data.add_elem(s)
    elif op == '-':
        data.delete_elem(s)
    elif op == '?':
        print(data.check_elem(s))
