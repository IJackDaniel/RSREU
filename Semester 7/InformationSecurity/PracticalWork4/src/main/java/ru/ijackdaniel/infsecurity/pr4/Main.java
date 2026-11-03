package ru.ijackdaniel.infsecurity.pr4;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public final class Main {
    private static final String[] VARIANTS = {
            "ОКЕСНВРП_ЫРЕАДЕЫН_В_РСИКО", "ДСЛИЕЗТЕА_Д_ЛЬЮВМИ__АОЧХК",
            "НМВИАИ_НЕВЕ_СМСТУОРДИАНКМ", "ЕДСЗЬНДЕ_МУБД_УЭ_КТЗЕМНАЫ",
            "СОНРЧОУО_ХДТ_ИЕИ_ВЗКАТРРИ", "_ОНКА_БНЫЕЦВЛЕ_К_ТГОАНЕИР",
            "НЗМАЕЕАА_Г_НОТВОССОТЬЯАЛС", "РППОЕААДТВЛ_ЕБЬЛНЫЕ_ПА_ВР",
            "ОПЗДЕП_ИХРДОТ_И_ВРИТЧ_САА", "ВКЫОСИРЙУ_ОЬВНЕ_СОАПНИОТС",
            "ПКТИРАОЛНАОИЧ_З_ЕСЬНЕЛНЖО", "ИПКСОЕ_ТСМНАЧИ_ОЕН_ГДЕЛА_",
            "АМВИННЬТЛЕАНЕ_ЙОВ_ОПХАРТО", "АРЫКЗЫ_КЙТНЛ_ААЫ_ОЛБКЫТРТ",
            "_ПАРИИВИАРЗ_БРА_ИСТЬЛТОЕК", "А_ЛНПАУВКЭИ_ЦИАКР_ОВОЕДРИ",
            "ЖВНОАН_АТЗОЬСН_ЫО_ФВИИКИЗ", "ОТВГОСЕЬЬТАДВ_С_ЬЗАТТЕЫАЧ",
            "ЯАМРИТ_ДЖЕХ_СВЕД_ТСУВЕТНО", "УЬБДТ_ОЕГТВ_ОЫКЭА_ВКАИУЦИ",
            "ЛТБЕИЛЖЫЕ__ОАПТЖРДУ_ЛМНОА", "ИПТКРРАФОГ_ВАИЯАЯ_ЖНУКАНА",
            "ПКЕЕРРПО_ЙУОТ_ИТПСУТЛЯЕИН", "ИЬЖЗНСД_ТУН_ЕТАНУВЕ_РЫГОЗ",
            "ЕОУРВА_НЬРИАДИЦЕПИ_РНШВЫЕ"
    };

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in, "UTF-8");
        BigramStatistics stats = new BigramStatistics();
        PermutationAnalyzer analyzer = new PermutationAnalyzer();
        System.out.println("ПР4. Анализ табличной перестановки");
        System.out.println("Загружено частот биграмм: " + stats.entries());
        System.out.println("Неуказанные биграммы и пробелы оцениваются приближённо.");
        while (true) {
            System.out.println("\n1. Столбцовая перестановка");
            System.out.println("2. Двойная перестановка");
            System.out.println("0. Выход");
            int task = ask(in, "Выбор: ", 0, 2);
            if (task == 0) return;
            String text;
            System.out.print("Криптограмма (Enter для выбора из таблицы вариантов): ");
            String typed = in.nextLine();
            if (typed.isBlank()) {
                int variant = ask(in, "Номер варианта (1..25): ", 1, 25);
                text = VARIANTS[variant - 1];
            } else text = typed.toUpperCase(java.util.Locale.forLanguageTag("ru-RU")).replace(' ', '_');
            System.out.println("Длина: " + text.length());
            int rows = ask(in, "Строк: ", 1, 10);
            int cols = ask(in, "Столбцов: ", 1, 10);
            if ((long) rows * cols != text.length()) {
                System.out.println("Нужно ровно " + text.length() + " ячеек. Дополнение пробелами отключено.");
                continue;
            }
            boolean originalRowMajor = true;
            if (task == 2) originalRowMajor = ask(in, "Исходная запись: 1 — по строкам, 2 — по столбцам: ", 1, 2) == 1;
            boolean ciphertextRowMajor = ask(in, "Криптограмма считана: 1 — по строкам, 2 — по столбцам: ", 1, 2) == 1;
            CipherTable table = new CipherTable(text, rows, cols, ciphertextRowMajor);
            System.out.println("Исходная таблица:");
            table.print();
            if (task == 1) {
                showCandidates(table, true, analyzer.columnMatrix(table, stats), analyzer, in);
            } else {
                DoublePermutationAnalyzer doubleAnalyzer = new DoublePermutationAnalyzer();
                long count = doubleAnalyzer.combinations(rows, cols);
                System.out.println("Возможных сочетаний перестановок: " + count);
                if (count <= DoublePermutationAnalyzer.MAX_COMBINATIONS) {
                    var results = doubleAnalyzer.analyze(table, stats, originalRowMajor, 20);
                    for (int i = 0; i < results.size(); i++) {
                        var result = results.get(i);
                        System.out.printf(java.util.Locale.forLanguageTag("ru-RU"),
                                "%d) Строки %s, столбцы %s, оценка %.3f: %s%n",
                                i + 1, PermutationAnalyzer.order(result.rows()),
                                PermutationAnalyzer.order(result.columns()), result.score(),
                                result.text().replace('_', ' '));
                    }
                } else {
                    System.out.println("Полный перебор ограничен " + DoublePermutationAnalyzer.MAX_COMBINATIONS
                            + " комбинаций. Укажите меньшие размеры таблицы.");
                }
                System.out.print("Проверить свой порядок строк и столбцов? (д/н): ");
                if (in.nextLine().trim().equalsIgnoreCase("д")) {
                    try {
                        int[] rowOrder = readOrder(in, "Порядок строк: ", rows);
                        int[] colOrder = readOrder(in, "Порядок столбцов: ", cols);
                        String restored = table.rows(rowOrder).columns(colOrder).read(originalRowMajor);
                        System.out.printf(java.util.Locale.forLanguageTag("ru-RU"),
                                "Оценка %.3f: %s%n", doubleAnalyzer.score(restored, stats),
                                restored.replace('_', ' '));
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Неверная перестановка: " + ex.getMessage());
                    }
                }
            }
        }
    }

    private static void showCandidates(CipherTable table, boolean rowMajor, double[][] matrix, PermutationAnalyzer analyzer, Scanner in) {
        analyzer.printMatrix(matrix);
        List<PermutationAnalyzer.Candidate> list = analyzer.best(matrix, 20);
        for (int i = 0; i < list.size(); i++) {
            var item = list.get(i);
            System.out.printf("%d) Порядок %s, оценка %.3f: %s%n", i + 1,
                    PermutationAnalyzer.order(item.order()), item.score(),
                    table.columns(item.order()).read(rowMajor).replace('_', ' '));
        }
        System.out.print("Свой порядок столбцов (например, 2 3 5 1 4; Enter для пропуска): ");
        String typed = in.nextLine().trim();
        if (!typed.isEmpty()) {
            try {
                String[] parts = typed.replace(',', ' ').trim().split("\\s+");
                int[] order = new int[parts.length];
                if (order.length != table.cols()) throw new IllegalArgumentException("Нужно указать все столбцы");
                boolean[] used = new boolean[order.length];
                for (int i = 0; i < order.length; i++) {
                    order[i] = Integer.parseInt(parts[i]) - 1;
                    if (order[i] < 0 || order[i] >= order.length || used[order[i]]) {
                        throw new IllegalArgumentException("Номера должны быть от 1 до " + order.length + " без повторений");
                    }
                    used[order[i]] = true;
                }
                double score = 0;
                for (int i = 1; i < order.length; i++) score += matrix[order[i - 1]][order[i]];
                System.out.printf("Порядок %s, оценка %.3f: %s%n", PermutationAnalyzer.order(order),
                        score, table.columns(order).read(rowMajor).replace('_', ' '));
            } catch (IllegalArgumentException ex) {
                System.out.println("Неправильный порядок: " + ex.getMessage());
            }
        }
    }

    private static int[] readOrder(Scanner in, String prompt, int n) {
        System.out.print(prompt);
        String[] parts = in.nextLine().trim().replace(',', ' ').split("\\s+");
        if (parts.length != n) throw new IllegalArgumentException("Требуется " + n + " номеров");
        int[] result = new int[n];
        boolean[] used = new boolean[n];
        for (int i = 0; i < n; i++) {
            int value = Integer.parseInt(parts[i]) - 1;
            if (value < 0 || value >= n || used[value]) throw new IllegalArgumentException("Номера должны быть от 1 до " + n + " без повторов");
            used[value] = true;
            result[i] = value;
        }
        return result;
    }

    private static int ask(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int result = Integer.parseInt(scanner.nextLine().trim());
                if (result >= min && result <= max) return result;
            } catch (NumberFormatException ignored) { }
            System.out.println("Введите целое число от " + min + " до " + max + ".");
        }
    }
}
