package com.IJackDaniel;

import java.util.Arrays;

public class Main {
    // Параметры
    private static final int N = 20000;
    private static final int COUNT_OF_PARTS = 20;
    private static final double STEP_OF_PARTS = 1.0 / COUNT_OF_PARTS;
    private static final double P = 0.25;

    private static final int ACCURACY = 5;
    private static final int ROUND = (int) Math.pow(10, ACCURACY);

    public static void main(String[] args) {
        // Ввод seed для генератора
        UniversalGenerator.setSeed(new int[]{5, 11, 3});

        // Массив случайных значений
        double[] arrOfValues = generateValues();

        // Массив значений частот для гистограммы
        int[] frequencies = generateFrequencies(arrOfValues);

        // Составление названий столбцов гистограммы
        String[] labels = generateLabels();

        // Вывод гистограммы
        HistogramPrinter.printHistogram(labels, frequencies);

        // Критерий Пирсона
        double pirson = QualityAppraiser.computePirson(frequencies, computeTheoreticalProbabilities(), COUNT_OF_PARTS, N);
        System.out.println("Критерий Пирсона: " + round(pirson));

        // Критерий Колмогорова
        double kolmogorov = QualityAppraiser.computeKolmogorov(arrOfValues.clone(), N);
        System.out.println("Критерий Колмогорова: " + round(kolmogorov));

        // Критерий числа серий (p=0.25)
        int name = QualityAppraiser.getR(arrOfValues, N, P);
        System.out.println("Критерий числа серий (p=0.25): " + name);
    }

    private static double round(double value) {
        return (double) Math.round(value * ROUND) / ROUND;
    }

    private static double[] generateValues() {
        double[] arrOfValues = new double[N];
        for (int i = 0; i < N; i++) {
            double value = UniversalGenerator.rnd();
            arrOfValues[i] = value;
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

    private static double[] computeTheoreticalProbabilities() {
        double[] result = new double[COUNT_OF_PARTS];
        Arrays.fill(result, 1.0 / COUNT_OF_PARTS);
        return result;
    }
}
