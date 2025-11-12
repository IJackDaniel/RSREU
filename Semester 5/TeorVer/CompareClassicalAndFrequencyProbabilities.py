import random
import time

random.seed(time.time())

NUM_EXPERIMENTS = 10

classicalProb = 0.5

results = []
countOfEagls = 0

print("Номер  | частотная вероятность | разница")
for i in range(1, NUM_EXPERIMENTS + 1):
    toss = random.choice(["E", "T"])
    if toss == "E":
        countOfEagls += 1
    results.append(countOfEagls / i);
    print(f"{i:^6d} | {(countOfEagls / i):^21.5f} | {abs(0.5 - (countOfEagls / i)):^7.5f}")