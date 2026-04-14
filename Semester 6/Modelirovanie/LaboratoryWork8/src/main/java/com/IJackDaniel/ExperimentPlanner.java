package com.IJackDaniel;

public class ExperimentPlanner {

    /**
     * Расчет объема выборки для оценки среднего значения.
     * Формула: N = (t_beta^2 * sigma^2) / epsilon^2
     *
     * @param tBeta   квантиль нормального распределения для заданной надежности beta
     * @param sigma   среднеквадратическое отклонение (оценка из пробного эксперимента)
     * @param epsilon заданная точность (полуширина доверительного интервала)
     * @return необходимый объем выборки N
     */
    public static int calculateNForMean(double tBeta, double sigma, double epsilon) {
        double n = Math.pow(tBeta * sigma / epsilon, 2);
        return (int) Math.ceil(n);
    }

    /**
     * Расчет объема выборки для оценки дисперсии.
     * Формула (приближенная для больших N, основанная на нормальной аппроксимации
     * распределения выборочной дисперсии):
     * N = 1 + 2 * (t_beta * sigma^2 / epsilon)^2
     *
     * @param tBeta   квантиль нормального распределения
     * @param sigma2  дисперсия (оценка из пробного эксперимента)
     * @param epsilon заданная точность оценки дисперсии
     * @return необходимый объем выборки N
     */
    public static int calculateNForVariance(double tBeta, double sigma2, double epsilon) {
        double n = 1 + 2 * Math.pow(tBeta * sigma2 / epsilon, 2);
        return (int) Math.ceil(n);
    }

    /**
     * Возвращает квантиль стандартного нормального распределения для заданной надежности beta.
     * Используются стандартные значения для типовых beta.
     *
     * @param beta надежность (например, 0.95, 0.99, 0.999)
     * @return квантиль t_beta
     */
    public static double getQuantile(double beta) {
        if (beta >= 0.999) return 3.30;
        if (beta >= 0.99) return 2.58;
        if (beta >= 0.98) return 2.33;
        if (beta >= 0.95) return 1.96;
        if (beta >= 0.90) return 1.65;
        // Для остальных случаев можно вычислить приближенно
        // но для лаб. работы хватит этих
        return 1.96;
    }
}