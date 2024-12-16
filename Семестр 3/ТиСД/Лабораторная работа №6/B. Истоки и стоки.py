n = int(input())
matrix = []
for _ in range(n):
    inp = input().split()
    matrix.append(inp)

istocks = []
stocks = []
for i in range(n):
    inp = matrix[i]
    outp = [matrix[j][i] for j in range(n)]
    if "1" in inp and "1" not in outp:
        istocks.append(i)
    elif "1" in outp and "1" not in inp:
        stocks.append(i)
    elif "1" not in inp and "1" not in outp:
        istocks.append(i)
        stocks.append(i)

print(len(istocks))
for elem1 in istocks:
    print(elem1 + 1)

print(len(stocks))
for elem2 in stocks:
    print(elem2 + 1)

"""
5
0 0 0 0 0
0 0 0 0 1
1 1 0 0 0
0 0 0 0 0
0 0 0 0 0
"""