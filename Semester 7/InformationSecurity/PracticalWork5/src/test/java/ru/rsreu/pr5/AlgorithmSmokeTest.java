package ru.rsreu.pr5;

import ru.rsreu.pr5.service.CryptoAnalyzer;
import ru.rsreu.pr5.service.MappingService;

import java.util.List;
import java.util.Map;

/** Простые проверки алгоритма без сторонних тестовых библиотек. */
public class AlgorithmSmokeTest {
    public static void main(String[] args) {
        check(CryptoAnalyzer.symbols("42 123 42, 17").equals(List.of("42", "42", "17")), "symbols");
        check(CryptoAnalyzer.counts("42 17 42").equals(Map.of("42", 2L, "17", 1L)), "counts");
        check(CryptoAnalyzer.ngrams("42 17 42 17", 2).get("42 17") == 2L, "ngrams");
        check(CryptoAnalyzer.decrypt("39 25 20, 34 82 63", Map.of(
                "39", 'В', "25", 'Н', "20", 'А', "34", 'Ш', "82", 'Е', "63", 'Й'))
                .equals("ВНА,ШЕЙ"), "decrypt");
        check(!CryptoAnalyzer.suggestedMappings("42 17 42", false).containsValue(' '), "без пробела");

        MappingService service = new MappingService();
        service.set("42", 'А');
        service.set("17", 'Б');
        service.set("17", 'А');
        check(!service.view().containsKey("42"), "уникальность замен");
        check(service.undo() && service.view().get("42") == 'А', "undo");
        service.swap("42", "17");
        check(service.view().get("42") == 'Б' && service.view().get("17") == 'А', "swap");
        System.out.println("Алгоритмические проверки пройдены");
    }

    private static void check(boolean condition, String name) {
        if (!condition) throw new AssertionError("Ошибка: " + name);
    }
}
