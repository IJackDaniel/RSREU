package com.IJackDaniel;

import java.util.Arrays;

public class QualityAppraiser {
    // Параметры последовательности
    private final int length;
    private final int countOfParts;
    private final double stepOfParts;

    // Последовательности данных
    private final double[] values;
    private double[] sortedValues;
    private final double[] probabilities;
    private final int[] frequencies;

    // Общие параметры
    private final double alpha;
    private final double beta;

    // Параметры для критерия Пирсона
    private final int r;
    private final double criticalValuePirson;

    // Значения критериев
    private double pirson;
    private double kolmogorov;

    // Параметры для вывода
    private final String SEPARATOR_1 = "=";
    private final String SEPARATOR_2 = "_";
    private final int LENGTH_OF_INPUT = 65;
    private static final int ACCURACY = 5;

    public QualityAppraiser(double[] values, int parts, double alpha) {
        this.length = values.length;
        this.countOfParts = parts;
        this.stepOfParts = this.round(1.0 / this.countOfParts);
        this.alpha = alpha;
        this.beta = this.round(1 - this.alpha);

        this.values = values;
        this.sortedValues = values.clone();
        Arrays.sort(this.sortedValues);
        this.probabilities = computeTheoreticalProbabilities();
        this.frequencies = generateFrequencies();
        this.r = this.countOfParts - 1;
        this.criticalValuePirson = PirsonCriticalValues.getPirsonCriticalValue(this.r, this.beta);

        computePirson();
        computeKolmogorov();
    }

    public void printParam() {
        System.out.println(createSeparatorLine("Параметры", this.SEPARATOR_1, this.LENGTH_OF_INPUT) +
                "\n" + createSeparatorLine("Общие", this.SEPARATOR_2, this.LENGTH_OF_INPUT) +
                "\nЧисло значений (N): " + this.length +
                "\nЧисло интервалов (k): " + this.countOfParts +
                "\nДлина интервала: " + this.stepOfParts +
                "\nУровень значимости (alpha): " + this.alpha +
                "\nНадёжность (beta): " + this.beta +
                "\n\n" + createSeparatorLine("Для критерия Пирсона", this.SEPARATOR_2, this.LENGTH_OF_INPUT) +
                "\nЧисло степеней свободы (r): " + this.r +
                "\nКритическое значение: " + this.criticalValuePirson +
                "\n\n" + createSeparatorLine("Для критерия числа серий", this.SEPARATOR_2, this.LENGTH_OF_INPUT) +
                "\n" + createSeparatorLine("", this.SEPARATOR_1, this.LENGTH_OF_INPUT) + "\n");
    }

    public void computePirson() {
        double xi = 0.0;
        for (int i = 0; i < this.countOfParts; i++) {
            xi += Math.pow(this.frequencies[i] - this.length * this.probabilities[i], 2) /
                    (this.length * this.probabilities[i]);
        }
        this.pirson = this.round(xi);
    }

    public void computeKolmogorov() {
        double dMax = 0.0;
        for (int i = 0; i < this.length; i++) {
            double ft = (double) (i + 1) / this.length;
            double dp = Math.abs((double) (i + 1) / this.length - ft);
            double dm = Math.abs(ft - (double) i / this.length);
            if (dp > dMax) dMax = dp;
            if (dm > dMax) dMax = dm;
        }
        this.kolmogorov = this.round(dMax * Math.sqrt(this.length));
    }

    public double getPirsonCriticalValue() {
        return this.criticalValuePirson;
    }

    public double getPirson() {
        return pirson;
    }

    public double getKolmogorov() {
        return kolmogorov;
    }


    public int[] getFrequencies() {
        return this.frequencies.clone();
    }

    private int[] generateFrequencies() {
        int[] frequencies = new int[this.countOfParts];
        for (int i = 0; i < this.length; i++) {
            int j = (int) (this.values[i] / this.stepOfParts);
            frequencies[j] += 1;
        }
        return frequencies;
    }

    private double[] computeTheoreticalProbabilities() {
        double[] result = new double[this.countOfParts];
        Arrays.fill(result, 1.0 / this.countOfParts);
        return result;
    }

    private String createSeparatorLine(String word, String separatorChar, int totalLength) {
        String processedWord = word;

        if (processedWord.length() >= totalLength) {
            return processedWord;
        }

        int remainingLength = totalLength - processedWord.length();
        int leftPadding = remainingLength / 2;
        int rightPadding = remainingLength - leftPadding;

        return separatorChar.repeat(leftPadding) + processedWord + separatorChar.repeat(rightPadding);
    }

    private double round(double value) {
        int divider = (int) Math.pow(10, ACCURACY);
        return (double) Math.round(value * divider) / divider;
    }
}
