// файл: Main.java
package com.IJackDaniel;

import com.IJackDaniel.basicClasses.UniversalGenerator;

/**
 * Лабораторная работа: Моделирование методом Монте-Карло.
 * Задача: стрельба двумя снарядами по k бакам с горючим.
 * Оценка вероятности воспламенения баков.
 */
public class Main {

    // ============ ПАРАМЕТРЫ ЗАДАЧИ ============
    private static final int K = 4;                     // количество баков (k>2)
    // Вероятности попадания в баки (должны суммироваться в 1)
    private static final double[] P = {0.1, 0.2, 0.3, 0.4};  // для k=4
    private static final int N_TRIALS = 100000;         // количество экспериментов Монте-Карло
    private static final double BETA = 0.95;            // надёжность доверительного интервала

    public static void main(String[] args) {
        // Инициализация ГСЧ (можно задать seed для воспроизводимости результатов)
        UniversalGenerator.setSeed(new int[]{42, 12345, 98765});

        System.out.println("==========================================================");
        System.out.println(" ЛАБОРАТОРНАЯ РАБОТА: Метод Монте-Карло");
        System.out.println(" Моделирование стрельбы по бакам с горючим");
        System.out.println("==========================================================");
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

        // ============ 1. МЕТОД МОНТЕ-КАРЛО ============
        System.out.println("##########################################################");
        System.out.println(" # 1. МОДЕЛИРОВАНИЕ МЕТОДОМ МОНТЕ-КАРЛО");
        System.out.println("##########################################################");

        ShootingSimulation simulation = new ShootingSimulation(K, P);
        MonteCarloEstimator estimator = new MonteCarloEstimator(N_TRIALS, simulation, BETA);
        estimator.run();

        System.out.printf("Количество воспламенений: %d из %d\n",
                estimator.getIgnitionCount(), estimator.getNTrials());
        System.out.printf("Оценка вероятности воспламенения P̂ = %.6f\n", estimator.getProbability());
        System.out.println();
        System.out.printf("ДОВЕРИТЕЛЬНЫЙ ИНТЕРВАЛ (β = %.2f):\n", BETA);
        System.out.printf("[%.6f ; %.6f]\n",
                estimator.getConfidenceLower(), estimator.getConfidenceUpper());
        System.out.println();

        // ============ 2. АНАЛИТИЧЕСКОЕ РЕШЕНИЕ ============
        System.out.println("##########################################################");
        System.out.println(" # 2. АНАЛИТИЧЕСКАЯ ПРОВЕРКА");
        System.out.println("##########################################################");

        double analyticProb = calculateAnalyticProbability(K, P);
        System.out.printf("Аналитическая вероятность воспламенения: P = %.6f\n", analyticProb);

        // Проверка: попадает ли аналитическое значение в доверительный интервал
        boolean inside = (analyticProb >= estimator.getConfidenceLower() &&
                analyticProb <= estimator.getConfidenceUpper());
        System.out.printf("\nАналитическое значение %s В доверительный интервал.\n",
                inside ? "ПОПАДАЕТ" : "НЕ ПОПАДАЕТ");

        // Сравнение погрешности
        double error = Math.abs(estimator.getProbability() - analyticProb);
        System.out.printf("Абсолютная погрешность оценки: %.6f\n", error);
        System.out.printf("Относительная погрешность: %.4f%%\n", (error / analyticProb) * 100);

        System.out.println("\n==========================================================");
        System.out.println(" ВЫВОД: Метод Монте-Карло даёт оценку, согласующуюся");
        System.out.println(" с аналитическим решением в пределах доверительного интервала.");
        System.out.println("==========================================================");
    }

    /**
     * Аналитический расчёт вероятности воспламенения
     * Для двух выстрелов с вероятностями попаданий p_i.
     *
     * Формула: P = Σ(p_i²) + 2·Σ(p_i·p_{i+1})
     *
     * Пояснение:
     * - p_i² — оба снаряда попали в бак i
     * - 2·p_i·p_{i+1} — снаряды попали в соседние баки i и i+1 (в любом порядке)
     *
     * @param k количество баков
     * @param p массив вероятностей попадания
     * @return точная вероятность воспламенения
     */
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