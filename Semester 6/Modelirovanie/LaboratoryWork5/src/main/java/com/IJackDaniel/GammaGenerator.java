package com.IJackDaniel;

import com.IJackDaniel.basicClasses.UniversalGenerator;

public class GammaGenerator {
    private final int shape;      // α = n (целое число) форма
    private final double scale;   // β = theta масштаб

    public GammaGenerator(double shape, double scale) {
        if (shape <= 0 || scale <= 0) {
            throw new IllegalArgumentException("Параметры должны быть > 0");
        }
        this.shape = (int) shape;
        this.scale = scale;
    }

    public double next() {
        double product = 1.0;

        for (int k = 0; k < shape; k++) {
            product *= UniversalGenerator.rnd();
        }

        return -Math.log(product) * scale;
    }

    public double[] generateArray(int n) {
        double[] arr = new double[n];
        for (int i = 0; i < n; i++) {
            arr[i] = next();
        }
        return arr;
    }

    public double cdf(double x) {
        if (x <= 0) return 0.0;
        return SpecialMath.gammaP(shape, x / scale);
    }
}