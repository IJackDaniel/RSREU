# # m воздушных шариков, n людишек
# m, n = list(map(int, input().split()))
# workers = []
# for i in range(n):
#     # t минут надувает, z шариков на одном дыхании, y минут отдыхает
#     t, z, y = list(map(int, input().split()))
#     workers.append({"number": i, "time": t, "count": z, "relax": y, "sum": 0, "can": z})
#
#
# print(workers)
# print()
#
# ready = 0
# time = 0
# while ready < m:
#     workers.sort(key=lambda a: (a["can"], a["sum"], a["time"]))
#     ready += 1
#     workers[0]["sum"] += workers[0]["time"]
#     workers[0]["can"] -= 1
#     print(workers)
#     workers.sort(key=lambda a: (a["sum"], a["time"]))
#     print(workers)
#     if workers[0]["can"] == 0 and sum([r for r in [workers[a]["can"] for a in range(n)]]) == 0:
#         if ready < m:
#             workers[0]["sum"] += workers[0]["relax"]
#             workers[0]["can"] = workers[0]["count"]
#     print(workers)
#     print()
#
# arr = [workers[b]["sum"] for b in range(n)]
# print(max(arr))