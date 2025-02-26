import heapq

n = int(input())
aquariums = []
for _ in range(n):
    aquariums.append(int(input()))

heap = []
for i in range(n):
    new_time = max(1000 - aquariums[i], 1)
    heapq.heappush(heap, (new_time, i, aquariums[i]))

cur_time = 0
cur_aquarium = 0
while heap:
    next_birth, aquarium_num, cur_fish_cnt = heapq.heappop(heap)
    reach_time = cur_time + abs(aquarium_num - cur_aquarium)
    if next_birth < reach_time:
        print(next_birth)
        break
    cur_time = next_birth
    cur_aquarium = aquarium_num

    new_fish_count = cur_fish_cnt + 1
    new_birth_time = cur_time + max(1000 - new_fish_count, 1)
    heapq.heappush(heap, (new_birth_time, aquarium_num, new_fish_count))
