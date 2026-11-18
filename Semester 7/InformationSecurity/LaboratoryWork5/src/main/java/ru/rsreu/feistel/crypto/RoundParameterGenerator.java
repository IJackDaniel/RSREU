package ru.rsreu.feistel.crypto;

import java.nio.ByteBuffer;

public final class RoundParameterGenerator {
    private final int leftKeyPart;
    private final int rightKeyPart;

    public RoundParameterGenerator(byte[] key) {
        if (key == null || key.length != 8) {
            throw new IllegalArgumentException("Ключ должен иметь размер 8 байт (64 бита).");
        }
        ByteBuffer buffer = ByteBuffer.wrap(key);
        this.leftKeyPart = buffer.getInt();
        this.rightKeyPart = buffer.getInt();
    }

    public int getLeftKeyPart() { return leftKeyPart; }
    public int getRightKeyPart() { return rightKeyPart; }
    public int rotateLeftKeyPart(int round) { return Integer.rotateLeft(leftKeyPart, round); }
    public int rotateRightKeyPart(int round) { return Integer.rotateRight(rightKeyPart, round); }
    public int calculateRoundParameter(int round) {
        return rotateLeftKeyPart(round) + rotateRightKeyPart(round);
    }
}
