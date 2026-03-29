package com.IJackDaniel;

import java.util.Arrays;
import java.util.function.Function;


public class QualityAppraiser {
    // Основные параметры
    private final int length;                    // Объём выборки
    private final int countOfParts;              // Количество интервалов разбиения (k)
    private final double[] boundaries;           // Границы интервалов разбиения
    private final double[] theoreticalProbabilities;  // Теоретические вероятности попадания в интервалы
    private final int[] frequencies;             // Эмпирические частоты попадания в интервалы

    // Параметры для критерия Пирсона
    private final double alpha;                  // Уровень значимости
    private final int r;                         // Число степеней свободы (k - 1)
    private final double criticalValuePierson;   // Критическое значение критерия Пирсона

    // Значения критериев
    private double pierson;                      // Наблюдаемое значение критерия Пирсона
    private double kolmogorov;                   // Наблюдаемое значение критерия Колмогорова

    // Функция теоретического распределения
    private final Function<Double, Double> distributionFunction;

    // Параметры вывода
    private static final int ACCURACY = 5;       // Точность округления

    public QualityAppraiser(double[] values, int parts, double alpha,
                            double lowerBound, double upperBound,
                            Function<Double, Double> distFunc) {
        this.length = values.length;
        this.countOfParts = parts;
        this.distributionFunction = distFunc;
        this.alpha = alpha;

        // Вычисляем границы интервалов разбиения
        this.boundaries = computeBoundaries(lowerBound, upperBound, parts);

        // Вычисляем теоретические вероятности попадания в интервалы
        this.theoreticalProbabilities = computeTheoreticalProbabilities();

        // Вычисляем эмпирические частоты попадания в интервалы
        this.frequencies = generateFrequencies(values);

        // Параметры для критерия Пирсона
        this.r = parts - 1;                      // Число степеней свободы
        this.criticalValuePierson = PiersonCriticalValues.getPiersonCriticalValue(this.r, alpha);

        // Вычисляем значения критериев
        computePierson();
        computeKolmogorov(values);
    }

    private double[] computeBoundaries(double lowerBound, double upperBound, int parts) {
        double[] boundaries = new double[parts + 1];
        double step = (upperBound - lowerBound) / parts;

        for (int i = 0; i <= parts; i++) {
            boundaries[i] = lowerBound + i * step;
        }

        return boundaries;
    }

    private double[] computeTheoreticalProbabilities() {
        double[] probs = new double[countOfParts];

        for (int i = 0; i < countOfParts; i++) {
            double fUpper = distributionFunction.apply(boundaries[i + 1]);
            double fLower = distributionFunction.apply(boundaries[i]);
            probs[i] = fUpper - fLower;

            // Проверка на корректность вероятности
            if (probs[i] < 0) {
                System.err.printf("Внимание: отрицательная вероятность в интервале %d: %.5f\n", i, probs[i]);
                probs[i] = 0;
            }
        }

        // Нормализация вероятностей (для устранения погрешностей округления)
        double sum = 0;
        for (double prob : probs) {
            sum += prob;
        }

        if (Math.abs(sum - 1.0) > 0.001) {
            // Корректируем последнюю вероятность для получения суммы = 1
            probs[countOfParts - 1] += (1.0 - sum);
        }

        return probs;
    }

    private int[] generateFrequencies(double[] values) {
        int[] freqs = new int[countOfParts];

        for (double val : values) {
            // Находим интервал, в который попадает значение
            for (int i = 0; i < countOfParts; i++) {
                // Для последнего интервала включаем правую границу
                if (i == countOfParts - 1) {
                    if (val >= boundaries[i] && val <= boundaries[i + 1]) {
                        freqs[i]++;
                        break;
                    }
                } else {
                    if (val >= boundaries[i] && val < boundaries[i + 1]) {
                        freqs[i]++;
                        break;
                    }
                }
            }
        }

        return freqs;
    }

    private void computePierson() {
        double xi = 0.0;

        for (int i = 0; i < countOfParts; i++) {
            double theoreticalFrequency = length * theoreticalProbabilities[i];

            if (theoreticalFrequency > 0) {
                double diff = frequencies[i] - theoreticalFrequency;
                xi += (diff * diff) / theoreticalFrequency;
            } else if (frequencies[i] > 0) {
                System.err.printf("Внимание: нулевая теоретическая частота в интервале %d, "
                        + "но есть эмпирические данные\n", i);
                xi = Double.POSITIVE_INFINITY;
            }
        }

        this.pierson = round(xi);
    }

