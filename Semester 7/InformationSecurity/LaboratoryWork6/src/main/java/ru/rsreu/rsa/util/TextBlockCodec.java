package ru.rsreu.rsa.util;

import java.math.BigInteger;
import java.util.Arrays;

public final class TextBlockCodec {
    private TextBlockCodec() { }

    public static int maximumBlockSize(BigInteger modulus) {
        if (modulus == null || modulus.compareTo(BigInteger.valueOf(256)) <= 0) {
            throw new IllegalArgumentException("Модуль n должен быть больше 256.");
        }
        int size = 0;
        BigInteger possibleValues = BigInteger.ONE;
        while (size < 255 && possibleValues.multiply(BigInteger.valueOf(256)).compareTo(modulus) <= 0) {
            possibleValues = possibleValues.multiply(BigInteger.valueOf(256));
            size++;
        }
        return size;
    }

    public static void validateBlockSize(int blockSize, BigInteger modulus) {
        if (blockSize < 1 || blockSize > maximumBlockSize(modulus)) {
            throw new IllegalArgumentException("Размер блока должен быть от 1 до "
                    + maximumBlockSize(modulus) + " байт для текущего модуля n.");
        }
    }

    public static BigInteger toUnsignedNumber(byte[] bytes, int start, int length) {
        return new BigInteger(1, Arrays.copyOfRange(bytes, start, start + length));
    }

    public static byte[] toFixedLengthBytes(BigInteger number, int length) {
        if (number.signum() < 0 || number.compareTo(BigInteger.ONE.shiftLeft(length * 8)) >= 0) {
            throw new IllegalArgumentException("Результат дешифрования не помещается в блок. Проверьте ключ и шифротекст.");
        }
        byte[] source = number.toByteArray();
        byte[] target = new byte[length];
        System.arraycopy(source, Math.max(0, source.length - length), target,
                Math.max(0, length - source.length), Math.min(length, source.length));
        return target;
    }
}
