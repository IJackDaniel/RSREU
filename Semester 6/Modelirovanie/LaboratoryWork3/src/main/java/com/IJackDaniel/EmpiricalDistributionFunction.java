package com.IJackDaniel;

import java.util.Arrays;
import java.util.function.Function;

public class EmpiricalDistributionFunction {

    private final double[] sortedValues;          // Отсортированная выборка
    private final int length;                     // Объём выборки
    private final Function<Double, Double> theoreticalCDF;  // Теоретическая функция распределения

    public EmpiricalDistributionFunction(double[] values, Function<Double, Double> theoreticalCDF) {
        this.sortedValues = values.clone();
        Arrays.sort(this.sortedValues);
        this.length = values.length;
        this.theoreticalCDF = theoreticalCDF;
    }

    public double getValue(double x) {
        if (x < sortedValues[0]) return 0.0;
        if (x >= sortedValues[length - 1]) return 1.0;

        int left = 0;
        int right = length - 1;
        int result = -1;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (sortedValues[mid] <= x) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return (double) (result + 1) / length;
    }

    public void printComparisonTable(double[] boundaries) {
        System.out.println("\n" + createSeparatorLine("Сравнение эмпирической и теоретической функций распределения", "=", 90));
        System.out.printf("%-15s %-20s %-20s %-15s\n", "x", "F_n(x)", "F(x)", "|F_n(x) - F(x)|");
        System.out.println("-".repeat(90));

        // Выводим значения на границах интервалов
        for (double boundary : boundaries) {
            double empirical = getValue(boundary);
            double theoretical = theoreticalCDF.apply(boundary);
            double diff = Math.abs(empirical - theoretical);

            System.out.printf("%-15.6f %-20.6f %-20.6f %-15.6f\n",
                    boundary, empirical, theoretical, diff);
        }

        System.out.println("-".repeat(90));
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
}