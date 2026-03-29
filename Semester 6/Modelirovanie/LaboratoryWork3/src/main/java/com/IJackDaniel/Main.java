package com.IJackDaniel;

import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        UniversalGenerator.setSeed(new int[]{5, 11, 3});

        // Параметры эксперимента
        int sampleSize = 1000;           // Объём выборки (не менее 1000)
        int intervals = 15;              // Количество интервалов разбиения (15 или 25)
        double alpha = 0.05;             // Уровень значимости
        double lowerBound = 0.0;         // Нижняя граница области определения
        double upperBound = 1.5;         // Верхняя граница области определения

        // Создаём генератор
        RandomVariableGenerator generator = new RandomVariableGenerator();

        // Функция распределения для проверки гипотез
        Function<Double, Double> distFunc = x -> generator.getDistributionFunction(x);

        // Генерация выборки
        System.out.println("Генерация случайных величин");
        System.out.printf("Метод генерации: метод обратных функций\n");
        System.out.printf("Объём выборки: %d\n", sampleSize);
        System.out.printf("Количество интервалов разбиения: %d\n", intervals);

        double[] sample = generator.generateSample(sampleSize);

        // Сбор статистических характеристик
        StatisticsCollector stats = new StatisticsCollector();
        for (double val : sample) {
            stats.addValue(val);
        }

        System.out.println("\nСтатистические характеристики:");
        System.out.printf("- Математическое ожидание: %.5f\n", stats.getExpectedValue());
        System.out.printf("- Дисперсия: %.5f\n", stats.getDispersion());
        System.out.printf("- Среднеквадратическое отклонение: %.5f\n",
                Math.sqrt(stats.getDispersion()));

        // Проверка гипотезы о распределении (критерии Пирсона и Колмогорова)
        QualityAppraiser appraiser = new QualityAppraiser(sample, intervals, alpha, lowerBound, upperBound, distFunc);
        appraiser.printResults();

        // Гистограмма частот
        System.out.println("\nГистограмма частот:");
        int[] frequencies = appraiser.getFrequencies();
        double[] boundaries = appraiser.getBoundaries();

        // Выводим графическую гистограмму
        HistogramPrinter.quickHistogram(frequencies, boundaries);

        // Статистическая функция распределения
        EmpiricalDistributionFunction edf = new EmpiricalDistributionFunction(sample, distFunc);

        // Выводим таблицу сравнения эмпирической и теоретической функций
        edf.printComparisonTable(boundaries);


        // Дополнительная информация о выборке
        System.out.println("\nДополнительная информация о выборке");
        System.out.printf("Минимальное значение: %.5f\n", getMin(sample));
        System.out.printf("Максимальное значение: %.5f\n", getMax(sample));
        System.out.printf("Размах: %.5f\n", getMax(sample) - getMin(sample));
        System.out.printf("Медиана: %.5f\n", getMedian(sample));

        // Проверка попадания в область определения
        int outOfRange = 0;
        for (double val : sample) {
            if (val < lowerBound || val > upperBound) {
                outOfRange++;
            }
        }
        System.out.printf("Значений вне [%.1f, %.1f]: %d\n", lowerBound, upperBound, outOfRange);
    }

    private static double getMin(double[] arr) {
        double min = arr[0];
        for (double val : arr) {
            if (val < min) min = val;
        }
        return min;
    }

    private static double getMax(double[] arr) {
        double max = arr[0];
        for (double val : arr) {
            if (val > max) max = val;
        }
        return max;
    }

    private static double getMedian(double[] arr) {
        double[] sorted = arr.clone();
        java.util.Arrays.sort(sorted);
        int n = sorted.length;
        if (n % 2 == 0) {
            return (sorted[n/2 - 1] + sorted[n/2]) / 2;
        } else {
            return sorted[n/2];
        }
    }
}