package ru.ijackdaniel.infsecurity.lab4.crypto;

import java.util.HexFormat;

public final class HexCodec {
    private HexCodec() {
    }

    public static String encode(byte[] bytes) {
        return HexFormat.of().withUpperCase().formatHex(bytes);
    }

    public static byte[] decode(String text) {
        if (text == null) {
            throw new IllegalArgumentException("HEX не задан");
        }

        // Пробелы и переводы строк допускаются между HEX-байтами.
        String cleaned = text.replaceAll("\\s", "");

        if ((cleaned.length() & 1) != 0) {
            throw new IllegalArgumentException("HEX должен содержать чётное число цифр");
        }

        try {
            return HexFormat.of().parseHex(cleaned);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Неверный HEX: используйте цифры 0–9 и A–F", ex);
        }
    }
}
