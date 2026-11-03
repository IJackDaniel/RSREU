package ru.ijackdaniel.infsecurity.lab4.crypto;

final class BitBlocks {
    private BitBlocks() { }

    static int read(byte[] input, int startBit, int width) {
        int value = 0;
        for (int j = 0; j < width; j++) {
            int bitIndex = startBit + j;
            int bit = (input[bitIndex / 8] >>> (7 - bitIndex % 8)) & 1;
            value = (value << 1) | bit;
        }
        return value;
    }

    static void write(byte[] output, int startBit, int width, int value) {
        for (int j = 0; j < width; j++) {
            int bitIndex = startBit + j;
            int bit = (value >>> (width - 1 - j)) & 1;
            output[bitIndex / 8] |= (byte) (bit << (7 - bitIndex % 8));
        }
    }
}