    private void computeKolmogorov(double[] values) {
        double[] sorted = values.clone();
        Arrays.sort(sorted);

        double dMax = 0.0;

        for (int i = 0; i < length; i++) {
            double empiricalCDF = (double) (i + 1) / length;

            double theoreticalCDF = distributionFunction.apply(sorted[i]);

            // Максимальное отклонение
            double diff = Math.abs(empiricalCDF - theoreticalCDF);
            if (diff > dMax) {
                dMax = diff;
            }
        }

        this.kolmogorov = round(dMax);
    }

    public void printResults() {
        System.out.println("\n" + createSeparatorLine("Проверка распределения", "=", 70));
        System.out.println("\n" + createSeparatorLine("Общие параметры", "_", 70));
        System.out.printf("Объём выборки (n): %d\n", length);
        System.out.printf("Число интервалов (k): %d\n", countOfParts);
        System.out.printf("Уровень значимости (α): %.2f\n", alpha);

        System.out.println("\n" + createSeparatorLine("Критерий Пирсона", "_", 70));
        System.out.printf("Число степеней свободы (r = k - 1): %d\n", r);
        System.out.printf("Наблюдаемое значение: %.5f\n", pierson);
        System.out.printf("Критическое значение (α=%.2f, r=%d): %.5f\n", alpha, r, criticalValuePierson);

        if (pierson < criticalValuePierson) {
            System.out.println("Результат: ГИПОТЕЗА ПРИНИМАЕТСЯ");
            System.out.println("(Эмпирическое распределение соответствует теоретическому)");
        } else {
            System.out.println("Результат: ГИПОТЕЗА ОТВЕРГАЕТСЯ");
            System.out.println("(Эмпирическое распределение не соответствует теоретическому)");
        }

        System.out.println("\n" + createSeparatorLine("Критерий Колмогорова", "_", 70));
        System.out.printf("Наблюдаемое значение D: %.5f\n", kolmogorov);
        System.out.printf("Критическое значение D(α=%.2f, n=%d): %.5f\n", alpha, length,
                getKolmogorovCriticalValue());

        if (kolmogorov < getKolmogorovCriticalValue()) {
            System.out.println("Результат: ГИПОТЕЗА ПРИНИМАЕТСЯ");
            System.out.println("(Эмпирическое распределение соответствует теоретическому)");
        } else {
            System.out.println("Результат: ГИПОТЕЗА ОТВЕРГАЕТСЯ");
            System.out.println("(Эмпирическое распределение не соответствует теоретическому)");
        }

        System.out.println("\n" + createSeparatorLine("", "=", 70));
    }

    private double getKolmogorovCriticalValue() {
        if (alpha == 0.05) {
            return round(1.36 / Math.sqrt(length));
        } else if (alpha == 0.01) {
            return round(1.63 / Math.sqrt(length));
        } else {
            return round(1.36 / Math.sqrt(length) * (Math.log(1.0 / alpha) / Math.log(1.0 / 0.05)));
        }
    }

    private String createSeparatorLine(String word, String separatorChar, int totalLength) {
        if (word.isEmpty()) {
            return separatorChar.repeat(totalLength);
        }

        if (word.length() >= totalLength) {
            return word;
        }

        int remainingLength = totalLength - word.length();
        int leftPadding = remainingLength / 2;
        int rightPadding = remainingLength - leftPadding;

        return separatorChar.repeat(leftPadding) + word + separatorChar.repeat(rightPadding);
    }

    private double round(double value) {
        if (Double.isInfinite(value) || Double.isNaN(value)) {
            return value;
        }
        int divider = (int) Math.pow(10, ACCURACY);
        return (double) Math.round(value * divider) / divider;
    }

    // Геттеры
    public double getPierson() {
        return pierson;
    }

    public double getKolmogorov() {
        return kolmogorov;
    }

    public double getCriticalValuePierson() {
        return criticalValuePierson;
    }

    public int[] getFrequencies() {
        return frequencies.clone();
    }

    public double[] getTheoreticalProbabilities() {
        return theoreticalProbabilities.clone();
    }

    public double[] getBoundaries() {
        return boundaries.clone();
    }

    public int getLength() {
        return length;
    }

    public int getCountOfParts() {
        return countOfParts;
    }
}