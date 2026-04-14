package com.IJackDaniel;

import com.IJackDaniel.basicClasses.HistogramPrinter;
import com.IJackDaniel.basicClasses.QualityAppraiser;
import com.IJackDaniel.basicClasses.StatisticsCollector;
import com.IJackDaniel.basicClasses.UniversalGenerator;

import java.util.Arrays;
import java.util.function.Function;

public class Main {

    // Константы задачи
    private static final int SAMPLE_SIZE = 10000;   // объём выборки
    private static final int INTERVALS = 15;        // k = 15
    private static final double ALPHA = 0.05;       // уровень значимости

    // Параметры распределений
    // Гамма-распределение
    private static final double GAMMA_SHAPE = 2.0;
    private static final double GAMMA_SCALE = 2.0;

    // Логнормальное распределение
    private static final double LOGNORMAL_MU = 0.5;
    private static final double LOGNORMAL_SIGMA = 0.5;

    public static void main(String[] args) {
        // Инициализируем базовый ГСЧ (можно задать seed)
        UniversalGenerator.setSeed(new int[]{12345, 67890, 13579});

        System.out.printf("Объём выборки: %d\n", SAMPLE_SIZE);
        System.out.printf("Количество интервалов разбиения: %d\n", INTERVALS);
        System.out.printf("Уровень значимости α: %.2f\n", ALPHA);
        System.out.println();

        // Гамма распределение
        System.out.println(" 1. Гамма-распределение:");
        System.out.printf("Параметры: форма k = %.2f, масштаб θ = %.2f\n", GAMMA_SHAPE, GAMMA_SCALE);
        System.out.println("Теоретическое матожидание: " + (GAMMA_SHAPE * GAMMA_SCALE));
        System.out.println("Теоретическая дисперсия:   " + (GAMMA_SHAPE * GAMMA_SCALE * GAMMA_SCALE));
        System.out.println();

        analyzeDistribution("Гамма",
                new GammaGenerator(GAMMA_SHAPE, GAMMA_SCALE)::generateArray,
                new GammaGenerator(GAMMA_SHAPE, GAMMA_SCALE)::cdf
        );

        // Логнормальное распределение
        System.out.println("\n\n 2. Логнормальное распределение:");
        System.out.printf("Параметры: μ = %.2f, σ = %.2f\n", LOGNORMAL_MU, LOGNORMAL_SIGMA);
        double theoreticalMean = Math.exp(LOGNORMAL_MU + LOGNORMAL_SIGMA * LOGNORMAL_SIGMA / 2.0);
        double theoreticalVar = (Math.exp(LOGNORMAL_SIGMA * LOGNORMAL_SIGMA) - 1.0) *
                Math.exp(2.0 * LOGNORMAL_MU + LOGNORMAL_SIGMA * LOGNORMAL_SIGMA);
        System.out.println("Теоретическое матожидание: " + theoreticalMean);
        System.out.println("Теоретическая дисперсия:   " + theoreticalVar);
        System.out.println();

        analyzeDistribution("Логнормальное",
                new LogNormalGenerator(LOGNORMAL_MU, LOGNORMAL_SIGMA)::generateArray,
                new LogNormalGenerator(LOGNORMAL_MU, LOGNORMAL_SIGMA)::cdf
        );
    }

    private static void analyzeDistribution(String name,
                                            java.util.function.IntFunction<double[]> generator,
                                            Function<Double, Double> cdf) {

        // 1. Генерируем выборку
        double[] sample = generator.apply(SAMPLE_SIZE);

        // 2. Собираем статистику
        StatisticsCollector stats = new StatisticsCollector();
        for (double v : sample) stats.addValue(v);

        System.out.println("--- Эмпирические оценки ---");
        System.out.printf("Матожидание: %.6f\n", stats.getExpectedValue());
        System.out.printf("Дисперсия:   %.6f\n", stats.getDispersion());

        // 3. Определяем границы для разбиения
        double min = Arrays.stream(sample).min().orElse(0.0);
        double max = Arrays.stream(sample).max().orElse(1.0);
        // Немного расширяем границы для лучшего отображения
        double padding = (max - min) * 0.01;
        double lower = Math.max(0.0, min - padding);
        double upper = max + padding;

        System.out.printf("Границы разбиения: [%.4f, %.4f]\n", lower, upper);

        // 4. Создаём оценщик качества (считает частоты, критерии)
        QualityAppraiser appraiser = new QualityAppraiser(sample, INTERVALS, ALPHA, lower, upper, cdf);

        // 5. Выводим таблицу с гистограммой
        System.out.println("\n--- Гистограмма частот ---");
        HistogramPrinter.quickHistogram(appraiser.getFrequencies(), appraiser.getBoundaries());

        // 6. Выводим детальную таблицу частот и вероятностей
        System.out.println("\n--- Таблица интервалов ---");
        System.out.printf("%-20s %10s %12s %15s\n", "Интервал", "Частота", "Частость", "Теор. вероятн.");
        System.out.println("-".repeat(65));

        int[] freqs = appraiser.getFrequencies();
        double[] bounds = appraiser.getBoundaries();
        double[] teorProbs = appraiser.getTheoreticalProbabilities();

        for (int i = 0; i < INTERVALS; i++) {
            String interval = String.format("[%7.4f, %7.4f]", bounds[i], bounds[i+1]);
            double freqRel = (double) freqs[i] / SAMPLE_SIZE;
            System.out.printf("%-20s %10d %12.5f %15.5f\n",
                    interval, freqs[i], freqRel, teorProbs[i]);
        }

        // 7. Печатаем результаты проверки гипотез
        appraiser.printResults();
    }
}