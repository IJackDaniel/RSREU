package com.IJackDaniel;


public class HistogramPrinter {

    // Параметры отображения гистограммы
    private static final int MAX_BAR_LENGTH = 50;      // Максимальная длина столбца в символах
    private static final String BAR_CHAR = "█";        // Символ для заполнения столбца
    private static final String EMPTY_CHAR = "░";      // Символ для пустой части столбца
    private static final int LEFT_MARGIN = 18;         // Отступ слева для подписи интервала
    private static final int VALUE_WIDTH = 10;         // Ширина для отображения значения частоты


    @FunctionalInterface
    public interface IntervalLabelGenerator {
        String generateLabel(int index, double leftBound, double rightBound);
    }

    public static class DefaultIntervalLabelGenerator implements IntervalLabelGenerator {
        private final int precision;

        public DefaultIntervalLabelGenerator(int precision) {
            this.precision = precision;
        }

        @Override
        public String generateLabel(int index, double leftBound, double rightBound) {
            String format = "%." + precision + "f";
            return String.format("[" + format + ", " + format + "]", leftBound, rightBound);
        }
    }

    public static void printHistogram(int[] frequencies, double[] boundaries, IntervalLabelGenerator labelGenerator) {
        if (frequencies == null || boundaries == null || frequencies.length == 0) {
            System.out.println("Нет данных для построения гистограммы");
            return;
        }

        int n = frequencies.length;
        int maxFrequency = getMax(frequencies);

        // Выводим заголовки столбцов
        printHeader();

        // Выводим каждый столбец гистограммы
        for (int i = 0; i < n; i++) {
            String label = labelGenerator.generateLabel(i, boundaries[i], boundaries[i + 1]);
            printBar(label, frequencies[i], maxFrequency);
        }
    }

    private static void printBar(String label, int frequency, int maxFrequency) {
        int barLength = maxFrequency == 0 ? 0 : (int) ((double) frequency / maxFrequency * MAX_BAR_LENGTH);

        System.out.printf("%-" + LEFT_MARGIN + "s %" + VALUE_WIDTH + "d ", label, frequency);
        System.out.print(BAR_CHAR.repeat(barLength));

        if (barLength < MAX_BAR_LENGTH) {
            System.out.print(EMPTY_CHAR.repeat(MAX_BAR_LENGTH - barLength));
        }

        System.out.printf(" %d", frequency);
        System.out.println();
    }

    private static void printHeader() {
        System.out.printf("%-" + LEFT_MARGIN + "s %-10s %s\n", "Интервал", "Частота", "Гистограмма");
        System.out.println("-".repeat(LEFT_MARGIN + VALUE_WIDTH + MAX_BAR_LENGTH + 10));
    }

    private static int getMax(int[] arr) {
        int max = arr[0];
        for (int val : arr) {
            if (val > max) max = val;
        }
        return max;
    }

    public static void quickHistogram(int[] frequencies, double[] boundaries) {
        printHistogram(frequencies, boundaries, new DefaultIntervalLabelGenerator(4));
    }
}