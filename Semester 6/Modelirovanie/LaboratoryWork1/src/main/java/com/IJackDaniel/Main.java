package com.IJackDaniel;

import static com.IJackDaniel.UniversalGenerator.rnd;

public class Main {
    // Параметры по варианту 2
    private static final int N = 500;
    private static final int COUNT_OF_PARTS = 16;
    private static final double STEP_OF_PARTS = 1.0 / COUNT_OF_PARTS;

    public static void main(String[] args) {
        // Инициализация числовых характеристик
        double expectedValue = 0;
        double dispersion = 0;

        // Массив случайных значений
        double[] arrOfValues = new double[N];
        for (int i = 0; i < N; i++) {
            arrOfValues[i] = rnd();
            expectedValue += arrOfValues[i];
            dispersion += arrOfValues[i] * arrOfValues[i];
        }

        // Массив значений для гистограммы
        int[] valuesForGist = new int[COUNT_OF_PARTS];
        for (int i = 0; i < N; i++) {
            int j = (int) (arrOfValues[i] / STEP_OF_PARTS);
            valuesForGist[j] += 1;
        }

        // Составление названий столбцов гистограммы
        String[] labels = new String[valuesForGist.length];
        for (int i = 0; i < COUNT_OF_PARTS; i++) {
            labels[i] = "[" + STEP_OF_PARTS * i + "; " + STEP_OF_PARTS * (i + 1) + "]";
        }

        // Вывод гистограммы
        HistogramPrinter.printHistogram(labels, valuesForGist);

        // Проверка количества значений
        int sumCheck = 0;
        for (int num : valuesForGist) {
            sumCheck += num;
        }
        System.out.println("Проверка суммы значений: " + sumCheck);

        // Числовые характеристики
        // Математическое ожидание
        expectedValue *= 1.0 / N;
        System.out.println("Матожидание: " + expectedValue);

        // Дисперсия
        dispersion *= 1.0 / N;
        dispersion = dispersion - expectedValue * expectedValue;
        System.out.println("Дисперсия: " + dispersion);

        //

    }
}
