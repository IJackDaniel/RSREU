package com.IJackDaniel;

import java.util.Map;

public class PiersonCriticalValues {
    // Таблица критических значений критерия Пирсона для уровня значимости α
    // Ключ: степени свободы (r)
    // Значение: Map (α -> критическое значение)
    private static final Map<Integer, Map<Double, Double>> TABLE = Map.ofEntries(
            Map.entry(1, Map.ofEntries(
                    Map.entry(0.10, 2.71),
                    Map.entry(0.05, 3.84),
                    Map.entry(0.025, 5.02),
                    Map.entry(0.01, 6.63),
                    Map.entry(0.001, 10.83)
            )),
            Map.entry(2, Map.ofEntries(
                    Map.entry(0.10, 4.61),
                    Map.entry(0.05, 5.99),
                    Map.entry(0.025, 7.38),
                    Map.entry(0.01, 9.21),
                    Map.entry(0.001, 13.82)
            )),
            Map.entry(3, Map.ofEntries(
                    Map.entry(0.10, 6.25),
                    Map.entry(0.05, 7.81),
                    Map.entry(0.025, 9.35),
                    Map.entry(0.01, 11.34),
                    Map.entry(0.001, 16.27)
            )),
            Map.entry(4, Map.ofEntries(
                    Map.entry(0.10, 7.78),
                    Map.entry(0.05, 9.49),
                    Map.entry(0.025, 11.14),
                    Map.entry(0.01, 13.28),
                    Map.entry(0.001, 18.47)
            )),
            Map.entry(5, Map.ofEntries(
                    Map.entry(0.10, 9.24),
                    Map.entry(0.05, 11.07),
                    Map.entry(0.025, 12.83),
                    Map.entry(0.01, 15.09),
                    Map.entry(0.001, 20.52)
            )),
            Map.entry(6, Map.ofEntries(
                    Map.entry(0.10, 10.64),
                    Map.entry(0.05, 12.59),
                    Map.entry(0.025, 14.45),
                    Map.entry(0.01, 16.81),
                    Map.entry(0.001, 22.46)
            )),
            Map.entry(7, Map.ofEntries(
                    Map.entry(0.10, 12.02),
                    Map.entry(0.05, 14.07),
                    Map.entry(0.025, 16.01),
                    Map.entry(0.01, 18.48),
                    Map.entry(0.001, 24.32)
            )),
            Map.entry(8, Map.ofEntries(
                    Map.entry(0.10, 13.36),
                    Map.entry(0.05, 15.51),
                    Map.entry(0.025, 17.53),
                    Map.entry(0.01, 20.09),
                    Map.entry(0.001, 26.12)
            )),
            Map.entry(9, Map.ofEntries(
                    Map.entry(0.10, 14.68),
                    Map.entry(0.05, 16.92),
                    Map.entry(0.025, 19.02),
                    Map.entry(0.01, 21.67),
                    Map.entry(0.001, 27.88)
            )),
            Map.entry(10, Map.ofEntries(
                    Map.entry(0.10, 15.99),
                    Map.entry(0.05, 18.31),
                    Map.entry(0.025, 20.48),
                    Map.entry(0.01, 23.21),
                    Map.entry(0.001, 29.59)
            )),
            Map.entry(11, Map.ofEntries(
                    Map.entry(0.10, 17.28),
                    Map.entry(0.05, 19.68),
                    Map.entry(0.025, 21.92),
                    Map.entry(0.01, 24.72),
                    Map.entry(0.001, 31.26)
            )),
            Map.entry(12, Map.ofEntries(
                    Map.entry(0.10, 18.55),
                    Map.entry(0.05, 21.03),
                    Map.entry(0.025, 23.34),
                    Map.entry(0.01, 26.22),
                    Map.entry(0.001, 32.91)
            )),
            Map.entry(13, Map.ofEntries(
                    Map.entry(0.10, 19.81),
                    Map.entry(0.05, 22.36),
                    Map.entry(0.025, 24.74),
                    Map.entry(0.01, 27.69),
                    Map.entry(0.001, 34.53)
            )),
            Map.entry(14, Map.ofEntries(
                    Map.entry(0.10, 21.06),
                    Map.entry(0.05, 23.68),
                    Map.entry(0.025, 26.12),
                    Map.entry(0.01, 29.14),
                    Map.entry(0.001, 36.12)
            )),
            Map.entry(15, Map.ofEntries(
                    Map.entry(0.10, 22.31),
                    Map.entry(0.05, 25.00),
                    Map.entry(0.025, 27.49),
                    Map.entry(0.01, 30.58),
                    Map.entry(0.001, 37.70)
            )),
            Map.entry(16, Map.ofEntries(
                    Map.entry(0.10, 23.54),
                    Map.entry(0.05, 26.30),
                    Map.entry(0.025, 28.85),
                    Map.entry(0.01, 32.00),
                    Map.entry(0.001, 39.25)
            )),
            Map.entry(17, Map.ofEntries(
                    Map.entry(0.10, 24.77),
                    Map.entry(0.05, 27.59),
                    Map.entry(0.025, 30.19),
                    Map.entry(0.01, 33.41),
                    Map.entry(0.001, 40.79)
            )),
            Map.entry(18, Map.ofEntries(
                    Map.entry(0.10, 25.99),
                    Map.entry(0.05, 28.87),
                    Map.entry(0.025, 31.53),
                    Map.entry(0.01, 34.81),
                    Map.entry(0.001, 42.31)
            )),
            Map.entry(19, Map.ofEntries(
                    Map.entry(0.10, 27.20),
                    Map.entry(0.05, 30.14),
                    Map.entry(0.025, 32.85),
                    Map.entry(0.01, 36.19),
                    Map.entry(0.001, 43.82)
            )),
            Map.entry(20, Map.ofEntries(
                    Map.entry(0.10, 28.41),
                    Map.entry(0.05, 31.41),
                    Map.entry(0.025, 34.17),
                    Map.entry(0.01, 37.57),
                    Map.entry(0.001, 45.32)
            )),
            Map.entry(25, Map.ofEntries(
                    Map.entry(0.10, 34.38),
                    Map.entry(0.05, 37.65),
                    Map.entry(0.025, 40.65),
                    Map.entry(0.01, 44.31),
                    Map.entry(0.001, 52.62)
            )),
            Map.entry(30, Map.ofEntries(
                    Map.entry(0.10, 40.26),
                    Map.entry(0.05, 43.77),
                    Map.entry(0.025, 46.98),
                    Map.entry(0.01, 50.89),
                    Map.entry(0.001, 59.70)
            )),
            Map.entry(50, Map.ofEntries(
                    Map.entry(0.10, 63.17),
                    Map.entry(0.05, 67.50),
                    Map.entry(0.025, 71.42),
                    Map.entry(0.01, 76.15),
                    Map.entry(0.001, 86.66)
            )),
            Map.entry(100, Map.ofEntries(
                    Map.entry(0.10, 118.50),
                    Map.entry(0.05, 124.34),
                    Map.entry(0.025, 129.56),
                    Map.entry(0.01, 135.81),
                    Map.entry(0.001, 149.45)
            ))
    );

