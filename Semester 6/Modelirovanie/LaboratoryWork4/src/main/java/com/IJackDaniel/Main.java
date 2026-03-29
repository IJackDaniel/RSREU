package com.IJackDaniel;

import java.util.Arrays;

public class Main {

    // Параметры лабораторной работы
    private static final int SAMPLE_SIZE = 1000;      // Объем выборки (не менее 1000)
    private static final int INTERVALS = 15;          // Количество интервалов (15 или 25)
    private static final double ALPHA = 0.05;         // Уровень значимости

    // Границы для построения гистограммы (для N(0,1) берем диапазон [-4, 4])
    private static final double LOWER_BOUND = -4.0;
    private static final double UPPER_BOUND = 4.0;

    public static void main(String[] args) {
        System.out.println("Лабораторная работа: Генерирование случайных величин с нормальным законом");
        System.out.println("Параметры: N(0,1), объем выборки n = " + SAMPLE_SIZE);
        System.out.println();

        // ========== Метод 1: Центральная предельная теорема ==========
        System.out.println("МЕТОД 1: Генерация на основе центральной предельной теоремы");
        System.out.println("(суммирование 12 независимых равномерных величин)");
        System.out.println();

        double[] sampleCLT = NormalGenerator.generateSampleCLT(SAMPLE_SIZE);
        processMethod("ЦПТ", sampleCLT, SAMPLE_SIZE, INTERVALS, ALPHA, LOWER_BOUND, UPPER_BOUND);

        // ========== Метод 2: Бокса-Мюллера ==========
        System.out.println();
        System.out.println("МЕТОД 2: Генерация методом Бокса-Мюллера");
        System.out.println("(преобразование двух равномерных величин)");
        System.out.println();

        double[] sampleBoxMuller = NormalGenerator.generateSampleBoxMuller(SAMPLE_SIZE);
        processMethod("Бокса-Мюллера", sampleBoxMuller, SAMPLE_SIZE, INTERVALS, ALPHA, LOWER_BOUND, UPPER_BOUND);

        // Итоговое сравнение методов
        printComparison(sampleCLT, sampleBoxMuller);
    }

    private static void processMethod(String methodName, double[] sample,
                                      int sampleSize, int intervals, double alpha,
                                      double lowerBound, double upperBound) {
        // 1. Вычисление статистических характеристик
        StatisticsCollector stats = new StatisticsCollector();
        for (double value : sample) {
            stats.addValue(value);
        }

        double empiricalMean = stats.getExpectedValue();
        double empiricalVariance = stats.getDispersion();

        System.out.println("Статистические характеристики выборки:");
        System.out.printf("  Эмпирическое математическое ожидание: %.6f (теоретическое: 0.000000)\n", empiricalMean);
        System.out.printf("  Эмпирическая дисперсия: %.6f (теоретическая: 1.000000)\n", empiricalVariance);
        System.out.printf("  Объем выборки: %d\n", sampleSize);
        System.out.println();

        // 2. Проверка распределения (критерии Пирсона и Колмогорова)
        QualityAppraiser appraiser = new QualityAppraiser(
                sample, intervals, alpha, lowerBound, upperBound,
                NormalGenerator.getDistributionFunction()
        );

        appraiser.printResults();

        // 3. Построение гистограммы частот
        System.out.println("Гистограмма частот:");
        System.out.println("(теоретические частоты указаны в скобках)");

        int[] frequencies = appraiser.getFrequencies();
        double[] boundaries = appraiser.getBoundaries();
        double[] theoreticalProbs = appraiser.getTheoreticalProbabilities();

        printDetailedHistogram(frequencies, boundaries, theoreticalProbs, sampleSize);

        System.out.println();
        System.out.println("Графическое представление гистограммы:");
        HistogramPrinter.quickHistogram(frequencies, boundaries);

        // 4. Статистическая функция распределения
        double[] sortedSample = sample.clone();
        Arrays.sort(sortedSample);
        printEmpiricalDistribution(sortedSample, NormalGenerator.getDistributionFunction());

        // 5. Вывод выборки (первые 20 значений)
        System.out.println("Первые 20 значений выборки:");
        System.out.print("  ");
        for (int i = 0; i < Math.min(20, sample.length); i++) {
            System.out.printf("%8.4f ", sample[i]);
            if ((i + 1) % 5 == 0 && i < 19) {
                System.out.print("\n  ");
            }
        }
        System.out.println();
        System.out.println();

        System.out.println("-------------------------------------------------------------------------------");
    }

    private static void printDetailedHistogram(int[] frequencies, double[] boundaries,
                                               double[] theoreticalProbs, int sampleSize) {
        System.out.printf("%-20s %12s %12s %12s %12s\n", "Интервал", "Частота", "Теор. частота", "Разность", "Хи-квадрат");
        System.out.println("--------------------------------------------------------------------------------");

        double chiSquare = 0.0;
        for (int i = 0; i < frequencies.length; i++) {
            double theoreticalFreq = theoreticalProbs[i] * sampleSize;
            double diff = frequencies[i] - theoreticalFreq;
            double chiComponent = (diff * diff) / theoreticalFreq;
            chiSquare += chiComponent;

            String interval = String.format("[%.2f, %.2f]", boundaries[i], boundaries[i + 1]);
            System.out.printf("%-20s %12d %12.2f %12.2f %12.4f\n",
                    interval, frequencies[i], theoreticalFreq, diff, chiComponent);
        }

        double totalTheoretical = 0;
        for (double prob : theoreticalProbs) {
            totalTheoretical += prob * sampleSize;
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-20s %12d %12.2f %12.2f %12.4f\n", "Итого:",
                Arrays.stream(frequencies).sum(), totalTheoretical, 0.0, chiSquare);
        System.out.printf("Наблюдаемое значение хи-квадрат = %.4f\n", chiSquare);
        System.out.println();
    }

