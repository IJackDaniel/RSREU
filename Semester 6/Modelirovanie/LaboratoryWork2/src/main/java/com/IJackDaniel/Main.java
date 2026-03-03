package com.IJackDaniel;

public class Main {
    // Исходные параметры
    private static final int N = 20_000;
    private static final int COUNT_OF_PARTS = 20;
    private static final double P = 0.25;

    // Параметры для проверки
     private static final double ALPHA = 0.05;

    public static void main(String[] args) {
        UniversalGenerator.setSeed(new int[]{5, 11, 3});
        QualityAppraiser appraiser = new QualityAppraiser(UniversalGenerator.generateValues(N), COUNT_OF_PARTS, ALPHA, P);

        appraiser.printParam();

        // Критерий Пирсона
        double pirson = appraiser.getPirson();
        double criticalValue = appraiser.getPirsonCriticalValue();
        String checkPirson = (pirson <= criticalValue)? "выполнено" : "не выполнено";
        System.out.println("Критерий Пирсона: " + pirson);
        System.out.println("Проверка равенства: " + pirson + " < " + criticalValue + " - " + checkPirson);

        // Критерий Колмогорова
        double kolmogorov = appraiser.getKolmogorov();
        System.out.println("\nКритерий Колмогорова: " + kolmogorov);

        // Критерий числа серий (p=0.25)
        int r = appraiser.getR();
        double rH = appraiser.getrH();
        double rB = appraiser.getrB();
        String result = (appraiser.checkBoundsForR())? "выполнено" : "не выполнено";
        System.out.println("\nКритерий числа серий (p=0.25): " + r);
        System.out.println("Проверка равенства: " + rH + " <= " + r + " <= " + rB + " - " + result);
    }
}
