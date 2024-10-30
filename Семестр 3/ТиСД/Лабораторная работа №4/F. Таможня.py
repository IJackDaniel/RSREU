import heapq


n = int(input())
arriv_durat = []
for _ in range(n):
    arriv_durat.append(list(map(int, input().split())))

arriv_durat.sort(key=lambda elem: elem[0])
apparatuses = []
mix_cnt_apparatuses = 0

for arrival, duration in arriv_durat:
    while apparatuses and apparatuses[0] <= arrival:
        heapq.heappop(apparatuses)
    heapq.heappush(apparatuses, arrival + duration)
    mix_cnt_apparatuses = max(mix_cnt_apparatuses, len(apparatuses))

print(mix_cnt_apparatuses)
