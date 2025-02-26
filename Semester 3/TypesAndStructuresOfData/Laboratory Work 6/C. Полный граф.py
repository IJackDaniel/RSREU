n, m = map(int, input().split())

network = set()
for _ in range(m):
    inp = sorted(list(map(int, input().split())))
    network.add(f"{inp[0]} {inp[1]}")

need = sum([a for a in range(n-1, 0, -1)])
if len(network) == need:
    print("YES")
else:
    print("NO")