    private static void printEmpiricalDistribution(double[] sortedSample,
                                                   java.util.function.Function<Double, Double> distFunc) {
        System.out.println("Статистическая функция распределения:");
        System.out.println("(сравнение эмпирической F*(x) и теоретической F(x))");
        System.out.printf("%-15s %-20s %-20s %-15s\n", "x", "Эмпирическая F*(x)", "Теоретическая F(x)", "|F*(x)-F(x)|");
        System.out.println("-----------------------------------------------------------------------------");

        int n = sortedSample.length;
        double maxDeviation = 0.0;
        int step = Math.max(1, n / 20);

        for (int i = 0; i < n; i += step) {
            double x = sortedSample[i];
            double empiricalCDF = (double) (i + 1) / n;
            double theoreticalCDF = distFunc.apply(x);
            double deviation = Math.abs(empiricalCDF - theoreticalCDF);

            if (deviation > maxDeviation) {
                maxDeviation = deviation;
            }

            System.out.printf("%-15.4f %-20.6f %-20.6f %-15.6f\n",
                    x, empiricalCDF, theoreticalCDF, deviation);
        }

        double lastX = sortedSample[n - 1];
        double lastEmpirical = 1.0;
        double lastTheoretical = distFunc.apply(lastX);
        double lastDeviation = Math.abs(lastEmpirical - lastTheoretical);
        System.out.printf("%-15.4f %-20.6f %-20.6f %-15.6f\n",
                lastX, lastEmpirical, lastTheoretical, lastDeviation);

        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("Максимальное отклонение D = %.6f\n", maxDeviation);

        double criticalValue = 1.36 / Math.sqrt(n);
        System.out.printf("Критическое значение D(alpha=0.05, n=%d) = %.6f\n", n, criticalValue);

        if (maxDeviation < criticalValue) {
            System.out.println("Результат: гипотеза принимается (отклонение не превышает критическое значение)");
        } else {
            System.out.println("Результат: гипотеза отвергается (отклонение превышает критическое значение)");
        }
        System.out.println();
    }

    private static void printComparison(double[] sampleCLT, double[] sampleBoxMuller) {
        System.out.println();
        System.out.println("СРАВНИТЕЛЬНЫЙ АНАЛИЗ МЕТОДОВ ГЕНЕРАЦИИ");
        System.out.println();

        StatisticsCollector statsCLT = new StatisticsCollector();
        StatisticsCollector statsBM = new StatisticsCollector();

        for (double v : sampleCLT) statsCLT.addValue(v);
        for (double v : sampleBoxMuller) statsBM.addValue(v);

        System.out.printf("%-30s %20s %20s\n", "Показатель", "Метод ЦПТ", "Метод Бокса-Мюллера");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-30s %20.6f %20.6f\n", "Математическое ожидание:",
                statsCLT.getExpectedValue(), statsBM.getExpectedValue());
        System.out.printf("%-30s %20.6f %20.6f\n", "Дисперсия:",
                statsCLT.getDispersion(), statsBM.getDispersion());
        System.out.printf("%-30s %20.6f %20.6f\n", "Отклонение M(X) от 0:",
                Math.abs(statsCLT.getExpectedValue()), Math.abs(statsBM.getExpectedValue()));
        System.out.printf("%-30s %20.6f %20.6f\n", "Отклонение D(X) от 1:",
                Math.abs(statsCLT.getDispersion() - 1.0), Math.abs(statsBM.getDispersion() - 1.0));

        System.out.println();
        System.out.println("Замер времени генерации (на 100000 элементов):");

        int testSize = 100000;
        long startCLT = System.nanoTime();
        NormalGenerator.generateSampleCLT(testSize);
        long timeCLT = System.nanoTime() - startCLT;

        long startBM = System.nanoTime();
        NormalGenerator.generateSampleBoxMuller(testSize);
        long timeBM = System.nanoTime() - startBM;

        System.out.printf("%-30s %20.2f %20.2f\n", "Время генерации (мс):",
                timeCLT / 1_000_000.0, timeBM / 1_000_000.0);

        System.out.println();
        System.out.println("Выводы:");
        System.out.println("  1. Метод ЦПТ проще в реализации, но дает приближенное распределение.");
        System.out.println("  2. Метод Бокса-Мюллера теоретически более точен, но требует вычисления");
        System.out.println("     трансцендентных функций (логарифм, косинус).");

        if (Math.abs(statsCLT.getExpectedValue()) > Math.abs(statsBM.getExpectedValue())) {
            System.out.println("  3. По математическому ожиданию точнее оказался метод Бокса-Мюллера.");
        } else {
            System.out.println("  3. По математическому ожиданию точнее оказался метод ЦПТ.");
        }

        if (Math.abs(statsCLT.getDispersion() - 1.0) > Math.abs(statsBM.getDispersion() - 1.0)) {
            System.out.println("  4. По дисперсии точнее оказался метод Бокса-Мюллера.");
        } else {
            System.out.println("  4. По дисперсии точнее оказался метод ЦПТ.");
        }

        if (timeCLT < timeBM) {
            System.out.println("  5. Метод ЦПТ работает быстрее.");
        } else {
            System.out.println("  5. Метод Бокса-Мюллера работает быстрее.");
        }
    }
}