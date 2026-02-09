package com.IJackDaniel;

import java.util.Arrays;

import static com.IJackDaniel.UniversalGenerator.rnd;

public class Main {
    // Параметры по варианту 2
    private static int n = 500;
    private static int countOfParts = 16;
    private static int sizeOfPart = n /countOfParts + 1;

    public static void main(String[] args) {
        // Массив случайных значений
        double[] arrOfValues = new double[n];
        for (int i = 0; i < n; i++) {
            arrOfValues[i] = rnd();
        }
        Arrays.sort(arrOfValues);

        // Массив со средними значениями по количеству участков разбиения
        double[] miniArray = new double[countOfParts];
        for (int i = 0; i < n; i++) {
            int j = i / sizeOfPart;
            miniArray[j] = miniArray[j] + arrOfValues[i];
        }
        for (int j = 0; j < countOfParts; j++) {
            miniArray[j] = miniArray[j] / sizeOfPart;
        }

        // Вывод массива
        for (double num : miniArray) {
            System.out.println(num);
        }
    }
}
