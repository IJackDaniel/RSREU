package com.IJackDaniel;

import java.util.Arrays;

public class Main {
    // Исходные параметры
    private static final int N = 20000;
    private static final int COUNT_OF_PARTS = 20;
    private static final double STEP_OF_PARTS = 1.0 / COUNT_OF_PARTS;
    private static final double P = 0.25;

    // Параметры для проверки
    private static final double ALPHA = 0.05;
    private static final double CRITICAL_VALUE_PIRSON = 10.12;
    private static final double BETA = 1 - ALPHA;
    private static final double TB = 1.96;

    // Параметры округления
    private static final int ACCURACY = 5;

    public static void main(String[] args) {
        // Ввод seed для генератора
        UniversalGenerator.setSeed(new int[]{5, 11, 3});

        // Массив случайных значений
        double[] arrOfValues = generateValues();

        // Массив значений частот
        int[] frequencies = generateFrequencies(arrOfValues);

//        // Составление названий столбцов гистограммы
//         String[] labels = generateLabels();
//
//        // Вывод гистограммы
//         HistogramPrinter.printHistogram(labels, frequencies);

        // Вывод параметров:
        String separator = "==============================";
        System.out.println(separator + "Параметры" + separator +
                "\nЧисло значений (N): " + N +
                "\nЧисло интервалов (k): " + COUNT_OF_PARTS +
                "\nДлина интервала: " + STEP_OF_PARTS +
                "\nУровень значимости (альфа): " + ALPHA +
                "\nНадёжность (бета): " + BETA +
                "\nЗначение квантиля стандартного нормального распределения: " + TB +
                "\n\n" + separator + "Критерии" + separator);

        // Критерий Пирсона
        double pirson = QualityAppraiser.computePirson(frequencies, computeTheoreticalProbabilities(), COUNT_OF_PARTS, N);
        System.out.println("Критерий Пирсона: " + round(pirson));
        String checkPirson = (pirson <= CRITICAL_VALUE_PIRSON)? "выполнено" : "не выполнено";
        System.out.println("Проверка равенства: " + round(pirson) + " < " + CRITICAL_VALUE_PIRSON + " - " + checkPirson);

        // Критерий Колмогорова
        double kolmogorov = QualityAppraiser.computeKolmogorov(arrOfValues.clone(), N);
        System.out.println("\nКритерий Колмогорова: " + round(kolmogorov));

        // Критерий числа серий (p=0.25)
        int r = QualityAppraiser.getR(arrOfValues.clone(), N, P);
        System.out.println("\nКритерий числа серий (p=0.25): " + r);
        double expVal = 2 * N * P * (1 - P) + P * P + (1 - P) * (1 - P);
        double dispersion = 4 * N * P * (1 - P) * (1 - 3 * P * (1 - P)) - 2 * P * (1 - P) * (3 - 10 * P * (1 - P));
        double sigma = Math.sqrt(dispersion);
        double rH = expVal - TB * sigma;
        double rB = expVal + TB * sigma;
        String result = (rH <= r && r <= rB)? "выполнено" : "не выполнено";
        System.out.println("Проверка равенства: " + round(rH) + " <= " + r + " <= " + round(rB) + " - " + result);
    }

    private static double round(double value) {
        int divider = (int) Math.pow(10, ACCURACY);
        return (double) Math.round(value * divider) / divider;
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
