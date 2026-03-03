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

    // Параметры для критерия числа серий
    private final double p;
    private final double tb;
    private double rH;
    private double rB;

    // Значения критериев
    private double pirson;
    private double kolmogorov;
    private int R;

    // Параметры для вывода
    private final String SEPARATOR1 = "=";
    private final String SEPARATOR2 = "_";
    private final int LENGTH_OF_INPUT = 65;

    public QualityAppraiser(double[] values, int parts, double alpha, double p) {
        this.length = values.length;
        this.countOfParts = parts;
        this.stepOfParts = 1.0 / this.countOfParts;
        this.alpha = alpha;
        this.beta = 1 - alpha;

        this.values = values;
        this.sortedValues = values.clone();
        Arrays.sort(this.sortedValues);
        this.probabilities = computeTheoreticalProbabilities();
        this.frequencies = generateFrequencies();

        this.r = this.countOfParts - 1;
        this.criticalValuePirson = PirsonCriticalValues.getPirsonCriticalValue(this.r, this.beta);

        this.p = p;
        this.tb = NormalQuantiles.getTB(this.alpha);
        computeBoundsForR();

        computePirson();
        computeKolmogorov();
        computeR();
    }

    public void printParam() {
        System.out.println(createSeparatorLine("Параметры", this.SEPARATOR1, this.LENGTH_OF_INPUT) +
                "\n" + createSeparatorLine("Общие", this.SEPARATOR2, this.LENGTH_OF_INPUT) +
                "\nЧисло значений (N): " + this.length +
                "\nЧисло интервалов (k): " + this.countOfParts +
                "\nДлина интервала: " + this.stepOfParts +
                "\nУровень значимости (alpha): " + this.alpha +
                "\nНадёжность (beta): " + this.beta +
                "\n\n" + createSeparatorLine("Для критерия Пирсона", this.SEPARATOR2, this.LENGTH_OF_INPUT) +
                "\nЧисло степеней свободы (r): " + this.r +
                "\nКритическое значение: " + this.criticalValuePirson +
                "\n\n" + createSeparatorLine("Для критерия числа серий", this.SEPARATOR2, this.LENGTH_OF_INPUT) +
                "\nРазделительный элемент (p): " + this.p +
                "\nЗначение квантиля стандартного нормального распределения: " + this.tb +
                "\nНижняя граница (RH): " + this.rH +
                "\nВерхняя граница (RB): " + this.rB +
                "\n" + createSeparatorLine("", this.SEPARATOR1, this.LENGTH_OF_INPUT) + "\n");
    }

    public void computePirson() {
        double xi = 0.0;
        for (int i = 0; i < this.countOfParts; i++) {
            xi += Math.pow(this.frequencies[i] - this.length * this.probabilities[i], 2) /
                    (this.length * this.probabilities[i]);
        }
        this.pirson = xi;
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
        this.kolmogorov = dMax * Math.sqrt(this.length);
    }

    public void computeR() {
        boolean y1;
        boolean y2;
        int r = 0;

        y1 = !(this.values[0] < this.p);

        for (int i = 1; i < this.length; i++) {
            y2 = !(this.values[i] < this.p);

            if(y1 != y2) r++;
            y1 = y2;
        }
        this.R = r;
    }

    public double getrH() {
        return this.rH;
    }

    public double getrB() {
        return this.rB;
    }

    public double getPirsonCriticalValue() {
        return this.criticalValuePirson;
    }

    public boolean checkBoundsForR() {
        return (this.rH <= this.R && this.R <= this.rB);
    }

    public double getPirson() {
        return pirson;
    }

    public double getKolmogorov() {
        return kolmogorov;
    }

    public int getR() {
        return R;
    }

    private void computeBoundsForR() {
        double expValue = computeExpValue();
        double dispersion = computeDispersion();
        double sigma = Math.sqrt(dispersion);
        this.rH = expValue - this.tb * sigma;
        this.rB = expValue + this.tb * sigma;
    }

    private double computeExpValue() {
        return 2 * this.length * this.p * (1 - this.p) + this.p * this.p + (1 - this.p) * (1 - this.p);
    }

    private double computeDispersion() {
        return 4 * this.length * this.p * (1 - this.p) * (1 - 3 * this.p * (1 - this.p)) -
                2 * this.p * (1 - this.p) * (3 - 10 * this.p * (1 - this.p));
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
}
