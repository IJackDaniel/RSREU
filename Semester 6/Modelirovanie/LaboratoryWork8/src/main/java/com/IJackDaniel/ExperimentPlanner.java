package com.IJackDaniel;

public class ExperimentPlanner {

    public static int calculateNForMeanNormal(double tBeta, double sigma, double epsilon) {
        double n = Math.pow(tBeta * sigma / epsilon, 2);
        return (int) Math.ceil(n);
    }

    public static int calculateNForMeanChebyshev(double alpha, double sigma, double epsilon) {
        double n = (sigma * sigma) / (alpha * epsilon * epsilon);
        return (int) Math.ceil(n);
    }

    public static int calculateNForVarianceChebyshev(double alpha, double sigma2, double epsilon) {
        double n = (2.0 * sigma2 * sigma2) / (alpha * epsilon * epsilon) + 1;
        return (int) Math.ceil(n);
    }

    public static double getQuantile(double beta) {
        if (beta >= 0.999) return 3.30;
        if (beta >= 0.99) return 2.58;
        if (beta >= 0.98) return 2.33;
        if (beta >= 0.95) return 1.96;
        if (beta >= 0.90) return 1.65;
        return 1.96;
    }
}