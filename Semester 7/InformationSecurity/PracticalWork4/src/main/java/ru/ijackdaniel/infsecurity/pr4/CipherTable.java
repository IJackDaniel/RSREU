package ru.ijackdaniel.infsecurity.pr4;

import java.util.Arrays;

public final class CipherTable {
    private final char[][] values;
    private final int rows;
    private final int cols;

    public CipherTable(String text, int rows, int cols, boolean rowMajor) {
        if (rows < 1 || cols < 1 || (long) rows * cols != text.length()) {
            throw new IllegalArgumentException("Размер таблицы должен точно соответствовать длине криптограммы");
        }
        this.rows = rows;
        this.cols = cols;
        values = new char[rows][cols];
        int k = 0;
        if (rowMajor) {
            for (int r = 0; r < rows; r++) for (int c = 0; c < cols; c++) values[r][c] = text.charAt(k++);
        } else {
            for (int c = 0; c < cols; c++) for (int r = 0; r < rows; r++) values[r][c] = text.charAt(k++);
        }
    }

    private CipherTable(char[][] src) {
        rows = src.length;
        cols = src[0].length;
        values = new char[rows][cols];
        for (int r = 0; r < rows; r++) values[r] = Arrays.copyOf(src[r], cols);
    }

    public int rows() { return rows; }
    public int cols() { return cols; }
    public char get(int r, int c) { return values[r][c]; }

    public CipherTable columns(int[] order) {
        if (order.length != cols) throw new IllegalArgumentException("Неверная длина перестановки столбцов");
        char[][] out = new char[rows][cols];
        for (int r = 0; r < rows; r++) for (int c = 0; c < cols; c++) out[r][c] = values[r][order[c]];
        return new CipherTable(out);
    }

    public CipherTable rows(int[] order) {
        if (order.length != rows) throw new IllegalArgumentException("Неверная длина перестановки строк");
        char[][] out = new char[rows][cols];
        for (int r = 0; r < rows; r++) out[r] = Arrays.copyOf(values[order[r]], cols);
        return new CipherTable(out);
    }

    public String read(boolean rowMajor) {
        StringBuilder sb = new StringBuilder(rows * cols);
        if (rowMajor) {
            for (int r = 0; r < rows; r++) for (int c = 0; c < cols; c++) sb.append(values[r][c]);
        } else {
            for (int c = 0; c < cols; c++) for (int r = 0; r < rows; r++) sb.append(values[r][c]);
        }
        return sb.toString();
    }

    public void print() {
        for (char[] row : values) {
            for (char ch : row) System.out.print(ch + " ");
            System.out.println();
        }
    }
}
