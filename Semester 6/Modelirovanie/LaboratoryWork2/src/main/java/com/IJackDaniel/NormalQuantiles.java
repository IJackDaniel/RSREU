package com.IJackDaniel;

import java.util.Map;

public class NormalQuantiles {
    private static final Map<Double, Double> quantileOfStandardNormalDistribution = Map.ofEntries(
            Map.entry(0.1, 1.65),
            Map.entry(0.05, 1.96),
            Map.entry(0.04, 2.06),
            Map.entry(0.03, 2.18),
            Map.entry(0.02, 2.33),
            Map.entry(0.01, 2.58),
            Map.entry(0.001, 3.3)
    );

    public static double getTB(double alpha) {
        return quantileOfStandardNormalDistribution.get(alpha);
    }
}
