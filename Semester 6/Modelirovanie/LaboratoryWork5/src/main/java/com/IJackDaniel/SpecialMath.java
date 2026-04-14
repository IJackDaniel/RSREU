package com.IJackDaniel;

public class SpecialMath {

    /**
     * Логарифм Гамма-функции (аппроксимация Ланцоша)
     */
    public static double logGamma(double x) {
        double[] cof = {
                76.18009172947146, -86.50532032941677,
                24.01409824083091, -1.231739572450155,
                0.1208650973866179e-2, -0.5395239384953e-5
        };
        double tmp = x + 5.5;
        tmp -= (x + 0.5) * Math.log(tmp);
        double ser = 1.000000000190015;
        double y = x;
        for (int j = 0; j <= 5; j++) {
            ser += cof[j] / ++y;
        }
        return -tmp + Math.log(2.5066282746310005 * ser / x);
    }

    /**
     * Гамма-функция
     */
    public static double gamma(double x) {
        return Math.exp(logGamma(x));
    }

    /**
     * Функция ошибок erf (аппроксимация)
     */
    public static double erf(double x) {
        double sign = (x >= 0) ? 1.0 : -1.0;
        x = Math.abs(x);

        double a1 = 0.254829592;
        double a2 = -0.284496736;
        double a3 = 1.421413741;
        double a4 = -1.453152027;
        double a5 = 1.061405429;
        double p = 0.3275911;

        double t = 1.0 / (1.0 + p * x);
        double y = 1.0 - (((((a5 * t + a4) * t) + a3) * t + a2) * t + a1) * t * Math.exp(-x * x);
        return sign * y;
    }

    /**
     * Обратная функция ошибок (аппроксимация)
     */
    public static double erfInv(double x) {
        double w, p;
        x = Math.max(-0.999, Math.min(0.999, x));
        w = -Math.log((1.0 - x) * (1.0 + x));
        if (w < 5.0) {
            w = w - 2.5;
            p = 2.81022636e-08;
            p = 3.43273939e-07 + p * w;
            p = -3.5233877e-06 + p * w;
            p = -4.39150654e-06 + p * w;
            p = 0.00021858087 + p * w;
            p = -0.00125372503 + p * w;
            p = -0.00417768164 + p * w;
            p = 0.246640727 + p * w;
            p = 1.50140941 + p * w;
        } else {
            w = Math.sqrt(w) - 3.0;
            p = -0.000200214257;
            p = 0.000100950558 + p * w;
            p = 0.00134934322 + p * w;
            p = -0.00367342844 + p * w;
            p = 0.00573950773 + p * w;
            p = -0.0076224613 + p * w;
            p = 0.00943887047 + p * w;
            p = 1.00167406 + p * w;
            p = 2.83297682 + p * w;
        }
        return p * x;
    }

    /**
     * Неполная гамма-функция (регуляризованная) P(a, x)
     */
    public static double gammaP(double a, double x) {
        if (x < 0.0 || a <= 0.0) return 0.0;
        if (x < a + 1.0) {
            return gammaSer(a, x);
        } else {
            return 1.0 - gammaCf(a, x);
        }
    }

    private static double gammaSer(double a, double x) {
        double gln = logGamma(a);
        double ap = a;
        double sum = 1.0 / a;
        double del = sum;
        for (int n = 1; n <= 100; n++) {
            ap += 1.0;
            del *= x / ap;
            sum += del;
            if (Math.abs(del) < Math.abs(sum) * 1e-12) break;
        }
        return sum * Math.exp(-x + a * Math.log(x) - gln);
    }

    private static double gammaCf(double a, double x) {
        double gln = logGamma(a);
        double b = x + 1.0 - a;
        double c = 1.0 / 1e-30;
        double d = 1.0 / b;
        double h = d;
        for (int i = 1; i <= 100; i++) {
            double an = -i * (i - a);
            b += 2.0;
            d = an * d + b;
            if (Math.abs(d) < 1e-30) d = 1e-30;
            c = b + an / c;
            if (Math.abs(c) < 1e-30) c = 1e-30;
            d = 1.0 / d;
            double del = d * c;
            h *= del;
            if (Math.abs(del - 1.0) < 1e-12) break;
        }
        return Math.exp(-x + a * Math.log(x) - gln) * h;
    }
}