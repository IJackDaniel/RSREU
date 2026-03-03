package com.IJackDaniel;

import java.util.Arrays;

public class QualityAppraiser {
    private final int length;
    private final int countOfParts;
    private final double stepOfParts;

    private final double[] values;
    private double[] sortedValues;
    private final double[] probabilities;
    private final int[] frequencies;

    private final double alpha;
    private final double beta;
    private final int r;

    private final double p;
    private final double tb;
    private double R;
    private double rH;
    private double rB;

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

        this.p = p;
        this.tb = NormalQuantiles.getTB(this.alpha);
        computeBoundsForR();
    }

    public void printParam() {
        String separator = "==============================";
        System.out.println(separator + "Параметры" + separator +
                "\nОбщие:" +
                "\nЧисло значений (N): " + this.length +
                "\nЧисло интервалов (k): " + this.countOfParts +
                "\nДлина интервала: " + this.stepOfParts +
                "\nУровень значимости (alpha)" + this.alpha +
                "\nНадёжность (beta)" + this.beta +
                "\n\nДля критерия Пирсона " +
                "\nЧисло степеней свободы (r): " + this.r +
                "\n\nДля критерия числа серий: " +
                "\nРазделительный элемент (p): " + this.p +
                "\nЗначение квантиля стандартного нормального распределения: " + this.tb + "\n\n");
    }

    public double computePirson() {
        double xi = 0.0;
        for (int i = 0; i < this.countOfParts; i++) {
            xi += Math.pow(this.frequencies[i] - this.length * this.probabilities[i], 2) /
                    (this.length * this.probabilities[i]);
        }
        return xi;
    }

    public double computeKolmogorov() {
        double dMax = 0.0;
        for (int i = 0; i < this.length; i++) {
            double ft = (double) (i + 1) / this.length;
            double dp = Math.abs((double) (i + 1) / this.length - ft);
            double dm = Math.abs(ft - (double) i / this.length);
            if (dp > dMax) dMax = dp;
            if (dm > dMax) dMax = dm;
        }
        return dMax * Math.sqrt(this.length);
    }

    public int getR() {
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
        return r;
    }

    public double getrH() {
        return this.rH;
    }

    public double getrB() {
        return this.rB;
    }

    public double getPirsonCriticalValue() {
        return PirsonCriticalValues.getPirsonCriticalValue(this.r, this.beta);
    }

    public boolean checkBoundsForR() {
        return (this.rH <= this.R && this.R <= this.rB);
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
}
