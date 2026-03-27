package com.IJackDaniel;

import java.util.function.Function;


public class NormalGenerator {

    private static final int CLT_SUM_COUNT = 12; // Количество слагаемых для ЦПТ

    public static double nextNormalCLT() {
        double sum = 0.0;
        for (int i = 0; i < CLT_SUM_COUNT; i++) {
            sum += UniversalGenerator.rnd();
        }
        return sum - 6.0;
    }

    public static double nextNormalBoxMuller() {
        double u1 = UniversalGenerator.rnd();
        double u2 = UniversalGenerator.rnd();

        // Избегаем нуля, чтобы избежать логарифма от нуля
        if (u1 == 0.0) u1 = 1e-10;

        double z0 = Math.sqrt(-2.0 * Math.log(u1)) * Math.cos(2.0 * Math.PI * u2);

        return z0;
    }

    public static double[] generateSampleCLT(int n) {
        double[] sample = new double[n];
        for (int i = 0; i < n; i++) {
            sample[i] = nextNormalCLT();
        }
        return sample;
    }

    public static double[] generateSampleBoxMuller(int n) {
        double[] sample = new double[n];
        for (int i = 0; i < n; i++) {
            sample[i] = nextNormalBoxMuller();
        }
        return sample;
    }

    public static double standardNormalCDF(double x) {
        // Аппроксимация функции распределения стандартного нормального закона
        double t = 1.0 / (1.0 + 0.2316419 * Math.abs(x));
        double d = 0.3989423 * Math.exp(-x * x / 2.0);
        double p = d * t * (0.3193815 + t * (-0.3565638 + t * (1.781478 + t * (-1.821256 + t * 1.330274))));

        if (x > 0) {
            return 1.0 - p;
        } else {
            return p;
        }
    }

    public static Function<Double, Double> getDistributionFunction() {
        return NormalGenerator::standardNormalCDF;
    }
}