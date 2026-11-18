package ru.rsreu.feistel.crypto;

import java.util.Arrays;

public final class Pkcs7Padding {
    private Pkcs7Padding() { }

    public static byte[] add(byte[] message, int blockSize) {
        int padding = blockSize - message.length % blockSize;
        byte[] result = Arrays.copyOf(message, message.length + padding);
        Arrays.fill(result, message.length, result.length, (byte) padding);
        return result;
    }

    public static byte[] remove(byte[] message, int blockSize) {
        if (message.length == 0 || message.length % blockSize != 0) {
            throw new IllegalArgumentException("Некорректный размер расшифрованных данных.");
        }
        int padding = Byte.toUnsignedInt(message[message.length - 1]);
        if (padding == 0 || padding > blockSize) {
            throw new IllegalArgumentException("Неверный ключ или повреждённые данные: ошибка PKCS#7.");
        }
        for (int i = message.length - padding; i < message.length; i++) {
            if (Byte.toUnsignedInt(message[i]) != padding) {
                throw new IllegalArgumentException("Неверный ключ или повреждённые данные: ошибка PKCS#7.");
            }
        }
        return Arrays.copyOf(message, message.length - padding);
    }
}
