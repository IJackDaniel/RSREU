package com.IJackDaniel;

public class RandomVariableGenerator {

    // Границы участков
    private static final double BOUNDARY_1 = 0.0;
    private static final double BOUNDARY_2 = 0.5;
    private static final double BOUNDARY_3 = 1.3;
    private static final double BOUNDARY_4 = 1.5;

    // Площадь первого участка (четверть круга радиусом 0.5)
    private static final double AREA_1 = Math.PI * 0.5 * 0.5 / 4;

    // Коэффициенты для второго участка (будет найдены)
    private final double k2;     // нормировочный коэффициент для второго участка
    private final double a2;     // коэффициент при x для плотности на втором участке
    private final double b2;     // свободный член для плотности на втором участке

    // Коэффициенты для третьего участка
    private final double k3;     // нормировочный коэффициент для третьего участка
    private final double a3;     // коэффициент при x для плотности на третьем участке
    private final double b3;     // свободный член для плотности на третьем участке

    // Площади участков
    private final double area2;
    private final double area3;

    // Значения функции распределения на границах
    private final double fAtBoundary2;
    private final double fAtBoundary3;

    public RandomVariableGenerator() {
        // Исходные формы плотности (без нормировки)
        double rawA2 = 0.3125;
        double rawB2 = 0.34375;
        double rawA3 = 1.25;
        double rawB3 = -0.875;

        // Вычисляем интегралы от исходных форм
        double rawIntegral2 = (rawA2/2)*(BOUNDARY_3*BOUNDARY_3 - BOUNDARY_2*BOUNDARY_2) +
                rawB2*(BOUNDARY_3 - BOUNDARY_2);

        double rawIntegral3 = (rawA3/2)*(BOUNDARY_4*BOUNDARY_4 - BOUNDARY_3*BOUNDARY_3) +
                rawB3*(BOUNDARY_4 - BOUNDARY_3);

        // Значения в точке стыка x = 1.3
        double rawValueAt13_2 = rawA2 * BOUNDARY_3 + rawB2;
        double rawValueAt13_3 = rawA3 * BOUNDARY_3 + rawB3;

        double ratio = rawValueAt13_2 / rawValueAt13_3;
        double denominator = rawIntegral2 + ratio * rawIntegral3;

        k2 = (1.0 - AREA_1) / denominator;
        k3 = k2 * ratio;

        // Вычисляем коэффициенты для плотности
        a2 = rawA2 * k2;
        b2 = rawB2 * k2;
        a3 = rawA3 * k3;
        b3 = rawB3 * k3;

        // Вычисляем площади участков
        area2 = k2 * rawIntegral2;
        area3 = k3 * rawIntegral3;

        // Вычисляем значения функции распределения на границах
        fAtBoundary2 = AREA_1;
        fAtBoundary3 = AREA_1 + area2;
    }

    // Плотность распределения
    public double getDensity(double x) {
        if (x < BOUNDARY_1 || x > BOUNDARY_4) return 0.0;
        if (x < BOUNDARY_2) {
            return Math.sqrt(0.25 - (x - 0.5) * (x - 0.5));
        }
        if (x < BOUNDARY_3) {
            return a2 * x + b2;
        }
        if (x < BOUNDARY_4) {
            return a3 * x + b3;
        }
        return 0.0;
    }

    private double computeF1(double x) {
        double arcsinPart = 0.25 * Math.asin(2 * x - 1);
        double sqrtPart = (x - 0.5) * Math.sqrt(0.25 - (x - 0.5) * (x - 0.5));

        // Нормируем результат
        double rawF1 = Math.PI / 8 + arcsinPart + sqrtPart;
        return AREA_1 * (rawF1 / (Math.PI / 8));
    }

    private double computeF2(double x) {
        double integral = (a2 / 2) * x * x + b2 * x;
        double integralAt05 = (a2 / 2) * BOUNDARY_2 * BOUNDARY_2 + b2 * BOUNDARY_2;
        return fAtBoundary2 + (integral - integralAt05);
    }

    private double computeF3(double x) {
        double integral = (a3 / 2) * x * x + b3 * x;
        double integralAt13 = (a3 / 2) * BOUNDARY_3 * BOUNDARY_3 + b3 * BOUNDARY_3;
        return fAtBoundary3 + (integral - integralAt13);
    }

    public double getDistributionFunction(double x) {
        if (x < BOUNDARY_1) return 0.0;
        if (x < BOUNDARY_2) {
            double f1 = computeF1(x);
            return Math.max(0.0, Math.min(fAtBoundary2, f1));
        }
        if (x < BOUNDARY_3) {
            double f2 = computeF2(x);
            return Math.max(fAtBoundary2, Math.min(fAtBoundary3, f2));
        }
        if (x < BOUNDARY_4) {
            double f3 = computeF3(x);
            return Math.max(fAtBoundary3, Math.min(1.0, f3));
        }
        return 1.0;
    }


    public double generate() {
        double u = UniversalGenerator.rnd();

        if (u < fAtBoundary2) {
            return solveOnFirstSegment(u);
        } else if (u < fAtBoundary3) {
            return solveOnSecondSegment(u);
        } else {
            return solveOnThirdSegment(u);
        }
    }

    private double solveOnFirstSegment(double u) {
        double left = BOUNDARY_1;
        double right = BOUNDARY_2;

        double uNorm = u / fAtBoundary2;

        for (int iteration = 0; iteration < 60; iteration++) {
            double mid = (left + right) / 2;
            double fMid = computeF1(mid) / fAtBoundary2;

            if (fMid < uNorm) {
                left = mid;
            } else {
                right = mid;
            }
        }

        return (left + right) / 2;
    }

    private double solveOnSecondSegment(double u) {
        double integralAt05 = (a2 / 2) * BOUNDARY_2 * BOUNDARY_2 + b2 * BOUNDARY_2;
        double c = fAtBoundary2 - integralAt05;

        double A = a2 / 2;
        double B = b2;
        double C = c - u;

        double discriminant = B * B - 4 * A * C;
        double x = (-B + Math.sqrt(discriminant)) / (2 * A);

        return x;
    }

    private double solveOnThirdSegment(double u) {
        double integralAt13 = (a3 / 2) * BOUNDARY_3 * BOUNDARY_3 + b3 * BOUNDARY_3;
        double c = fAtBoundary3 - integralAt13;

        double A = a3 / 2;
        double B = b3;
        double C = c - u;

        double discriminant = B * B - 4 * A * C;
        double x = (-B + Math.sqrt(discriminant)) / (2 * A);

        return x;
    }

    public double[] generateSample(int size) {
        double[] sample = new double[size];
        for (int i = 0; i < size; i++) {
            sample[i] = generate();
        }
        return sample;
    }
}