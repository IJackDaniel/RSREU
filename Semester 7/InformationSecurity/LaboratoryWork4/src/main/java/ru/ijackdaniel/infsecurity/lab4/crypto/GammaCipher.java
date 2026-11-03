package ru.ijackdaniel.infsecurity.lab4.crypto;

import ru.ijackdaniel.infsecurity.lab4.model.*;

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
        long bitLength = (long) input.length * 8;

        for (long offset = 0, index = 1; offset < bitLength; offset += config.bits(), index++) {
            int start = (int) offset;
            int width = (int) Math.min(config.bits(), bitLength - offset);
            int inputBlock = BitBlocks.read(input, start, width);

            boolean feedback = config.mode() == CipherMode.FEEDBACK && index > 1;
            int source = feedback ? Integer.bitCount(previousPlaintext) : previousGamma;

            int gamma = (int) (((long) config.a() * source + config.c()) % config.modulus());
            int gammaBlock = gamma >>> (config.bits() - width);
            int outputBlock = inputBlock ^ gammaBlock;
            BitBlocks.write(output, start, width, outputBlock);
            int plaintextBlock = decrypt ? outputBlock : inputBlock;
            if (trace) {
                steps.add(new CipherStep((int) index, width, inputBlock, gammaBlock, outputBlock, source, feedback ? previousPlaintext : null, feedback ? Integer.bitCount(previousPlaintext) : null));
            }
            previousPlaintext = plaintextBlock;
            previousGamma = gamma;
        }
        return new CipherResult(output, steps);
    }
}
