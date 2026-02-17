package com.IJackDaniel;

import static com.IJackDaniel.UniversalGenerator.rnd;

public class Main {
    // Параметры по варианту 2
    private static final int N = 500;
    private static final int COUNT_OF_PARTS = 16;
    private static final double STEP_OF_PARTS = 1.0 / COUNT_OF_PARTS;

    public static void main(String[] args) {
        // Инициализация числовых характеристик
        double sumValues = 0;
        double sumSquaresValues = 0;
        double sumCubedValues = 0;

        // Массив случайных значений
        double[] arrOfValues = new double[N];
        for (int i = 0; i < N; i++) {
            arrOfValues[i] = rnd();

            // Для характеристик
            sumValues += arrOfValues[i];
            sumSquaresValues += arrOfValues[i] * arrOfValues[i];
            sumCubedValues += arrOfValues[i] * arrOfValues[i] * arrOfValues[i];
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
        double expectedValue = sumValues *  (1.0 / N);
        System.out.println("Матожидание: " + expectedValue);

        // Дисперсия
        double dispersion = sumSquaresValues * (1.0 / N);
        dispersion = dispersion - expectedValue * expectedValue;
        System.out.println("Дисперсия: " + dispersion);

        // Второй начальный момент
        double secondStartMoment = sumSquaresValues * (1.0 / N);
        System.out.println("Второй начальный момент: " + secondStartMoment);

        // Третий начальный момент
        double thirdStartMoment = sumCubedValues * (1.0 / N);
        System.out.println("Третий начальный момент: " + thirdStartMoment);

        // Статистическая функция распределения
        double[] statDistribution = new double[COUNT_OF_PARTS];
        int cumulativeSum = 0;
        for (int i = 0; i < COUNT_OF_PARTS; i++) {
            cumulativeSum += valuesForGist[i];
            statDistribution[i] = (double) cumulativeSum / N;
        }
        System.out.println("\nСтатистическая функция распределения:");
        for (int i = 0; i < COUNT_OF_PARTS; i++) {
            System.out.printf("%s: F(x)=%.4f\n", labels[i], statDistribution[i]);
        }
    }
}
