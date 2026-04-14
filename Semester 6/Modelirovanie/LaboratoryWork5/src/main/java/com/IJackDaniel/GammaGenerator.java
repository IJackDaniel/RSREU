package com.IJackDaniel;

import com.IJackDaniel.basicClasses.UniversalGenerator;

public class GammaGenerator {
    private final double shape;  // k
    private final double scale;  // theta

    public GammaGenerator(double shape, double scale) {
        if (shape <= 0 || scale <= 0) {
            throw new IllegalArgumentException("Параметры должны быть > 0");
        }
        this.shape = shape;
        this.scale = scale;
    }

    public double next() {
        if (shape < 1.0) {
            // Метод Джонка для k < 1
            return nextJohnk() * scale;
        } else {
            // Метод Марсальи-Цанга для k >= 1
            double d = shape - 1.0 / 3.0;
            double c = 1.0 / Math.sqrt(9.0 * d);
            double x, v, u;
            while (true) {
                do {
                    x = normal();
                    v = 1.0 + c * x;
                } while (v <= 0.0);
                v = v * v * v;
                u = UniversalGenerator.rnd();
                if (u < 1.0 - 0.0331 * x * x * x * x) break;
                if (Math.log(u) < 0.5 * x * x + d * (1.0 - v + Math.log(v))) break;
            }
            return d * v * scale;
        }
    }

    private double nextJohnk() {
        // Метод Джонка для shape < 1
        double b = (Math.E + shape) / Math.E;
        double p, u1, u2, u3;
        while (true) {
            u1 = UniversalGenerator.rnd();
            u2 = UniversalGenerator.rnd();
            p = b * u1;
            if (p > 1.0) {
                double y = -Math.log((b - p) / shape);
                if (u2 <= Math.pow(y, shape - 1.0)) return y;
            } else {
                double y = Math.pow(p, 1.0 / shape);
                if (u2 <= Math.exp(-y)) return y;
            }
        }
    }

    // Полярный метод Бокса-Мюллера для нормального распределения N(0,1)
    private double normal() {
        double u1, u2, s;
        do {
            u1 = 2.0 * UniversalGenerator.rnd() - 1.0;
            u2 = 2.0 * UniversalGenerator.rnd() - 1.0;
            s = u1 * u1 + u2 * u2;
        } while (s >= 1.0 || s == 0.0);
        return u1 * Math.sqrt(-2.0 * Math.log(s) / s);
    }

    public double[] generateArray(int n) {
        double[] arr = new double[n];
        for (int i = 0; i < n; i++) arr[i] = next();
        return arr;
    }

    // Функция распределения (CDF) Гамма
    public double cdf(double x) {
        if (x <= 0) return 0.0;
        return SpecialMath.gammaP(shape, x / scale);
    }
}