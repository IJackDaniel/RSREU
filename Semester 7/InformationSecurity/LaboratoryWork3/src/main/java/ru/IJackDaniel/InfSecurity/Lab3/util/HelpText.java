package ru.IJackDaniel.InfSecurity.Lab3.util;

public final class HelpText {
    private HelpText() {
    }

    public static final String CONTENT = """
            Программа работает с символами стандартного ASCII (коды 0-127).

            Поддерживаются:
            • латинские буквы A-Z и a-z;
            • цифры 0-9;
            • пробел;
            • основные знаки пунктуации и специальные символы.

            Кириллица и другие символы Unicode не поддерживаются в соответствии с условием варианта лабораторной работы.
            """;
}
