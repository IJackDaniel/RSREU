package ru.rsreu.rsa.crypto;

import java.math.BigInteger;
import ru.rsreu.rsa.model.RsaKeyPair;

public final class RsaCipher {
    public BigInteger encryptBlock(BigInteger message, RsaKeyPair key) {
        checkBlock(message, key.modulus());
        return message.modPow(key.publicExponent(), key.modulus());
    }

    public BigInteger decryptBlock(BigInteger cipher, RsaKeyPair key) {
        checkBlock(cipher, key.modulus());
        return cipher.modPow(key.privateExponent(), key.modulus());
    }

    private void checkBlock(BigInteger block, BigInteger modulus) {
        if (block == null || block.signum() < 0 || block.compareTo(modulus) >= 0) {
            throw new IllegalArgumentException("Числовой блок должен быть от 0 до n − 1.");
        }
    }
}
