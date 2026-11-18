package ru.rsreu.rsa.model;

import java.math.BigInteger;
import java.util.List;

public record ExponentiationTrace(BigInteger input, BigInteger exponent, BigInteger modulus,
                                  BigInteger output, List<ExponentiationStep> steps) {
    public ExponentiationTrace { steps = List.copyOf(steps); }
}
