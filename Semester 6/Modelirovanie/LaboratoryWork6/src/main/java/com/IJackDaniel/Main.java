package com.IJackDaniel;

import com.IJackDaniel.basicClasses.UniversalGenerator;


public class Main {

    // Параметры задачи
    private static final int K = 4;   // количество баков (k>2)

    // Вероятности попадания в баки (должны суммироваться в 1)
    private static final double[] P = {0.1, 0.2, 0.3, 0.4};  // для k=4

    private static final int N_TRIALS = 100000;         // количество экспериментов Монте-Карло
    private static final double BETA = 0.95;            // надёжность доверительного интервала

    public static void main(String[] args) {
        UniversalGenerator.setSeed(new int[]{42, 12345, 98765});

        System.out.printf("Количество баков k = %d\n", K);
        System.out.print("Вероятности попадания: ");
        for (int i = 0; i < K; i++) {
            System.out.printf("p%d = %.2f  ", i+1, P[i]);
        }
        System.out.println();
        System.out.printf("Количество экспериментов: %d\n", N_TRIALS);
        System.out.printf("Надёжность β = %.2f\n", BETA);
        System.out.println("Условие воспламенения: два попадания в ОДИН бак ИЛИ в СОСЕДНИЕ баки");
        System.out.println();

        // Метод Монте-Карло
        System.out.println(" 1. Моделирование методом Монте-Карло");

        ShootingSimulation simulation = new ShootingSimulation(K, P);
        MonteCarloEstimator estimator = new MonteCarloEstimator(N_TRIALS, simulation, BETA);
        estimator.run();

        System.out.printf("Количество воспламенений: %d из %d\n",
                estimator.getIgnitionCount(), estimator.getNTrials());
        System.out.printf("Оценка вероятности воспламенения = %.6f\n", estimator.getProbability());
        System.out.println();
        System.out.printf("Доверительный интервал (β = %.2f):\n", BETA);
        System.out.printf("[%.6f ; %.6f]\n",
                estimator.getConfidenceLower(), estimator.getConfidenceUpper());
        System.out.println();

        // Аналитическое решение
        System.out.println(" 2. Аналитическая проверка");

        double analyticProb = calculateAnalyticProbability(K, P);
        System.out.printf("Аналитическая вероятность воспламенения: P = %.6f\n", analyticProb);

        // Проверка: попадает ли аналитическое значение в доверительный интервал
        boolean inside = (analyticProb >= estimator.getConfidenceLower() &&
                analyticProb <= estimator.getConfidenceUpper());
        System.out.printf("\nАналитическое значение %s В доверительный интервал.\n",
                inside ? "Попадает" : "Не попадает");

        // Сравнение погрешности
        double error = Math.abs(estimator.getProbability() - analyticProb);
        System.out.printf("Абсолютная погрешность оценки: %.6f\n", error);
        System.out.printf("Относительная погрешность: %.4f%%\n", (error / analyticProb) * 100);
    }

    private static double calculateAnalyticProbability(int k, double[] p) {
        double prob = 0.0;

        // Случай 1: оба попадания в один и тот же бак i
        for (int i = 0; i < k; i++) {
            prob += p[i] * p[i];
        }

        // Случай 2: попадания в соседние баки (i, i+1) и (i+1, i)
        for (int i = 0; i < k - 1; i++) {
            prob += 2.0 * p[i] * p[i+1];
        }

        return prob;
    }
}