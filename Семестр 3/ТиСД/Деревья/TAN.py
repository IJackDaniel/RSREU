# print("-----Практика 1-----")
# n, m = map(int, input().split())
#
# a = [[0 for _ in range(n)] for _ in range(n)]
#
# for _ in range(m):
#     edge = list(map(int, input().split()))
#     a[edge[0] - 1][edge[1] - 1] = 1
#     a[edge[1] - 1][edge[0] - 1] = 1
#
# for r in a:
#     print("  ".join(map(str, r)))
#
# # Ввод:
# # 5 3
# # 1 3
# # 2 3
# # 5 2

# print("-----Практика 2-----")
# n, m = map(int, input().split())
#
# a = [[0 for _ in range(n)] for _ in range(n)]
#
# for _ in range(m):
#     edge = list(map(int, input().split()))
#     a[edge[0] - 1][edge[1] - 1] = 1
#
# for r in a:
#     print("  ".join(map(str, r)))
