// файл: ShootingSimulation.java
package com.IJackDaniel;

import com.IJackDaniel.basicClasses.UniversalGenerator;

/**
 * Моделирование стрельбы двумя снарядами по k бакам с горючим.
 * Условие воспламенения: два попадания в один бак или в два соседних бака.
 */
public class ShootingSimulation {
    private final int k;           // количество баков
    private final double[] p;      // вероятности попадания в каждый бак (сумма = 1)

    /**
     * Конструктор
     * @param k количество баков (должно быть > 2)
     * @param p массив вероятностей попадания в каждый бак
     */
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

    /**
     * Проверяет, произошло ли воспламенение при двух выстрелах
     * @param hit1 бак, в который попал первый снаряд (от 0 до k-1)
     * @param hit2 бак, в который попал второй снаряд (от 0 до k-1)
     * @return true - воспламенение, false - нет
     */
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

    /**
     * Генерирует один эксперимент (два выстрела) и возвращает результат
     * @return true - воспламенение произошло, false - нет
     */
    public boolean runExperiment() {
        int hit1 = shoot();
        int hit2 = shoot();
        return isIgnition(hit1, hit2);
    }

    /**
     * Один выстрел: возвращает индекс бака (0..k-1) согласно распределению p
     * Используется метод обратной функции распределения
     */
    private int shoot() {
        double r = UniversalGenerator.rnd();  // случайное число из [0,1)
        double sum = 0.0;
        for (int i = 0; i < k; i++) {
            sum += p[i];
            if (r <= sum) {
                return i;
            }
        }
        return k - 1; // fallback (не должен сработать при корректных p, сумма которых = 1)
    }
}