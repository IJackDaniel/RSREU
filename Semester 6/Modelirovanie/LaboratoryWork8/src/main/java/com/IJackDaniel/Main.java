package com.IJackDaniel;

import com.IJackDaniel.basicClasses.*;

import java.util.Arrays;
import java.util.function.Function;

public class Main {

    // Параметры модели
    private static final int M = 10;                 // Количество шагов
    private static final int N_PRELIMINARY = 20;     // Объем пробного эксперимента

    // Параметры планирования
    private static final double EPSILON_MEAN = 0.05;     // Точность оценки среднего
    private static final double EPSILON_VARIANCE = 0.05; // Точность оценки дисперсии
    private static final double BETA = 0.95;             // Достоверность (надежность)
    private static final double ALPHA = 1 - BETA;        // Уровень значимости

    public static void main(String[] args) {
        System.out.println("Модель: Двумерное случайное блуждание, M = " + M + " шагов");
        System.out.println("Исследуемый параметр: Координата X (смещение по оси X)");
        System.out.println("Заданная точность для среднего epsilon = " + EPSILON_MEAN);
        System.out.println("Заданная точность для дисперсии epsilon = " + EPSILON_VARIANCE);
        System.out.println("Заданная достоверность beta = " + BETA);

        // Часть 1. Пробный эксперимент и анализ нормальности
        System.out.println("\nЧасть 1. Пробный эксперимент (N = " + N_PRELIMINARY + ")");

        double[] preliminaryData = new double[N_PRELIMINARY];
        StatisticsCollector prelimStats = new StatisticsCollector();

        for (int i = 0; i < N_PRELIMINARY; i++) {
            double x = performRandomWalkX(M);
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
        System.out.println("\nПроверка нормальности распределения координаты X");
        double minX = Arrays.stream(preliminaryData).min().orElse(-M);
        double maxX = Arrays.stream(preliminaryData).max().orElse(M);
        double boundMargin = 0.1 * (maxX - minX);

        Function<Double, Double> normalCDF = x -> {
            double z = (x - sampleMean) / sampleStd;
            return normalCDF_Std(z);
        };

        QualityAppraiser appraiser = new QualityAppraiser(
                preliminaryData,
                10,
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
        } else {
            System.out.println("Результат: Распределение X отличается от нормального.");
        }

        // Часть 2. Планирование объёма выборки
        System.out.println("\nЧасть 2. Расчет необходимого объема выборки");

        int N_mean, N_var;

        if (isNormal) {
            System.out.println("Распределение признано нормальным. Используем нормальную аппроксимацию.");
            double tBeta = ExperimentPlanner.getQuantile(BETA);
            System.out.printf("Квантиль нормального распределения t_beta = %.4f\n", tBeta);
            N_mean = ExperimentPlanner.calculateNForMeanNormal(tBeta, sampleStd, EPSILON_MEAN);
            // Для дисперсии даже при нормальности часто используют Чебышёва (более осторожный подход)
            N_var = ExperimentPlanner.calculateNForVarianceChebyshev(ALPHA, sampleVar, EPSILON_VARIANCE);
        } else {
            System.out.println("Распределение не является нормальным. Используем неравенство Чебышёва.");
            System.out.printf("Уровень значимости alpha = %.2f\n", ALPHA);
            N_mean = ExperimentPlanner.calculateNForMeanChebyshev(ALPHA, sampleStd, EPSILON_MEAN);
            N_var = ExperimentPlanner.calculateNForVarianceChebyshev(ALPHA, sampleVar, EPSILON_VARIANCE);
        }

        System.out.printf("Необходимый объем выборки для оценки среднего: N_mean = %d\n", N_mean);
        System.out.printf("Необходимый объем выборки для оценки дисперсии: N_var  = %d\n", N_var);

        int N_total = Math.max(N_mean, N_var);
        System.out.printf("Итоговый объем основного эксперимента: N = %d\n", N_total);

        // Часть 3. Основной эксперимент
        System.out.println("\nЧасть 3. Основной эксперимент (N = " + N_total + ")");

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

        // Расчет доверительных интервалов (через неравенство Чебышёва)
        double deltaMean = finalStd / Math.sqrt(ALPHA * N_total);
        double deltaVar = finalVar * Math.sqrt(2.0 / (ALPHA * (N_total - 1)));

        System.out.println("Результаты основного эксперимента");
        System.out.printf("Оценка среднего X: %.5f +- %.5f (доверительный интервал, beta=%.2f)\n",
                finalMean, deltaMean, BETA);
        System.out.printf("Оценка дисперсии X: %.5f +- %.5f (доверительный интервал, beta=%.2f)\n",
                finalVar, deltaVar, BETA);

        System.out.println("\nПроверка достигнутой точности");
        System.out.printf("Точность среднего: %.5f (требовалось <= %.5f) - %s\n",
                deltaMean, EPSILON_MEAN, deltaMean <= EPSILON_MEAN ? "ДОСТИГНУТА" : "НЕ ДОСТИГНУТА");
        System.out.printf("Точность дисперсии: %.5f (требовалось <= %.5f) - %s\n",
                deltaVar, EPSILON_VARIANCE, deltaVar <= EPSILON_VARIANCE ? "ДОСТИГНУТА" : "НЕ ДОСТИГНУТА");
    }

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