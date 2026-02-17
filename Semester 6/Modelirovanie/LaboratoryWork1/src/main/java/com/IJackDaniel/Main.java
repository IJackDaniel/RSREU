package com.IJackDaniel;

import static com.IJackDaniel.UniversalGenerator.rnd;

public class Main {
    // Параметры по варианту 2
    private static final int N = 500;
    private static final int COUNT_OF_PARTS = 16;
    private static final double STEP_OF_PARTS = 1.0 / COUNT_OF_PARTS;
    private static final StatisticsCollector STATISTICS_COLLECTOR = new StatisticsCollector();

    public static void main(String[] args) {
        // Массив случайных значений
        double[] arrOfValues = generateValues();

        // Массив значений частот для гистограммы
        int[] frequencies = generateFrequencies(arrOfValues);

        // Составление названий столбцов гистограммы
        String[] labels = generateLabels();

        // Вывод гистограммы
        HistogramPrinter.printHistogram(labels, frequencies);

        // Числовые характеристики
        System.out.println("Проверка суммы значений: " + STATISTICS_COLLECTOR.getCountOfElements() +
                        "\n" + "Матожидание: " + STATISTICS_COLLECTOR.getExpectedValue() +
                        "\n" + "Дисперсия: " + STATISTICS_COLLECTOR.getDispersion() +
                        "\n" + "Второй начальный момент: " + STATISTICS_COLLECTOR.getSecondStartMoment() +
                        "\n" + "Третий начальный момент: " + STATISTICS_COLLECTOR.getThirdStartMoment()
                );

        // Статистическая функция распределения
        double[] statDistribution = new double[COUNT_OF_PARTS];
        int cumulativeSum = 0;
        for (int i = 0; i < COUNT_OF_PARTS; i++) {
            cumulativeSum += frequencies[i];
            statDistribution[i] = (double) cumulativeSum / N;
        }
        System.out.println("\nСтатистическая функция распределения:");
        for (int i = 0; i < COUNT_OF_PARTS; i++) {
            System.out.printf("%s: F(x)=%.4f\n", labels[i], statDistribution[i]);
        }
    }

    private static double[] generateValues() {
        double[] arrOfValues = new double[N];
        for (int i = 0; i < N; i++) {
            double value = rnd();
            arrOfValues[i] = value;

            // Для характеристик
            STATISTICS_COLLECTOR.addValue(value);
        }
        return arrOfValues;
    }

    private static int[] generateFrequencies(double[] arrayOfValues) {
        int[] frequencies = new int[COUNT_OF_PARTS];
        for (int i = 0; i < N; i++) {
            int j = (int) (arrayOfValues[i] / STEP_OF_PARTS);
            frequencies[j] += 1;
        }
        return frequencies;
    }

    private static String[] generateLabels() {
        String[] labels = new String[COUNT_OF_PARTS];
        for (int i = 0; i < COUNT_OF_PARTS; i++) {
            labels[i] = "[" + STEP_OF_PARTS * i + "; " + STEP_OF_PARTS * (i + 1) + "]";
        }
        return labels;
    }
}
