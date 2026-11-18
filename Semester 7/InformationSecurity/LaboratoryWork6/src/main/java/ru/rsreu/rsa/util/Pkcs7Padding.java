package ru.rsreu.rsa.util;

import java.util.Arrays;

/** Дополнение сообщения до целого числа байтовых блоков. */
public final class Pkcs7Padding {
    private Pkcs7Padding() { }

    public static byte[] add(byte[] data, int blockSize) {
        if (blockSize < 1 || blockSize > 255) {
            throw new IllegalArgumentException("Размер блока PKCS#7 должен быть от 1 до 255 байт.");
        }
        int paddingLength = blockSize - data.length % blockSize;
        byte[] result = Arrays.copyOf(data, data.length + paddingLength);
        Arrays.fill(result, data.length, result.length, (byte) paddingLength);
        return result;
    }

    public static byte[] remove(byte[] data, int blockSize) {
        if (blockSize < 1 || blockSize > 255 || data.length == 0 || data.length % blockSize != 0) {
            throw new IllegalArgumentException("Некорректная длина данных для PKCS#7.");
        }
        int paddingLength = Byte.toUnsignedInt(data[data.length - 1]);
        if (paddingLength < 1 || paddingLength > blockSize) {
            throw new IllegalArgumentException("Некорректное дополнение PKCS#7. Проверьте ключ и размер блока.");
        }
        for (int i = data.length - paddingLength; i < data.length; i++) {
            if (Byte.toUnsignedInt(data[i]) != paddingLength) {
                throw new IllegalArgumentException("Некорректное дополнение PKCS#7. Проверьте ключ и размер блока.");
            }
        }
        return Arrays.copyOf(data, data.length - paddingLength);
    }
}
