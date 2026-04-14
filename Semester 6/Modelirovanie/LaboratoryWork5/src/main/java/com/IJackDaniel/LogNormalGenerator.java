package com.IJackDaniel;

import com.IJackDaniel.basicClasses.UniversalGenerator;

public class LogNormalGenerator {
    private final double mu;
    private final double sigma;
    private boolean hasSpare = false;
    private double spare;

    public LogNormalGenerator(double mu, double sigma) {
        this.mu = mu;
        this.sigma = sigma;
    }

    public double next() {
        // Используем полярный метод для N(mu, sigma^2)
        double z;
        if (hasSpare) {
            hasSpare = false;
            z = spare;
        } else {
            double u1, u2, s;
            do {
                u1 = 2.0 * UniversalGenerator.rnd() - 1.0;
                u2 = 2.0 * UniversalGenerator.rnd() - 1.0;
                s = u1 * u1 + u2 * u2;
            } while (s >= 1.0 || s == 0.0);
            double mult = Math.sqrt(-2.0 * Math.log(s) / s);
            z = u1 * mult;
            spare = u2 * mult;
            hasSpare = true;
        }
        // Логнормальное преобразование
        return Math.exp(mu + sigma * z);
    }

    public double[] generateArray(int n) {
        double[] arr = new double[n];
        for (int i = 0; i < n; i++) arr[i] = next();
        return arr;
    }

    // Функция распределения (CDF) Логнормального
    public double cdf(double x) {
        if (x <= 0) return 0.0;
        double z = (Math.log(x) - mu) / sigma;
        return 0.5 * (1.0 + SpecialMath.erf(z / Math.sqrt(2.0)));
    }
}