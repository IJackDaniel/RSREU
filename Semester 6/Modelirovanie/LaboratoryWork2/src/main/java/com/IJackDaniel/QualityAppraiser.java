package com.IJackDaniel;

import java.util.Arrays;

public class QualityAppraiser {
    private int n;
    private int countOfParts;
    private double stepOfParts;

    private double[] values;
    private double[] sortedValues;
    private double[] probabilities;
    private int[] frequencies;

    private double p;
    private double tb;
    private double expValue;
    private double dispersion;
    private double sigma;
    private double rH;
    private double rB;

    public QualityAppraiser(double[] values, int parts, double p, double tb) {
        this.n = values.length;
        this.countOfParts = parts;
        this.stepOfParts = 1.0 / this.countOfParts;

        this.p = p;
        this.tb = tb;
        this.expValue = computeExpValue();
        this.dispersion = computeDispersion();
        this.sigma = Math.sqrt(this.dispersion);
        this.rH = this.expValue - this.tb * this.sigma;
        this.rB = this.expValue + this.tb * this.sigma;

        this.values = values;
        this.sortedValues = values.clone();
        Arrays.sort(this.sortedValues);
        this.probabilities = computeTheoreticalProbabilities();
        this.frequencies = generateFrequencies();
    }

    public void printParam() {
        String separator = "==============================";
        System.out.println(separator + "Параметры" + separator +
                "\nЧисло значений (N): " + this.n +
                "\nЧисло интервалов (k): " + this.countOfParts +
                "\nДлина интервала: " + this.stepOfParts +
                "\nЗначение квантиля стандартного нормального распределения: " + this.tb + "\n\n");
    }

    public double computePirson() {
        double xi = 0.0;
        for (int i = 0; i < this.countOfParts; i++) {
            xi += Math.pow(this.frequencies[i] - this.n * this.probabilities[i], 2) / (this.n * this.probabilities[i]);
        }
        return xi;
    }

    public double computeKolmogorov() {
        double dMax = 0.0;
        for (int i = 0; i < this.n; i++) {
            double ft = (double) (i + 1) / this.n;
            double dp = Math.abs((double) (i + 1) / this.n - ft);
            double dm = Math.abs(ft - (double) i / this.n);
            if (dp > dMax) dMax = dp;
            if (dm > dMax) dMax = dm;
        }
        return dMax * Math.sqrt(this.n);
    }

    public int getR() {
        boolean y1;
        boolean y2;
        int R = 0;

        y1 = !(this.values[0] < this.p);

        for (int i = 1; i < this.n; i++) {
            y2 = !(this.values[i] < this.p);

            if(y1 != y2) R++;
            y1 = y2;
        }
        return R;
    }

    public double getrH() {
        return this.rH;
    }

    public double getrB() {
        return this.rB;
    }

    private double computeExpValue() {
        return 2 * this.n * this.p * (1 - this.p) + this.p * this.p + (1 - this.p) * (1 - this.p);
    }

    private double computeDispersion() {
        return 4 * this.n * this.p * (1 - this.p) * (1 - 3 * this.p * (1 - this.p)) - 2 * this.p * (1 - this.p) * (3 - 10 * this.p * (1 - this.p));
    }

    private int[] generateFrequencies() {
        int[] frequencies = new int[this.countOfParts];
        for (int i = 0; i < this.n; i++) {
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
}
