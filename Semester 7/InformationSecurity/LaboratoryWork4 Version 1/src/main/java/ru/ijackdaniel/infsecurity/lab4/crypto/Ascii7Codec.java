package ru.ijackdaniel.infsecurity.lab4.crypto;

public final class Ascii7Codec {
    private static final int MAX_ASCII_CODE = 127;

    private Ascii7Codec() {
    }

    public static byte[] encode(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Текст не задан");
        }

        byte[] result = new byte[text.length()];

        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);

            if (symbol > MAX_ASCII_CODE) {
                throw new IllegalArgumentException(
                        "Доступен только ASCII (0..127). Недопустимый символ на позиции " + (i + 1)
                );
            }

            result[i] = (byte) symbol;
        }

        return result;
    }

    public static String decode(byte[] data) {
        StringBuilder text = new StringBuilder(data.length);

        for (byte value : data) {
            // byte в Java знаковый, поэтому получаем код символа как число 0..255.
            int code = value & 0xFF;

            if (code > MAX_ASCII_CODE) {
                throw new IllegalArgumentException("HEX содержит значение вне 7-битного диапазона");
            }

            text.append((char) code);
        }

        return text.toString();
    }
}
