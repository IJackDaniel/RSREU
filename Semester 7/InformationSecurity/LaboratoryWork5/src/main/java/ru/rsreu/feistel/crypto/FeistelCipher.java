package ru.rsreu.feistel.crypto;

import ru.rsreu.feistel.model.CipherDirection;
import ru.rsreu.feistel.model.OperationResult;
import ru.rsreu.feistel.model.RoundTrace;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class FeistelCipher {
    public static final int BLOCK_BYTES = 16;
    public static final int MIN_ROUNDS = 1;
    public static final int MAX_ROUNDS = 128;
    public static final int VARIANT_ROUNDS = 10;

    public OperationResult encrypt(byte[] plaintext, byte[] key, int numberOfRounds) {
        validateArguments(plaintext, key, numberOfRounds);
        return transform(Pkcs7Padding.add(plaintext, BLOCK_BYTES), key, numberOfRounds, CipherDirection.ENCRYPT);
    }

    public OperationResult decrypt(byte[] ciphertext, byte[] key, int numberOfRounds) {
        validateArguments(ciphertext, key, numberOfRounds);
        if (ciphertext.length == 0 || ciphertext.length % BLOCK_BYTES != 0) {
            throw new IllegalArgumentException("Длина шифротекста должна быть кратна 16 байтам и не равна нулю.");
        }
        OperationResult raw = transform(ciphertext, key, numberOfRounds, CipherDirection.DECRYPT);
        return new OperationResult(Pkcs7Padding.remove(raw.output(), BLOCK_BYTES), raw.rounds(), raw.blockCount());
    }

    private void validateArguments(byte[] bytes, byte[] key, int numberOfRounds) {
        if (bytes == null) { throw new IllegalArgumentException("Отсутствуют данные."); }
        if (key == null || key.length != 8) { throw new IllegalArgumentException("Размер ключа должен составлять 8 байт."); }
        if (numberOfRounds < MIN_ROUNDS || numberOfRounds > MAX_ROUNDS) {
            throw new IllegalArgumentException("Количество раундов должно быть от 1 до 128.");
        }
    }

    private OperationResult transform(byte[] input, byte[] key, int numberOfRounds, CipherDirection direction) {
        RoundParameterGenerator generator = new RoundParameterGenerator(key);
        ByteBuffer source = ByteBuffer.wrap(input);
        ByteBuffer target = ByteBuffer.allocate(input.length);
        List<RoundTrace> trace = new ArrayList<>();
        int blocks = input.length / BLOCK_BYTES;

        for (int block = 1; block <= blocks; block++) {
            int x1 = source.getInt();
            int x2 = source.getInt();
            int x3 = source.getInt();
            int x4 = source.getInt();

            for (int step = 0; step < numberOfRounds; step++) {
                int round = direction == CipherDirection.ENCRYPT ? step + 1 : numberOfRounds - step;
                int[] before = {x1, x2, x3, x4};
                int rotatedLeft = generator.rotateLeftKeyPart(round);
                int rotatedRight = generator.rotateRightKeyPart(round);
                int vi = generator.calculateRoundParameter(round);
                int functionInput;
                int fi;
                int nextX1, nextX2, nextX3, nextX4;

                if (direction == CipherDirection.ENCRYPT) {
                    functionInput = x1;
                    fi = functionInput ^ vi;
                    nextX1 = x2 ^ fi;
                    nextX2 = x3;
                    nextX3 = x4;
                    nextX4 = x1;
                } else {
                    functionInput = x4;
                    fi = functionInput ^ vi;
                    nextX1 = x4;
                    nextX2 = x1 ^ fi;
                    nextX3 = x2;
                    nextX4 = x3;
                }

                x1 = nextX1;
                x2 = nextX2;
                x3 = nextX3;
                x4 = nextX4;
                
                trace.add(new RoundTrace(direction, block, round, before,
                        new int[]{x1, x2, x3, x4}, generator.getLeftKeyPart(),
                        generator.getRightKeyPart(), rotatedLeft, rotatedRight,
                        vi, functionInput, fi));
            }
            target.putInt(x1).putInt(x2).putInt(x3).putInt(x4);
        }
        return new OperationResult(target.array(), trace, blocks);
    }
}
