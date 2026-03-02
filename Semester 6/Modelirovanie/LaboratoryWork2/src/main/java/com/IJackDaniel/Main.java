package com.IJackDaniel;

import java.util.Arrays;

public class Main {
    // Исходные параметры
    private static final int N = 20000;
    private static final int COUNT_OF_PARTS = 20;
    private static final double P = 0.25;

    // Параметры для проверки
    private static final double ALPHA = 0.05;
    private static final double CRITICAL_VALUE_PIRSON = 10.12;
    private static final double BETA = 1 - ALPHA;
    private static final double TB = 1.96;

    // Параметры округления
    private static final int ACCURACY = 5;

    public static void main(String[] args) {
        UniversalGenerator.setSeed(new int[]{5, 11, 3});

        QualityAppraiser appraiser = new QualityAppraiser(generateValues(), COUNT_OF_PARTS, P, TB);

        appraiser.printParam();

        // Критерий Пирсона
        double pirson = appraiser.computePirson();
        System.out.println("Критерий Пирсона: " + round(pirson));
        String checkPirson = (pirson <= CRITICAL_VALUE_PIRSON)? "выполнено" : "не выполнено";
        System.out.println("Проверка равенства: " + round(pirson) + " < " + CRITICAL_VALUE_PIRSON + " - " + checkPirson);

        // Критерий Колмогорова
        double kolmogorov = appraiser.computeKolmogorov();
        System.out.println("\nКритерий Колмогорова: " + round(kolmogorov));

        // Критерий числа серий (p=0.25)
        int r = appraiser.getR();
        System.out.println("\nКритерий числа серий (p=0.25): " + r);
        double rH = appraiser.getrH();
        double rB = appraiser.getrB();
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
}
