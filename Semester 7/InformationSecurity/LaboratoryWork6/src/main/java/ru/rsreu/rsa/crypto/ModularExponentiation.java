package ru.rsreu.rsa.crypto;

import ru.rsreu.rsa.model.ExponentiationStep;
import ru.rsreu.rsa.model.ExponentiationTrace;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class ModularExponentiation {
    public ExponentiationTrace calculateWithSteps(BigInteger input, BigInteger exponent, BigInteger modulus) {
        if (modulus.signum() <= 0 || exponent.signum() < 0) {
            throw new IllegalArgumentException("Неверные параметры возведения в степень.");
        }
        BigInteger base = input.mod(modulus);
        BigInteger power = exponent;
        BigInteger result = BigInteger.ONE;
        List<ExponentiationStep> steps = new ArrayList<>();
        int iteration = 1;
        while (power.signum() > 0) {
            BigInteger oldResult = result;
            BigInteger oldBase = base;
            BigInteger oldPower = power;
            boolean bitIsOne = power.testBit(0);
            if (bitIsOne) result = result.multiply(base).mod(modulus);
            base = base.multiply(base).mod(modulus);
            power = power.shiftRight(1);
            steps.add(new ExponentiationStep(iteration++, oldPower, oldResult,
                    oldBase, bitIsOne, result, base));
        }
        return new ExponentiationTrace(input, exponent, modulus, result, steps);
    }
}
