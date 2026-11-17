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
                showCandidates(table, true, analyzer.columnMatrix(table, stats), analyzer, stats, in);
            } else {
                showDoubleCandidates(table, originalRowMajor, stats, in);
            }
        }
    }

    private static void showCandidates(CipherTable table, boolean rowMajor, double[][] matrix,
                                       PermutationAnalyzer analyzer, BigramStatistics stats, Scanner in) {
        analyzer.printMatrix(matrix);
        List<PermutationAnalyzer.Candidate> list = analyzer.best(matrix, 20);
        for (int i = 0; i < list.size(); i++) {
            var item = list.get(i);
            System.out.printf("%d) Порядок %s, оценка %.3f: %s%n", i + 1,
                    PermutationAnalyzer.order(item.order()), item.score(),
                    table.columns(item.order()).read(rowMajor).replace('_', ' '));
        }
        while (true) {
            System.out.println("\n1. Расчёт оценки кандидата");
            System.out.println("2. Расчёт соседства двух столбцов");
            System.out.println("3. Проверить свой порядок столбцов");
            System.out.println("0. Вернуться в меню");
            int action = ask(in, "Выбор: ", 0, 3);
            if (action == 0) return;
            if (action == 1) {
                int n = ask(in, "Номер кандидата: ", 1, list.size());
                var candidate = list.get(n - 1);
                ScoreExplanation.columnOrder(table, stats, candidate.order());
            } else if (action == 2) {
                int left = ask(in, "Первый столбец: ", 1, table.cols());
                int right = ask(in, "Следующий столбец: ", 1, table.cols());
                if (left == right) System.out.println("Нужно выбрать два разных столбца.");
                else ScoreExplanation.columnPair(table, stats, left - 1, right - 1);
            } else {
                try {
                    int[] order = readOrder(in, "Порядок столбцов: ", table.cols());
                    double score = 0;
                    for (int i = 1; i < order.length; i++) score += matrix[order[i - 1]][order[i]];
                    System.out.printf("Порядок %s, оценка %.3f: %s%n", PermutationAnalyzer.order(order),
                            score, table.columns(order).read(rowMajor).replace('_', ' '));
                    System.out.print("Показать подробный расчёт? (д/н): ");
                    if (yes(in.nextLine())) ScoreExplanation.columnOrder(table, stats, order);
                } catch (IllegalArgumentException ex) {
                    System.out.println("Неправильный порядок: " + ex.getMessage());
                }
            }
        }
    }

    private static void showDoubleCandidates(CipherTable table, boolean originalRowMajor,
                                             BigramStatistics stats, Scanner in) {
        DoublePermutationAnalyzer analyzer = new DoublePermutationAnalyzer();
        long count = analyzer.combinations(table.rows(), table.cols());
        System.out.println("Возможных сочетаний перестановок: " + count);
        List<DoublePermutationAnalyzer.Result> results = List.of();
        if (count <= DoublePermutationAnalyzer.MAX_COMBINATIONS) {
            results = analyzer.analyze(table, stats, originalRowMajor, 20);
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
        while (true) {
            System.out.println("\n1. Расчёт оценки кандидата");
            System.out.println("2. Проверить свой порядок строк и столбцов");
            System.out.println("0. Вернуться в меню");
            int action = ask(in, "Выбор: ", 0, 2);
            if (action == 0) return;
            if (action == 1) {
                if (results.isEmpty()) {
                    System.out.println("Список кандидатов пуст.");
                    continue;
                }
                int n = ask(in, "Номер кандидата: ", 1, results.size());
                var result = results.get(n - 1);
                System.out.println("Строки: " + PermutationAnalyzer.order(result.rows()));
                System.out.println("Столбцы: " + PermutationAnalyzer.order(result.columns()));
                ScoreExplanation.text(result.text(), stats);
            } else {
                try {
                    int[] rowOrder = readOrder(in, "Порядок строк: ", table.rows());
                    int[] colOrder = readOrder(in, "Порядок столбцов: ", table.cols());
                    String restored = table.rows(rowOrder).columns(colOrder).read(originalRowMajor);
                    System.out.printf(java.util.Locale.forLanguageTag("ru-RU"), "Оценка %.3f: %s%n",
                            analyzer.score(restored, stats), restored.replace('_', ' '));
                    System.out.print("Показать подробный расчёт? (д/н): ");
                    if (yes(in.nextLine())) ScoreExplanation.text(restored, stats);
                } catch (IllegalArgumentException ex) {
                    System.out.println("Неверная перестановка: " + ex.getMessage());
                }
            }
        }
    }

    private static boolean yes(String answer) {
        return answer.trim().equalsIgnoreCase("д");
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
