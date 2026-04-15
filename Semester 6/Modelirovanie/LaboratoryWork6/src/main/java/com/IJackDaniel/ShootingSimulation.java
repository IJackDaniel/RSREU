// файл: ShootingSimulation.java
package com.IJackDaniel;

import com.IJackDaniel.basicClasses.UniversalGenerator;

public class ShootingSimulation {
    private final int k;           // количество баков
    private final double[] p;      // вероятности попадания в каждый бак (сумма = 1)

    public ShootingSimulation(int k, double[] p) {
        if (k != p.length) {
            throw new IllegalArgumentException("Количество баков k должно совпадать с длиной массива p");
        }
        if (k < 3) {
            throw new IllegalArgumentException("k должно быть > 2 (по условию задачи)");
        }
        this.k = k;
        this.p = p.clone();
    }

    public boolean isIgnition(int hit1, int hit2) {
        // Условие 1: два попадания в один и тот же бак
        if (hit1 == hit2) {
            return true;
        }

        // Условие 2: два попадания в соседние баки (разность индексов = 1)
        if (Math.abs(hit1 - hit2) == 1) {
            return true;
        }

        return false;
    }

    public boolean runExperiment() {
        int hit1 = shoot();
        int hit2 = shoot();
        return isIgnition(hit1, hit2);
    }

    private int shoot() {
        double r = UniversalGenerator.rnd();  // случайное число из [0,1)
        double sum = 0.0;
        for (int i = 0; i < k; i++) {
            sum += p[i];
            if (r <= sum) {
                return i;
            }
        }
        return k - 1;
    }
}