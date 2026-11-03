package ru.ijackdaniel.infsecurity.pr4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class PermutationAnalyzer {
    public record Candidate(int[] order, double score) {
        public Candidate { order = order.clone(); }
        @Override public int[] order() { return order.clone(); }
    }

    public double[][] columnMatrix(CipherTable table, BigramStatistics stats) {
        int n = table.cols();
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) {
            if (i == j) { result[i][j] = Double.NEGATIVE_INFINITY; continue; }
            double value = 0;
            for (int r = 0; r < table.rows(); r++) {
                value += Math.log(stats.score(table.get(r, i), table.get(r, j)));
            }
            result[i][j] = value;
        }
        return result;
    }

    public double[][] rowMatrix(CipherTable table, BigramStatistics stats) {
        int n = table.rows();
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) {
            result[i][j] = i == j ? Double.NEGATIVE_INFINITY
                    : Math.log(stats.score(table.get(i, table.cols() - 1), table.get(j, 0)));
        }
        return result;
    }

    // Для записи исходного текста по столбцам: граница соседних столбцов.
    public double[][] columnBoundaryMatrix(CipherTable table, BigramStatistics stats) {
        int n = table.cols();
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) {
            result[i][j] = i == j ? Double.NEGATIVE_INFINITY
                    : Math.log(stats.score(table.get(table.rows() - 1, i), table.get(0, j)));
        }
        return result;
    }

    public double[][] rowAdjacencyMatrix(CipherTable table, BigramStatistics stats) {
        int n = table.rows();
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) {
            if (i == j) { result[i][j] = Double.NEGATIVE_INFINITY; continue; }
            double value = 0;
            for (int c = 0; c < table.cols(); c++) value += Math.log(stats.score(table.get(i, c), table.get(j, c)));
            result[i][j] = value;
        }
        return result;
    }

    public List<Candidate> best(double[][] matrix, int limit) {
        int n = matrix.length;
        if (n < 1 || n > 10) throw new IllegalArgumentException("Для точного перебора требуется от 1 до 10 элементов");
        List<Candidate> result = new ArrayList<>();
        int[] p = new int[n];
        for (int i = 0; i < n; i++) p[i] = i;
        do {
            double score = 0;
            for (int i = 1; i < n; i++) score += matrix[p[i - 1]][p[i]];
            if (result.size() < limit || score > result.get(result.size() - 1).score()) {
                result.add(new Candidate(p, score));
                result.sort(Comparator.comparingDouble(Candidate::score).reversed());
                if (result.size() > limit) result.remove(result.size() - 1);
            }
        } while (nextPermutation(p));
        return result;
    }

    private boolean nextPermutation(int[] p) {
        int i = p.length - 2;
        while (i >= 0 && p[i] >= p[i + 1]) i--;
        if (i < 0) return false;
        int j = p.length - 1;
        while (p[j] <= p[i]) j--;
        int t = p[i]; p[i] = p[j]; p[j] = t;
        for (int a = i + 1, b = p.length - 1; a < b; a++, b--) {
            t = p[a]; p[a] = p[b]; p[b] = t;
        }
        return true;
    }

    public void printMatrix(double[][] matrix) {
        System.out.println("Матрица логарифмов условных оценок соседства:");
        System.out.print("      ");
        for (int i = 0; i < matrix.length; i++) System.out.printf("%9d", i + 1);
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("%5d ", i + 1);
            for (int j = 0; j < matrix.length; j++) {
                if (i == j) System.out.printf("%9s", "x");
                else System.out.printf(java.util.Locale.ROOT, "%9.2f", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public static String order(int[] p) {
        return Arrays.toString(Arrays.stream(p).map(x -> x + 1).toArray());
    }
}
