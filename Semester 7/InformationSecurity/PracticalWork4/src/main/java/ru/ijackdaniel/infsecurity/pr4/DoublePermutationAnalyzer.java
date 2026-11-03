package ru.ijackdaniel.infsecurity.pr4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public final class DoublePermutationAnalyzer {
    public static final long MAX_COMBINATIONS = 1_000_000_000;

    public record Result(int[] rows, int[] columns, double score, String text) {
        public Result { rows = rows.clone(); columns = columns.clone(); }
        @Override public int[] rows() { return rows.clone(); }
        @Override public int[] columns() { return columns.clone(); }
    }

    public long combinations(int rows, int cols) {
        long a = factorial(rows);
        long b = factorial(cols);
        if (a > Long.MAX_VALUE / b) return Long.MAX_VALUE;
        return a * b;
    }

    private long factorial(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            if (result > Long.MAX_VALUE / i) return Long.MAX_VALUE;
            result *= i;
        }
        return result;
    }

    public List<Result> analyze(CipherTable table, BigramStatistics stats, boolean originalRowMajor, int limit) {
        long count = combinations(table.rows(), table.cols());
        if (count > MAX_COMBINATIONS) {
            throw new IllegalArgumentException("Полный перебор слишком велик: " + count + " комбинаций (лимит " + MAX_COMBINATIONS + ")");
        }
        PriorityQueue<Result> best = new PriorityQueue<>(Comparator.comparingDouble(Result::score));
        int[] rowOrder = identity(table.rows());
        do {
            int[] columnOrder = identity(table.cols());
            do {
                CipherTable restored = table.rows(rowOrder).columns(columnOrder);
                String text = restored.read(originalRowMajor);
                double score = score(text, stats);
                Result result = new Result(rowOrder, columnOrder, score, text);
                if (best.size() < limit) best.add(result);
                else if (score > best.peek().score()) {
                    best.poll();
                    best.add(result);
                }
            } while (next(columnOrder));
        } while (next(rowOrder));
        List<Result> results = new ArrayList<>(best);
        results.sort(Comparator.comparingDouble(Result::score).reversed());
        return results;
    }

    public double score(String text, BigramStatistics stats) {
        double sum = 0;
        for (int i = 1; i < text.length(); i++) {
            sum += Math.log(stats.score(text.charAt(i - 1), text.charAt(i)));
        }
        return sum;
    }

    private static int[] identity(int size) {
        int[] p = new int[size];
        for (int i = 0; i < size; i++) p[i] = i;
        return p;
    }

    private static boolean next(int[] p) {
        int i = p.length - 2;
        while (i >= 0 && p[i] >= p[i + 1]) i--;
        if (i < 0) return false;
        int j = p.length - 1;
        while (p[j] <= p[i]) j--;
        int tmp = p[i]; p[i] = p[j]; p[j] = tmp;
        for (int a = i + 1, b = p.length - 1; a < b; a++, b--) {
            tmp = p[a]; p[a] = p[b]; p[b] = tmp;
        }
        return true;
    }
}
