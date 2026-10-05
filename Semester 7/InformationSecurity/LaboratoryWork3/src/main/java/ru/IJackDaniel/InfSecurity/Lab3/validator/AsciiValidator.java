package ru.IJackDaniel.InfSecurity.Lab3.validator;

public class AsciiValidator {
    public void validate(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Введите текст для обработки.");
        }

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) > 127) {
                throw new IllegalArgumentException(
                        "Текст содержит символы вне стандартного ASCII. Подробнее см. «Справка»."
                );
            }
        }
    }
}
