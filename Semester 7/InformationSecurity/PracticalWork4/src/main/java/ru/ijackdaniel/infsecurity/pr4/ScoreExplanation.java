package ru.ijackdaniel.infsecurity.pr4;

import java.util.Locale;

public final class ScoreExplanation {
    private static final Locale NUMBER_LOCALE = Locale.ROOT;

    private ScoreExplanation() { }

    // Оценка одной ячейки матрицы соседства столбцов, по формуле из методички.
    public static double columnPair(CipherTable table, BigramStatistics stats, int left, int right) {
        if (left == right) throw new IllegalArgumentException("Столбцы должны быть разными");
        System.out.printf("Соседство столбцов %d -> %d%n", left + 1, right + 1);
        System.out.println("Строка   Биграмма     p             ln(p)");
        double total = 0;
        for (int r = 0; r < table.rows(); r++) {
            char a = table.get(r, left);
            char b = table.get(r, right);
            total += printBigram(r + 1, a, b, stats);
        }
        System.out.printf(NUMBER_LOCALE, "Сумма логарифмов: %.6f%n", total);
        return total;
    }

    // Сумма оценок соседства всех пар столбцов выбранной перестановки.
    public static double columnOrder(CipherTable table, BigramStatistics stats, int[] order) {
        System.out.println("Порядок столбцов: " + PermutationAnalyzer.order(order));
        double total = 0;
        for (int i = 1; i < order.length; i++) {
            System.out.println();
            total += columnPair(table, stats, order[i - 1], order[i]);
        }
        System.out.printf(NUMBER_LOCALE, "%nОбщая оценка перестановки: %.6f%n", total);
        return total;
    }

    // Общая оценка восстановленного текста по всем соседним символам.
    public static double text(String restored, BigramStatistics stats) {
        System.out.println("Восстановленный текст: " + restored.replace('_', ' '));
        System.out.println("Номер     Биграмма     p             ln(p)");
        double total = 0;
        for (int i = 1; i < restored.length(); i++) {
            total += printBigram(i, restored.charAt(i - 1), restored.charAt(i), stats);
        }
        System.out.printf(NUMBER_LOCALE, "Общая оценка текста: %.6f%n", total);
        return total;
    }

    private static double printBigram(int number, char a, char b, BigramStatistics stats) {
        double probability = stats.score(a, b);
        double log = Math.log(probability);
        System.out.printf(NUMBER_LOCALE, "%5d     %-7s  %12.8f  %12.6f%n",
                number, "" + a + b, probability, log);
        return log;
    }
}
