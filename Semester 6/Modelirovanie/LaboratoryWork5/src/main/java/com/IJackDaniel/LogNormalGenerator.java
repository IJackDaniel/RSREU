package com.IJackDaniel;

import com.IJackDaniel.basicClasses.UniversalGenerator;

public class LogNormalGenerator {
    private final double mu;
    private final double sigma;

    public LogNormalGenerator(double mu, double sigma) {
        this.mu = mu;
        this.sigma = sigma;
    }

    public double next() {
        double s = 0.0;
        for (int j = 0; j < 12; j++) {
            s += UniversalGenerator.rnd();
        }

        double y = s - 6.0;

        return Math.exp(sigma * y + mu);
    }

    public double[] generateArray(int n) {
        double[] arr = new double[n];
        for (int i = 0; i < n; i++) {
            arr[i] = next();
        }
        return arr;
    }

    // Функция распределения (CDF) Логнормального
    public double cdf(double x) {
        if (x <= 0) return 0.0;
        double z = (Math.log(x) - mu) / sigma;
        return 0.5 * (1.0 + SpecialMath.erf(z / Math.sqrt(2.0)));
    }
}