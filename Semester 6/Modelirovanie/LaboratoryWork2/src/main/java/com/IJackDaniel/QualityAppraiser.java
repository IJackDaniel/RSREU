package com.IJackDaniel;

import java.util.Arrays;

public class QualityAppraiser {
    public static double computePirson(int[] valuesFromHistogram, double[] probability, int k, int n) {
        double xi = 0.0;
        for (int i = 0; i < k; i++) {
            xi += Math.pow(valuesFromHistogram[i] - n * probability[i], 2) / (n * probability[i]);
        }
        return xi;
    }

    public static double computeKolmogorov(double[] values, int n) {
        Arrays.sort(values);
        double dMax = 0.0;
        for (int i = 0; i < n; i++) {
            double dp = Math.abs((double) (i + 1) / n - Ft(values[i], values, n));
            double dm = Math.abs(Ft(values[i], values, n) - (double) i / n);
            if (dp > dMax) dMax = dp;
            if (dm > dMax) dMax = dm;
        }
        return dMax * Math.sqrt(n);
    }

    public static int getR(double[] values, int n, double p) {
        boolean y1;
        boolean y2;
        int R = 0;

        if (values[0] < p) y1 = false;
        else y1 = true;

        for (int i = 1; i < n; i++) {
            if (values[i] < p) y2 = false;
            else y2 = true;

            if(y1 != y2) R++;
            y1 = y2;
        }
        return R;
    }

    private static double Ft(double value, double[] values, double n) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == value) {
                return (double) i / n;
            }
        }
        return 0;
    }
}
