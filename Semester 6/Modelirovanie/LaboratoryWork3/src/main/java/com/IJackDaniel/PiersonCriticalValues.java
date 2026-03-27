package com.IJackDaniel;

import java.util.Map;

public class PirsonCriticalValues {
    private static final Map<Integer, Map<Double, Double>> TABLE = Map.ofEntries(
            Map.entry(
                    5, Map.ofEntries(
                            Map.entry(0.99, 0.55),
                            Map.entry(0.975, 0.83),
                            Map.entry(0.950, 1.15),
                            Map.entry(0.9, 1.61),
                            Map.entry(0.8, 2.34),
                            Map.entry(0.7, 3.0),
                            Map.entry(0.6, 3.66),
                            Map.entry(0.5, 4.35),
                            Map.entry(0.4, 5.13),
                            Map.entry(0.3, 6.06)
                    )),
            Map.entry(
                    6, Map.ofEntries(
                            Map.entry(0.99, 0.87),
                            Map.entry(0.975, 1.24),
                            Map.entry(0.950, 1.64),
                            Map.entry(0.9, 2.2),
                            Map.entry(0.8, 3.07),
                            Map.entry(0.7, 3.83),
                            Map.entry(0.6, 4.57),
                            Map.entry(0.5, 5.35),
                            Map.entry(0.4, 6.21),
                            Map.entry(0.3, 7.23)
                    )),
            Map.entry(
                    7, Map.ofEntries(
                            Map.entry(0.99, 1.24),
                            Map.entry(0.975, 1.69),
                            Map.entry(0.950, 2.17),
                            Map.entry(0.9, 2.83),
                            Map.entry(0.8, 3.82),
                            Map.entry(0.7, 4.67),
                            Map.entry(0.6, 5.49),
                            Map.entry(0.5, 6.35),
                            Map.entry(0.4, 7.28),
                            Map.entry(0.3, 8.38)
                    )),
            Map.entry(
                    8, Map.ofEntries(
                            Map.entry(0.99, 1.65),
                            Map.entry(0.975, 2.18),
                            Map.entry(0.950, 2.73),
                            Map.entry(0.9, 3.49),
                            Map.entry(0.8, 4.59),
                            Map.entry(0.7, 5.53),
                            Map.entry(0.6, 6.42),
                            Map.entry(0.5, 7.34),
                            Map.entry(0.4, 8.35),
                            Map.entry(0.3, 9.52)
                    )),
            Map.entry(
                    9, Map.ofEntries(
                            Map.entry(0.99, 2.09),
                            Map.entry(0.975, 2.7),
                            Map.entry(0.950, 3.33),
                            Map.entry(0.9, 4.17),
                            Map.entry(0.8, 5.38),
                            Map.entry(0.7, 6.39),
                            Map.entry(0.6, 7.36),
                            Map.entry(0.5, 8.34),
                            Map.entry(0.4, 9.41),
                            Map.entry(0.3, 10.66)
                    )),
            Map.entry(
                    10, Map.ofEntries(
                            Map.entry(0.99, 2.56),
                            Map.entry(0.975, 3.25),
                            Map.entry(0.950, 3.94),
                            Map.entry(0.9, 4.87),
                            Map.entry(0.8, 6.18),
                            Map.entry(0.7, 7.27),
                            Map.entry(0.6, 8.3),
                            Map.entry(0.5, 9.34),
                            Map.entry(0.4, 10.47),
                            Map.entry(0.3, 11.78)
                    )),
            Map.entry(
                    11, Map.ofEntries(
                            Map.entry(0.99, 3.05),
                            Map.entry(0.975, 3.82),
                            Map.entry(0.950, 4.57),
                            Map.entry(0.9, 5.58),
                            Map.entry(0.8, 6.99),
                            Map.entry(0.7, 8.15),
                            Map.entry(0.6, 9.24),
                            Map.entry(0.5, 10.34),
                            Map.entry(0.4, 11.53),
                            Map.entry(0.3, 12.90)
                    )),
            Map.entry(
                    12, Map.ofEntries(
                            Map.entry(0.99, 3.57),
                            Map.entry(0.975, 4.4),
                            Map.entry(0.950, 5.23),
                            Map.entry(0.9, 6.30),
                            Map.entry(0.8, 7.81),
                            Map.entry(0.7, 9.03),
                            Map.entry(0.6, 10.18),
                            Map.entry(0.5, 11.34),
                            Map.entry(0.4, 12.58),
                            Map.entry(0.3, 14.01)
                    )),
            Map.entry(
                    13, Map.ofEntries(
                            Map.entry(0.99, 4.11),
                            Map.entry(0.975, 5.01),
                            Map.entry(0.950, 5.89),
                            Map.entry(0.9, 7.04),
                            Map.entry(0.8, 8.63),
                            Map.entry(0.7, 9.93),
                            Map.entry(0.6, 11.13),
                            Map.entry(0.5, 12.34),
                            Map.entry(0.4, 13.64),
                            Map.entry(0.3, 15.12)
                    )),
            Map.entry(
                    14, Map.ofEntries(
                            Map.entry(0.99, 4.66),
                            Map.entry(0.975, 5.63),
                            Map.entry(0.950, 6.57),
                            Map.entry(0.9, 7.79),
                            Map.entry(0.8, 9.47),
                            Map.entry(0.7, 10.82),
                            Map.entry(0.6, 12.08),
                            Map.entry(0.5, 13.34),
                            Map.entry(0.4, 14.69),
                            Map.entry(0.3, 16.22)
                    )),
            Map.entry(
                    15, Map.ofEntries(
                            Map.entry(0.99, 5.23),
                            Map.entry(0.975, 6.26),
                            Map.entry(0.950, 7.26),
                            Map.entry(0.9, 8.55),
                            Map.entry(0.8, 10.31),
                            Map.entry(0.7, 11.72),
                            Map.entry(0.6, 13.03),
                            Map.entry(0.5, 14.34),
                            Map.entry(0.4, 15.73),
                            Map.entry(0.3, 17.32)
                    )),
            Map.entry(
                    16, Map.ofEntries(
                            Map.entry(0.99, 5.81),
                            Map.entry(0.975, 6.91),
                            Map.entry(0.950, 7.96),
                            Map.entry(0.9, 9.31),
                            Map.entry(0.8, 11.15),
                            Map.entry(0.7, 12.62),
                            Map.entry(0.6, 13.98),
                            Map.entry(0.5, 15.34),
                            Map.entry(0.4, 16.78),
                            Map.entry(0.3, 18.42)
                    )),
            Map.entry(
                    17, Map.ofEntries(
                            Map.entry(0.99, 6.41),
                            Map.entry(0.975, 7.56),
                            Map.entry(0.950, 8.67),
                            Map.entry(0.9, 10.09),
                            Map.entry(0.8, 12.0),
                            Map.entry(0.7, 13.53),
                            Map.entry(0.6, 14.94),
                            Map.entry(0.5, 16.34),
                            Map.entry(0.4, 17.82),
                            Map.entry(0.3, 19.51)
                    )),
            Map.entry(
                    18, Map.ofEntries(
                            Map.entry(0.99, 7.01),
                            Map.entry(0.975, 8.23),
                            Map.entry(0.950, 9.39),
                            Map.entry(0.9, 10.86),
                            Map.entry(0.8, 12.86),
                            Map.entry(0.7, 14.44),
                            Map.entry(0.6, 15.89),
                            Map.entry(0.5, 17.34),
                            Map.entry(0.4, 18.87),
                            Map.entry(0.3, 20.6)
                    )),
            Map.entry(
                    19, Map.ofEntries(
                            Map.entry(0.99, 7.63),
                            Map.entry(0.975, 8.91),
                            Map.entry(0.950, 10.12),
                            Map.entry(0.9, 11.65),
                            Map.entry(0.8, 13.72),
                            Map.entry(0.7, 15.35),
                            Map.entry(0.6, 16.85),
                            Map.entry(0.5, 18.34),
                            Map.entry(0.4, 19.91),
                            Map.entry(0.3, 21.69)
                    )),
            Map.entry(
                    20, Map.ofEntries(
                            Map.entry(0.99, 8.26),
                            Map.entry(0.975, 9.59),
                            Map.entry(0.950, 10.85),
                            Map.entry(0.9, 12.44),
                            Map.entry(0.8, 14.58),
                            Map.entry(0.7, 16.27),
                            Map.entry(0.6, 17.81),
                            Map.entry(0.5, 19.34),
                            Map.entry(0.4, 20.95),
                            Map.entry(0.3, 22.77)
                    )),
            Map.entry(
                    25, Map.ofEntries(
                            Map.entry(0.99, 11.52),
                            Map.entry(0.975, 13.12),
                            Map.entry(0.950, 14.61),
                            Map.entry(0.9, 16.47),
                            Map.entry(0.8, 18.94),
                            Map.entry(0.7, 20.87),
                            Map.entry(0.6, 22.62),
                            Map.entry(0.5, 24.34),
                            Map.entry(0.4, 26.14),
                            Map.entry(0.3, 28.17)
                    )),
            Map.entry(
                    50, Map.ofEntries(
                            Map.entry(0.99, 29.71),
                            Map.entry(0.975, 32.36),
                            Map.entry(0.950, 34.76),
                            Map.entry(0.9, 37.69),
                            Map.entry(0.8, 41.45),
                            Map.entry(0.7, 44.31),
                            Map.entry(0.6, 46.86),
                            Map.entry(0.5, 49.33),
                            Map.entry(0.4, 51.89),
                            Map.entry(0.3, 54.72)
                    )),
            Map.entry(
                    100, Map.ofEntries(
                            Map.entry(0.99, 70.06),
                            Map.entry(0.975, 74.22),
                            Map.entry(0.950, 77.93),
                            Map.entry(0.9, 82.36),
                            Map.entry(0.8, 87.95),
                            Map.entry(0.7, 92.13),
                            Map.entry(0.6, 95.81),
                            Map.entry(0.5, 99.33),
                            Map.entry(0.4, 102.95),
                            Map.entry(0.3, 106.91)
                    ))
    );

    public static double getPirsonCriticalValue(int r, double beta) {
        Map<Double, Double> string = TABLE.get(r);
        return string.get(beta);
    }
}
