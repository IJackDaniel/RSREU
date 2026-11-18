package ru.rsreu.rsa.model;

import java.math.BigInteger;

public record RsaKeyPair(BigInteger p, BigInteger q, BigInteger modulus,
                         BigInteger phi, BigInteger publicExponent, BigInteger privateExponent) {
}
