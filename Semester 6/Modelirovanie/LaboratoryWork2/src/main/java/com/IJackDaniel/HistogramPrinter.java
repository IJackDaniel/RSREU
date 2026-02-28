package com.IJackDaniel;

public class HistogramPrinter {
    // Максимальная длина столбца в символах
    private static final int MAX_BAR_LENGTH = 50;
    private static final int MAX_LABEL_LENGTH = 20;
    private static final String SEPARATOR_STRING = ("=".repeat(MAX_BAR_LENGTH + MAX_LABEL_LENGTH)) + "\n";

    // Функция отрисовки гистограммы
    public static void printHistogram(String[] labels, int[] values) {
        // Проверка пустых массивов
        if (labels == null || values == null || labels.length == 0 || values.length == 0) {
            System.out.println("Массивы пусты");
            return;
        }

        // Проверка длин массивов
        if (labels.length != values.length) {
            System.out.println("Массивы разной длины");
            return;
        }

        // Поиск максимального значения (для масштабирования столбцов)
        int maxValue = 0;
        for (int value : values) {
            if (value > maxValue) {
                maxValue = value;
            }
        }

        // Поиск максимальной длины подписи (но не более 20 символов)
        int maxLabelLength = 0;
        for (String label : labels) {
            if (label != null && label.length() > maxLabelLength) {
                maxLabelLength = label.length();
            }
        }
        maxLabelLength = Math.min(maxLabelLength, MAX_LABEL_LENGTH);

        System.out.println("Гистограмма");
        System.out.println(SEPARATOR_STRING);

        for (int i = 0; i < values.length; i++) {
            // Вычисление длины столбца с учётом масштабирования
            int barLength = (maxValue == 0) ? 0 : (int) ((double) values[i] / maxValue * MAX_BAR_LENGTH);

            // Обработка подписи
            String label = labels[i];
            if (label == null) label = "";
            if (label.length() > maxLabelLength) {
                label = label.substring(0, maxLabelLength - 3) + "...";
            }

            // Форматирование подписи
            String formattedLabel = String.format("%-" + maxLabelLength + "s", label);

            // Формирование столбца гистограммы
            StringBuilder bar = new StringBuilder();
            for (int j = 0; j < barLength; j++) {
                bar.append("█");
            }

            // Вставка значения в столбец
            String valueStr = String.valueOf(values[i]);
            if (barLength > 0) {
                // Если столбец достаточно длинный, добавляем значение внутри него
                if (barLength > valueStr.length() + 2) {
                    bar.insert(barLength - valueStr.length(), valueStr);
                }
            }

            System.out.printf("%s | %s\n", formattedLabel, bar.toString());
        }
        System.out.println("\n" + SEPARATOR_STRING);
    }
}
