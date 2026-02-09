package com.IJackDaniel;

import java.util.Arrays;

public class UniversalGenerator {
    private static int K = 3;
    // Вспомогательные массивы
    private static int[] _A = new int[] {171, 172, 170};
    private static int[] _B = new int[] {177, 176, 178};
    private static int[] _C = new int[] {2, 35, 63};
    private static int[] _D = new int[] {30269, 30307, 30323};

    private static int[] _Y = new int[] {1, 1, 1};

    public static double rnd() {
        double s = 0.0;
        double[] x = new double[K];
        int[] yN = new int[K];

        for (int i = 0; i < K; i++) {
            yN[i] = (int) Math.abs(_A[i] * (_Y[i] % _B[i]) - _C[i] * (double) _Y[i] / _B[i]);
            _Y[i] = yN[i];
            x[i] = (double) yN[i] / _D[i];
            s += x[i];
        }

        return Math.abs(s - Math.floor(s));
    }
}
