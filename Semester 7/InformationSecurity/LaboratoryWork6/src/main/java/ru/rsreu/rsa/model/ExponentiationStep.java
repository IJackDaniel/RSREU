package ru.rsreu.rsa.model;

import java.math.BigInteger;

public record ExponentiationStep(int iteration, BigInteger exponentBefore,
                                 BigInteger resultBefore, BigInteger baseBefore,
                                 boolean multiplied, BigInteger resultAfter,
                                 BigInteger baseSquared) {
}
