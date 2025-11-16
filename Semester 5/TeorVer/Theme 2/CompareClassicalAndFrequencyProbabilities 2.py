import random
import time
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

random.seed(time.time())

NUM_EXPERIMENTS = 10000

sumCounts = {
    2: 1,
    3: 2,
    4: 3,
    5: 4,
    6: 5,
    7: 6,
    8: 5,
    9: 4,
    10: 3,
    11: 2,
    12: 1
}
totalChallenges = 6 ** 2
classicalProb = {s : count / totalChallenges for s, count in sumCounts.items()}

freqCounts = {s: 0 for s in range(2, 13)}
freqHistory = {s: [] for s in range(2, 13)}

for i in range(1, NUM_EXPERIMENTS + 1):
    dice1 = random.randint(1, 6)
    dice2 = random.randint(1, 6)
    sum = dice1 + dice2

    freqCounts[sum] += 1

    for val in range(2, 13):
        freqHistory[val].append((freqCounts[val] / i))

countOfRows = 10
indices = np.linspace(1, NUM_EXPERIMENTS, countOfRows, dtype=int)

dataRows = []
for index in indices:
    row = {"Испытаний": index}
    for s in range(2, 13):
        row[f"P(Σ={s})"] = round(freqHistory[s][index - 1], 4)
    dataRows.append(row)

df = pd.DataFrame(dataRows)
print("\nСравнение частотных вероятностей по мере увеличения количества испытаний")
print(df.to_string(index=False))

finalFreqProb = {s: freqHistory[s][-1] for s in range(2, 13)}
summary = pd.DataFrame({
    "Сумма": list(range(2, 13)),
    "Классическая вероятность": [round(classicalProb[s], 4) for s in range(2, 13)],
    "Частотная вероятность (последняя)": [round(finalFreqProb[s], 4) for s in range(2, 13)],
    "Разница": [round(abs(finalFreqProb[s] - classicalProb[s]), 4) for s in range(2, 13)]
})

print("\n Сравнение частотной вероятности и классической")
print(summary.to_string(index=False))

plt.figure(figsize=(8, 4))
s = 7  # Выбор интересующей суммы
plt.plot(range(1, NUM_EXPERIMENTS + 1), freqHistory[s], label=f"Частотная P(Σ={s})")
plt.axhline(y=classicalProb[s], color="red", linestyle="--", label=f"Классическая P(Σ={s}) = {classicalProb[s]:.3f}")
plt.title("Сходимость частотной вероятности к классической (Σ=7)")
plt.xlabel("Количество испытаний")
plt.ylabel("Вероятность")
plt.legend()
plt.grid(True)
plt.show()
