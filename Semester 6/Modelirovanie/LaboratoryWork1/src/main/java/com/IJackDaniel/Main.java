package com.IJackDaniel;

import java.util.Arrays;

import static com.IJackDaniel.UniversalGenerator.rnd;

public class Main {
    // Параметры по варианту 2
    private static int n = 500;
    private static int countOfParts = 16;
    private static double stepOfParts = 1.0 / countOfParts;



    public static void main(String[] args) {
        double expectedValue = 0;
        double dispersion = 0;

        // Массив случайных значений
        double[] arrOfValues = new double[n];
        for (int i = 0; i < n; i++) {
            arrOfValues[i] = rnd();
            expectedValue += arrOfValues[i];
            dispersion += arrOfValues[i] * arrOfValues[i];
        }

        // Значения для гистограммы
        int[] valuesForGist = new int[countOfParts];
        for (int i = 0; i < n; i++) {
            int j = (int) (arrOfValues[i] / stepOfParts);
            valuesForGist[j] += 1;
        }

        for (int i = 0; i < countOfParts; i++) {
            System.out.println("Диапазон " + (i + 1) + " [" + stepOfParts * i + "; " + stepOfParts * (i + 1) +
                    "]. Количество значений: " + valuesForGist[i]);
        }
        System.out.println();

        // Проверка количества значений
        int sumCheck = 0;
        for (int num : valuesForGist) {
            sumCheck += num;
        }
        System.out.println("Проверка суммы значений: " + sumCheck);

        // Числовые характеристики
        expectedValue *= 1.0 / n;
        System.out.println("Матожидание: " + expectedValue);

        dispersion *= 1.0 / n;
        dispersion = dispersion - expectedValue * expectedValue;
        System.out.println("Дисперсия: " + dispersion);
    }
}
