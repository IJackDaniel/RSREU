package ru.IJackDaniel.InfSecurity.Lab3.service;

import ru.IJackDaniel.InfSecurity.Lab3.model.PermutationKey;
import ru.IJackDaniel.InfSecurity.Lab3.validator.PermutationKeyValidator;

import java.util.Arrays;

public class KeyParserService {
    private final PermutationKeyValidator validator = new PermutationKeyValidator();

    public PermutationKey parse(String rawKey) {
        if (rawKey == null || rawKey.isBlank()) {
            throw new IllegalArgumentException("Введите ключ перестановки.");
        }

        String normalized = rawKey.trim().replace(',', ' ');
        String[] tokens = normalized.split("\\s+");

        int[] positions;
        try {
            positions = Arrays.stream(tokens)
                    .mapToInt(Integer::parseInt)
                    .toArray();
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Ключ должен содержать только целые числа, разделённые пробелами.");
        }

        validator.validate(positions);
        return new PermutationKey(positions);
    }
}
