package ru.ijackdaniel.infsecurity.lab4.crypto;

import ru.ijackdaniel.infsecurity.lab4.model.CipherConfig;
import ru.ijackdaniel.infsecurity.lab4.model.CipherMode;
import ru.ijackdaniel.infsecurity.lab4.model.CipherResult;
import ru.ijackdaniel.infsecurity.lab4.model.CipherStep;

import java.util.ArrayList;
import java.util.Objects;

public final class GammaCipher {
    public CipherResult encrypt(byte[] plaintext, CipherConfig config, boolean trace) {
        return transform(plaintext, config, trace, false);
    }

    public CipherResult decrypt(byte[] ciphertext, CipherConfig config, boolean trace) {
        return transform(ciphertext, config, trace, true);
    }

    private CipherResult transform(byte[] input, CipherConfig config, boolean trace, boolean decrypt) {
        Objects.requireNonNull(input, "Данные не заданы");
        Objects.requireNonNull(config, "Параметры не заданы");

        byte[] output = new byte[input.length];
        var steps = new ArrayList<CipherStep>();
        int previousGamma = config.seed();
        int previousPlaintext = 0;

        for (int i = 0; i < input.length; i++) {
            int value = input[i] & 0xFF;

            if (value > 127) {
                throw new IllegalArgumentException("Каждый входной символ должен иметь 7-битный код (0..127)");
            }

            // В режиме обратной связи берём число единичных битов предыдущего открытого символа.
            boolean feedback = config.mode() == CipherMode.FEEDBACK && i > 0;
            int source = feedback ? Integer.bitCount(previousPlaintext) : previousGamma;
            int gamma = (config.a() * source + config.c()) % CipherConfig.MODULUS;
            int transformed = value ^ gamma;

            output[i] = (byte) transformed;

            // При дешифровании открытый символ получается после XOR.
            // Именно он нужен для следующего шага с обратной связью.
            int plaintext = decrypt ? transformed : value;

            if (trace) {
                steps.add(new CipherStep(i + 1, CipherConfig.BITS, value, gamma, transformed, source, feedback ? previousPlaintext : null, feedback ? Integer.bitCount(previousPlaintext) : null));
            }

            previousPlaintext = plaintext;
            previousGamma = gamma;
        }

        return new CipherResult(output, steps);
    }
}
