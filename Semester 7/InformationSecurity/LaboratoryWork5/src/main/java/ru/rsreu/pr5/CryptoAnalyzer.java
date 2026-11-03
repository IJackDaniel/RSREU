package ru.rsreu.pr5;

import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

/**
 * Analysis utilities for the two-digit monoalphabetic substitution used in PR5.
 */
public final class CryptoAnalyzer {

    private CryptoAnalyzer() {
    }

    public static final String ALPHABET = " АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЫЬЭЮЯ";

    public static final List<Character> FREQUENCY_ORDER = List.of(
            ' ', 'О', 'Е', 'А', 'И', 'Т', 'Н', 'С', 'Р', 'В', 'Л', 'К', 'М',
            'Д', 'П', 'У', 'Я', 'Ы', 'Ь', 'Б', 'Г', 'Ч', 'Й', 'Х', 'Ж', 'Ю', 'Ш', 'Ц', 'Щ', 'Э', 'Ф', 'З');

    public static final Map<Character, Double> REFERENCE_FREQUENCIES = referenceFrequencies();

    private static Map<Character, Double> referenceFrequencies() {
        Map<Character, Double> result = new LinkedHashMap<>();
        String letters = " ОЕАИТНСРВЛКМДПУЯЫЬБГЧЙХЖЮШЦЩЭФ";
        double[] values = {0.175, 0.089, 0.072, 0.062, 0.062, 0.053, 0.053, 0.045, 0.040, 0.038, 0.035, 0.028, 0.026, 0.025, 0.023, 0.021, 0.018, 0.016, 0.014, 0.014, 0.013, 0.012, 0.010, 0.009, 0.007, 0.006, 0.006, 0.004, 0.003, 0.003, 0.002};
        for (int i = 0; i < letters.length(); i++) result.put(letters.charAt(i), values[i]);
        return Collections.unmodifiableMap(result);
    }

    private static final Pattern TOKEN = Pattern.compile("(?<!\\d)\\d{2}(?!\\d)");

    public static List<String> symbols(String raw) {
        Matcher matcher = TOKEN.matcher(raw);
        List<String> result = new ArrayList<>();
        while (matcher.find()) result.add(matcher.group());
        return result;
    }

    public static Map<String, Long> counts(String raw) {
        Map<String, Long> counts = new HashMap<>();
        for (String code : symbols(raw)) counts.merge(code, 1L, Long::sum);
        return counts.entrySet().stream()
                .sorted(Comparator.<Map.Entry<String, Long>>comparingLong(Map.Entry::getValue).reversed()
                        .thenComparing(Map.Entry::getKey))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (a, b) -> a, LinkedHashMap::new));
    }

    public static Map<String, Character> suggestedMappings(String raw) {
        return suggestedMappings(raw, true);
    }

    public static Map<String, Character> suggestedMappings(String raw, boolean includeSpace) {
        Map<String, Character> mappings = new LinkedHashMap<>();
        int i = 0;
        for (String code : counts(raw).keySet()) {
            while (i < FREQUENCY_ORDER.size() && !includeSpace && FREQUENCY_ORDER.get(i) == ' ') i++;
            if (i < FREQUENCY_ORDER.size()) mappings.put(code, FREQUENCY_ORDER.get(i++));
        }
        return mappings;
    }

    public static String decrypt(String raw, Map<String, Character> mappings) {
        StringBuilder output = new StringBuilder();
        Matcher m = TOKEN.matcher(raw);
        int previous = 0;
        while (m.find()) {
            String between = raw.substring(previous, m.start());

            output.append(between.replaceAll("\\s+", ""));
            Character letter = mappings.get(m.group());
            output.append(letter == null ? '·' : letter);
            previous = m.end();
        }
        output.append(raw.substring(previous).replaceAll("\\s+", ""));
        return output.toString();
    }

    public static Map<String, Long> ngrams(String raw, int n) {
        List<String> codes = symbols(raw);
        Map<String, Long> count = new HashMap<>();
        for (int i = 0; i + n <= codes.size(); i++) {
            String gram = String.join(" ", codes.subList(i, i + n));
            count.merge(gram, 1L, Long::sum);
        }
        return count.entrySet().stream()
                .sorted(Comparator.<Map.Entry<String, Long>>comparingLong(Map.Entry::getValue).reversed().thenComparing(Map.Entry::getKey))
                .limit(30).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
    }
}
