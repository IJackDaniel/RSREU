package com.IJackDaniel;

import com.IJackDaniel.basicClasses.*;

import java.util.Arrays;
import java.util.function.Function;

public class Main {

    // Параметры модели
    private static final int M = 10;                 // Количество шагов
    private static final int N_PRELIMINARY = 1000;   // Объем пробного эксперимента

    // Параметры планирования
    private static final double EPSILON_MEAN = 0.05;     // Точность оценки среднего
    private static final double EPSILON_VARIANCE = 0.01; // Точность оценки дисперсии
    private static final double BETA = 0.95;             // Достоверность (надежность)
    private static final double ALPHA = 1 - BETA;        // Уровень значимости

    public static void main(String[] args) {
        System.out.println("=== Лабораторная работа №8: Тактическое планирование эксперимента ===");
        System.out.println("Модель: Двумерное случайное блуждание, M = " + M + " шагов");
        System.out.println("Исследуемый параметр: Координата X (смещение по оси X)");
        System.out.println("Заданная точность для среднего epsilon = " + EPSILON_MEAN);
        System.out.println("Заданная точность для дисперсии epsilon = " + EPSILON_VARIANCE);
        System.out.println("Заданная достоверность beta = " + BETA);

        // --------------------------------------------------------------------
        // ЧАСТЬ 1. ПРОБНЫЙ ЭКСПЕРИМЕНТ И АНАЛИЗ НОРМАЛЬНОСТИ
        // --------------------------------------------------------------------
        System.out.println("\n--- Часть 1. Пробный эксперимент (N = " + N_PRELIMINARY + ") ---");

        double[] preliminaryData = new double[N_PRELIMINARY];
        StatisticsCollector prelimStats = new StatisticsCollector();

        for (int i = 0; i < N_PRELIMINARY; i++) {
            double x = performRandomWalkX(M); // Берем только координату X
            preliminaryData[i] = x;
            prelimStats.addValue(x);
        }

        double sampleMean = prelimStats.getExpectedValue();
        double sampleVar = prelimStats.getDispersion();
        double sampleStd = Math.sqrt(sampleVar);

        System.out.printf("Выборочное среднее X: %.5f\n", sampleMean);
        System.out.printf("Выборочная дисперсия X: %.5f\n", sampleVar);
        System.out.printf("Выборочное СКО X: %.5f\n", sampleStd);

        // Проверка нормальности распределения координаты X
        System.out.println("\n--- Проверка нормальности распределения координаты X ---");
        double minX = Arrays.stream(preliminaryData).min().orElse(-M);
        double maxX = Arrays.stream(preliminaryData).max().orElse(M);
        double boundMargin = 0.1 * (maxX - minX);

        Function<Double, Double> normalCDF = x -> {
            double z = (x - sampleMean) / sampleStd;
            return normalCDF_Std(z);
        };

        QualityAppraiser appraiser = new QualityAppraiser(
                preliminaryData,
                10, // Число интервалов для проверки
                ALPHA,
                minX - boundMargin,
                maxX + boundMargin,
                normalCDF
        );

        double pierson = appraiser.getPierson();
        double critical = appraiser.getCriticalValuePierson();
        boolean isNormal = pierson < critical;

        System.out.printf("Критерий Пирсона: %.4f (критическое: %.4f)\n", pierson, critical);
        if (isNormal) {
            System.out.println("Результат: Распределение X можно считать нормальным.");
            System.out.println("Для планирования будут использованы формулы с квантилями нормального распределения.");
        } else {
            System.out.println("Результат: Распределение X отличается от нормального.");
            System.out.println("Для планирования следовало бы использовать неравенство Чебышева, но в рамках работы применим нормальную аппроксимацию.");
        }

        // --------------------------------------------------------------------
        // ЧАСТЬ 2. ПЛАНИРОВАНИЕ ОБЪЕМА ВЫБОРКИ
        // --------------------------------------------------------------------
        System.out.println("\n--- Часть 2. Расчет необходимого объема выборки ---");

        double tBeta = ExperimentPlanner.getQuantile(BETA);
        System.out.printf("Квантиль нормального распределения для beta=%.2f: t_beta = %.4f\n", BETA, tBeta);

        int N_mean = ExperimentPlanner.calculateNForMean(tBeta, sampleStd, EPSILON_MEAN);
        int N_var = ExperimentPlanner.calculateNForVariance(tBeta, sampleVar, EPSILON_VARIANCE);

        System.out.printf("Необходимый объем выборки для оценки среднего: N_mean = %d\n", N_mean);
        System.out.printf("Необходимый объем выборки для оценки дисперсии: N_var  = %d\n", N_var);

        int N_total = Math.max(N_mean, N_var);
        System.out.printf("Итоговый объем основного эксперимента: N = %d\n", N_total);

        // --------------------------------------------------------------------
        // ЧАСТЬ 3. ОСНОВНОЙ ЭКСПЕРИМЕНТ
        // --------------------------------------------------------------------
        System.out.println("\n--- Часть 3. Основной эксперимент (N = " + N_total + ") ---");

        double[] mainData = new double[N_total];
        StatisticsCollector mainStats = new StatisticsCollector();

        for (int i = 0; i < N_total; i++) {
            double x = performRandomWalkX(M);
            mainData[i] = x;
            mainStats.addValue(x);
        }

        double finalMean = mainStats.getExpectedValue();
        double finalVar = mainStats.getDispersion();
        double finalStd = Math.sqrt(finalVar);

        // Расчет доверительных интервалов
        double deltaMean = tBeta * finalStd / Math.sqrt(N_total);
        double deltaVar = tBeta * finalVar * Math.sqrt(2.0 / (N_total - 1)); // Приближенная формула

        System.out.println("--- Результаты основного эксперимента ---");
        System.out.printf("Оценка среднего X: %.5f ± %.5f (доверительный интервал)\n", finalMean, deltaMean);
        System.out.printf("Оценка дисперсии X: %.5f ± %.5f (доверительный интервал)\n", finalVar, deltaVar);

        System.out.println("\n--- Проверка достигнутой точности ---");
        System.out.printf("Точность среднего: %.5f (требовалось ≤ %.5f) - %s\n",
                deltaMean, EPSILON_MEAN, deltaMean <= EPSILON_MEAN ? "ДОСТИГНУТА" : "НЕ ДОСТИГНУТА");
        System.out.printf("Точность дисперсии: %.5f (требовалось ≤ %.5f) - %s\n",
                deltaVar, EPSILON_VARIANCE, deltaVar <= EPSILON_VARIANCE ? "ДОСТИГНУТА" : "НЕ ДОСТИГНУТА");

        System.out.println("\n=== Работа завершена ===");
    }

    /**
     * Моделирует двумерное блуждание и возвращает итоговую координату X.
     */
    private static double performRandomWalkX(int steps) {
        int x = 0;
        for (int i = 0; i < steps; i++) {
            double r = UniversalGenerator.rnd();
            if (r < 0.25) { /* y++ */ }
            else if (r < 0.50) x++;
            else if (r < 0.75) { /* y-- */ }
            else x--;
        }
        return x;
    }

    /**
     * Функция стандартного нормального распределения.
     */
    private static double normalCDF_Std(double x) {
        if (x < -8.0) return 0.0;
        if (x > 8.0) return 1.0;
        double sum = 0.0;
        double term = x;
        for (int i = 3; sum + term != sum; i += 2) {
            sum = sum + term;
            term = term * x * x / i;
        }
        return 0.5 + sum * Math.exp(-(x * x) / 2) / Math.sqrt(2 * Math.PI);
    }
}