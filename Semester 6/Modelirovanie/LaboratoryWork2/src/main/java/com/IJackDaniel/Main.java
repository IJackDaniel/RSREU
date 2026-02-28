package com.IJackDaniel;

public class Main {
    // Параметры
    private static final int N = 20000;
    private static final int COUNT_OF_PARTS = 20;
    private static final double STEP_OF_PARTS = 1.0 / COUNT_OF_PARTS;

    public static void main(String[] args) {
        // Ввод seed для генератора
        UniversalGenerator.setSeed(new int[]{1, 1, 1});

        // Массив случайных значений
        double[] arrOfValues = generateValues();

        // Массив значений частот для гистограммы
        int[] frequencies = generateFrequencies(arrOfValues);

        // Составление названий столбцов гистограммы
        String[] labels = generateLabels();

        // Вывод гистограммы
        HistogramPrinter.printHistogram(labels, frequencies);

        // Критерий Пирсона

        // Критерий Колмогорова

        // Критерий числа серий (p=0.25)
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
}
