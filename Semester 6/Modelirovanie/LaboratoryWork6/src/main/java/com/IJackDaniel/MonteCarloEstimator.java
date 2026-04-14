// файл: MonteCarloEstimator.java
package com.IJackDaniel;

/**
 * Оценка вероятности методом Монте-Карло с построением доверительного интервала.
 */
public class MonteCarloEstimator {
    private final int nTrials;           // количество экспериментов
    private final ShootingSimulation simulation;
    private final double beta;            // надёжность (0.95)

    // Результаты после run()
    private double probability;           // оценка вероятности
    private double confidenceLower;       // нижняя граница доверительного интервала
    private double confidenceUpper;       // верхняя граница доверительного интервала
    private int ignitionCount;            // количество воспламенений

    /**
     * Конструктор
     * @param nTrials количество экспериментов
     * @param simulation модель стрельбы
     * @param beta надёжность (например, 0.95)
     */
    public MonteCarloEstimator(int nTrials, ShootingSimulation simulation, double beta) {
        this.nTrials = nTrials;
        this.simulation = simulation;
        this.beta = beta;
    }

    /**
     * Запуск моделирования
     */
    public void run() {
        ignitionCount = 0;
        for (int i = 0; i < nTrials; i++) {
            if (simulation.runExperiment()) {
                ignitionCount++;
            }
        }

        probability = (double) ignitionCount / nTrials;

        // Доверительный интервал для биномиальной пропорции (нормальное приближение)
        double z = getZForBeta(beta);
        double stdError = Math.sqrt(probability * (1.0 - probability) / nTrials);
        double margin = z * stdError;

        confidenceLower = probability - margin;
        confidenceUpper = probability + margin;

        // Корректировка границ на отрезок [0,1]
        confidenceLower = Math.max(0.0, confidenceLower);
        confidenceUpper = Math.min(1.0, confidenceUpper);
    }

    /**
     * Возвращает квантиль стандартного нормального распределения для заданной надёжности
     */
    private double getZForBeta(double beta) {
        // Для наиболее распространённых значений β
        if (Math.abs(beta - 0.90) < 0.001) {
            return 1.645;
        } else if (Math.abs(beta - 0.95) < 0.001) {
            return 1.96;
        } else if (Math.abs(beta - 0.99) < 0.001) {
            return 2.576;
        }
        // По умолчанию для β=0.95
        return 1.96;
    }

    // Геттеры
    public double getProbability() { return probability; }
    public double getConfidenceLower() { return confidenceLower; }
    public double getConfidenceUpper() { return confidenceUpper; }
    public int getIgnitionCount() { return ignitionCount; }
    public int getNTrials() { return nTrials; }
}