package com.IJackDaniel;

import com.IJackDaniel.basicClasses.*;

import java.util.Arrays;
import java.util.function.Function;

public class Main {

    private static final int M = 10;
    private static final int N_EXPERIMENTS = 100000;
    private static final int HISTOGRAM_BINS = 15;
    private static final double ALPHA = 0.05;

    public static void main(String[] args) {
        System.out.println("=== Лабораторная работа: Моделирование случайных блужданий ===");
        System.out.println("Тип: Двумерное случайное блуждание");
        System.out.println("Параметры: M = " + M + " шагов, N = " + N_EXPERIMENTS + " экспериментов");

        // 1. Генерация выборки
        double[] distances = new double[N_EXPERIMENTS];
        StatisticsCollector stats = new StatisticsCollector();
        double sumSquares = 0.0;

        for (int i = 0; i < N_EXPERIMENTS; i++) {
            double dist = performRandomWalk(M);
            distances[i] = dist;
            stats.addValue(dist);
            sumSquares += dist * dist;
        }

        // 2. Вывод статистик
        System.out.println("\n--- Статистические характеристики выборки ---");
        double sampleMean = stats.getExpectedValue();
        double sampleVar = stats.getDispersion();
        double sampleStd = Math.sqrt(sampleVar);

        System.out.printf("Выборочное среднее: %.5f\n", sampleMean);
        System.out.printf("Выборочная дисперсия: %.5f\n", sampleVar);
        System.out.printf("Среднеквадратическое отклонение: %.5f\n", sampleStd);
        System.out.printf("Размах выборки: [%.3f, %.3f]\n",
                Arrays.stream(distances).min().orElse(0),
                Arrays.stream(distances).max().orElse(0));

        // 3. Построение гистограммы
        System.out.println("\n--- Гистограмма распределения расстояния ---");

        double minBound = 0.0;
        double maxBound = M * 1.2;

        double[] boundaries = new double[HISTOGRAM_BINS + 1];
        double step = (maxBound - minBound) / HISTOGRAM_BINS;
        for (int i = 0; i <= HISTOGRAM_BINS; i++) {
            boundaries[i] = minBound + i * step;
        }

        int[] frequencies = new int[HISTOGRAM_BINS];
        for (double val : distances) {
            int bin = (int) ((val - minBound) / step);
            if (bin >= HISTOGRAM_BINS) bin = HISTOGRAM_BINS - 1;
            if (bin >= 0) {
                frequencies[bin]++;
            }
        }

        HistogramPrinter.printHistogram(frequencies, boundaries,
                new HistogramPrinter.DefaultIntervalLabelGenerator(4));

        // 4. Анализ целесообразности аппроксимации
        System.out.println("\n--- Анализ возможности аппроксимации известными законами ---");
        System.out.println("Уровень значимости alpha = " + ALPHA);

        // Нормальное распределение
        System.out.println("\n[1] Проверка нормального распределения");
        Function<Double, Double> normalCDF = x -> {
            double z = (x - sampleMean) / sampleStd;
            return normalCDF_Std(z);
        };
        checkHypothesis(distances, boundaries, normalCDF, "Нормальный");

        // Распределение Рэлея
        System.out.println("\n[2] Проверка распределения Рэлея");
        double sigmaRayleigh = Math.sqrt(sumSquares / (2.0 * N_EXPERIMENTS));
        System.out.printf("Оценка параметра sigma: %.5f\n", sigmaRayleigh);

        Function<Double, Double> rayleighCDF = x -> {
            if (x < 0) return 0.0;
            return 1 - Math.exp(- (x * x) / (2 * sigmaRayleigh * sigmaRayleigh));
        };
        checkHypothesis(distances, boundaries, rayleighCDF, "Рэлея");

        // Экспоненциальное распределение
        System.out.println("\n[3] Проверка экспоненциального распределения");
        double lambda = 1.0 / sampleMean;
        System.out.printf("Оценка параметра lambda: %.5f\n", lambda);

        Function<Double, Double> expCDF = x -> {
            if (x < 0) return 0.0;
            return 1 - Math.exp(-lambda * x);
        };
        checkHypothesis(distances, boundaries, expCDF, "Экспоненциальный");
    }

    private static void checkHypothesis(double[] data, double[] bounds,
                                        Function<Double, Double> cdf,
                                        String lawName) {
        double min = bounds[0];
        double max = bounds[bounds.length - 1];
        double lowerBound = min - 0.1 * (max - min);
        double upperBound = max + 0.1 * (max - min);

        QualityAppraiser appraiser = new QualityAppraiser(
                data,
                HISTOGRAM_BINS,
                ALPHA,
                lowerBound,
                upperBound,
                cdf
        );

        double pierson = appraiser.getPierson();
        double critical = appraiser.getCriticalValuePierson();

        System.out.printf("Наблюдаемое значение критерия Пирсона: %.4f\n", pierson);
        System.out.printf("Критическое значение (alpha=%.2f, r=%d): %.4f\n",
                ALPHA, HISTOGRAM_BINS - 1, critical);

        if (pierson < critical) {
            System.out.printf("Результат: Гипотеза о соответствии закону %s ПРИНИМАЕТСЯ.\n", lawName);
        } else {
            System.out.printf("Результат: Гипотеза о соответствии закону %s ОТВЕРГАЕТСЯ.\n", lawName);
        }
    }

    private static double performRandomWalk(int steps) {
        int x = 0, y = 0;
        for (int i = 0; i < steps; i++) {
            double r = UniversalGenerator.rnd();
            if (r < 0.25) y++;
            else if (r < 0.50) x++;
            else if (r < 0.75) y--;
            else x--;
        }
        return Math.sqrt(x * x + y * y);
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