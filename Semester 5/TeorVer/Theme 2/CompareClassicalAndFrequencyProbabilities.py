import random
import time
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

random.seed(time.time())

NUM_EXPERIMENTS = 10000

classicalProb = 0.5

results = []
countOfEagls = 0

for i in range(1, NUM_EXPERIMENTS + 1):
    toss = random.choice(["E", "T"])
    if toss == "E":
        countOfEagls += 1
    results.append(countOfEagls / i)

countOfRows = 10
indices = np.linspace(1, NUM_EXPERIMENTS, countOfRows, dtype=int)

experiments = indices
freqProbabilities = [results[i - 1] for i in indices]

data = {
    "Опытов": experiments,
    "Частотная вероятность": [round(p, 4) for p in freqProbabilities],
    "Классическая вероятность": [classicalProb] * len(experiments),
    "Разница": [round(abs(p - classicalProb), 4) for p in freqProbabilities]
}

df = pd.DataFrame(data)
print(df.to_string(index=False))

plt.figure(figsize=(8, 5))
plt.plot(range(1, NUM_EXPERIMENTS + 1), results, label="Частотная вероятность")
plt.axhline(y=classicalProb, color="red", linestyle="--", label="Классическая вероятность")
plt.title("Сходимость частотной вероятности к классической\n(пример с подбрасыванием монеты)")
plt.xlabel("Количество испытаний")
plt.ylabel("Вероятность орла")
plt.legend()
plt.grid(True)
plt.show()
