package ru.ijackdaniel.infsecurity.pr4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class BigramStatistics {
    private static final String VOWELS = "АЕИОУЫЭЮЯ";
    private static final String CONSONANTS = "БВГДЖЗЙКЛМНПРСТФХЦЧШЩ";
    private static final double SMOOTHING = 0.3;
    private final Map<String, Integer> frequency = new HashMap<>();
    private final Map<Character, Integer> rowTotal = new HashMap<>();

    public BigramStatistics() throws IOException {
        InputStream stream = BigramStatistics.class.getResourceAsStream("/bigrams.csv");
        if (stream == null) {
            Path path = Path.of("src", "main", "resources", "bigrams.csv");
            if (Files.isRegularFile(path)) stream = Files.newInputStream(path);
        }
        if (stream == null) throw new IOException("Не найден bigrams.csv. Проверьте Resources Root.");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank() || line.startsWith("first,")) continue;
                String[] p = line.split(",", -1);
                if (p.length != 3) throw new IOException("Неправильная строка CSV: " + line);
                int count = Integer.parseInt(p[2].trim());
                if (count < 0) throw new IOException("Отрицательная частота: " + line);
                char left = p[0].trim().charAt(0);
                char right = p[1].trim().charAt(0);
                frequency.put("" + left + right, count);
                rowTotal.merge(left, count, Integer::sum);
            }
        }
    }

    // Оценка перехода между буквами. Неизвестные биграммы не приравниваем к нулю.
    public double score(char left, char right) {
        left = Character.toUpperCase(left);
        right = Character.toUpperCase(right);
        if (left == ' ') left = '_';
        if (right == ' ') right = '_';
        if (left == '_' && right == '_') return 0.000001;
        if (left == '_' || right == '_') return spaceProbability(left, right);
        if (left == 'Ё') left = 'Е';
        if (right == 'Ё') right = 'Е';
        if (left == 'Ъ') left = 'Ь';
        if (right == 'Ъ') right = 'Ь';
        int count = frequency.getOrDefault("" + left + right, 0);
        int total = rowTotal.getOrDefault(left, 0);
        // Условные частоты P(следующая буква | текущая буква).
        return (count + SMOOTHING) / (total + SMOOTHING * 32.0);
    }

    private static double spaceProbability(char left, char right) {
        if (left == '_') {
            if ("ВСКУОАИЕПНДЗТЯМРБ".indexOf(right) >= 0) return 0.12;
            if (CONSONANTS.indexOf(right) >= 0 || VOWELS.indexOf(right) >= 0) return 0.015;
            return 0.001;
        }
        if ("ЕИАОНТСРЬЫЮМХЛВЙКУ".indexOf(left) >= 0) return 0.09;
        if (CONSONANTS.indexOf(left) >= 0 || VOWELS.indexOf(left) >= 0) return 0.015;
        return 0.001;
    }

    public int entries() { return frequency.size(); }
}