    public static double getPiersonCriticalValue(int r, double alpha) {
        // Проверяем, есть ли в таблице нужное количество степеней свободы
        if (TABLE.containsKey(r)) {
            Map<Double, Double> alphaMap = TABLE.get(r);

            // Ищем ближайшее значение alpha
            double closestAlpha = alpha;
            double closestValue = 0;
            double minDiff = Double.MAX_VALUE;

            for (Map.Entry<Double, Double> entry : alphaMap.entrySet()) {
                double diff = Math.abs(entry.getKey() - alpha);
                if (diff < minDiff) {
                    minDiff = diff;
                    closestAlpha = entry.getKey();
                    closestValue = entry.getValue();
                }
            }

            return closestValue;
        }

        // Если r > 100, используем аппроксимацию
        if (r > 100) {
            double z = 0;
            if (Math.abs(alpha - 0.05) < 0.01) {
                z = 1.645;
            } else if (Math.abs(alpha - 0.01) < 0.01) {
                z = 2.326;
            } else {
                z = 1.96;
            }

            return (z * Math.sqrt(2 * r - 1) + 2 * r - 1) / 2;
        }

        // Для r, которых нет в таблице, используем интерполяцию
        return interpolateCriticalValue(r, alpha);
    }

    private static double interpolateCriticalValue(int r, double alpha) {
        // Находим ближайшие известные значения r1 и r2
        int r1 = -1, r2 = -1;
        double value1 = 0, value2 = 0;

        for (Integer knownR : TABLE.keySet()) {
            if (knownR <= r) {
                if (knownR > r1) {
                    r1 = knownR;
                    value1 = TABLE.get(knownR).get(alpha);
                }
            }
            if (knownR >= r) {
                if (r2 == -1 || knownR < r2) {
                    r2 = knownR;
                    value2 = TABLE.get(knownR).get(alpha);
                }
            }
        }

        if (r1 == -1) {
            return value2;
        }
        if (r2 == -1) {
            return value1;
        }
        if (r1 == r2) {
            return value1;
        }

        // Линейная интерполяция
        return value1 + (value2 - value1) * (r - r1) / (r2 - r1);
    }
}