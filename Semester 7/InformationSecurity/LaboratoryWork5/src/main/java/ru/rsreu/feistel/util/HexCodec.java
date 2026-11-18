package ru.rsreu.feistel.util;

import java.util.HexFormat;

public final class HexCodec {
    private HexCodec() { }

    public static byte[] parseKey(String hex) {
        String value = hex == null ? "" : hex.strip();
        if (!value.matches("(?i)[0-9a-f]{16}")) {
            throw new IllegalArgumentException("Ключ должен состоять ровно из 16 HEX-символов (64 бита).");
        }
        return HexFormat.of().parseHex(value);
    }

    public static String formatBytes(byte[] bytes) {
        return HexFormat.of().withUpperCase().formatHex(bytes);
    }

    public static String formatWord(int value) {
        return String.format("%08X", value);
    }

    public static String formatBits(int value) {
        String bits = String.format("%32s", Integer.toBinaryString(value)).replace(' ', '0');
        return bits.replaceAll("(.{8})(?!$)", "$1 ");
    }
}
