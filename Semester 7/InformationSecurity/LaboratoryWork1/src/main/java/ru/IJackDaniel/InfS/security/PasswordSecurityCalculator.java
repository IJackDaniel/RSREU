package ru.IJackDaniel.InfS.security;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class PasswordSecurityCalculator {

    private static final int MIN_PASSWORD_LENGTH = 6;

    private static final BigDecimal MINUTES_IN_DAY = new BigDecimal("1440");

    public PasswordSecurityResult calculate(PasswordGenerationSettings settings) {
        int alphabetPower = settings.getAlphabetPower();

        if (alphabetPower == 0) {
            throw new IllegalArgumentException("Алфавит не может быть пустым");
        }

        BigDecimal validityMinutes = settings.getValidityDays().multiply(MINUTES_IN_DAY);

        BigDecimal attemptsCount = settings.getAttemptsPerMinute().multiply(validityMinutes);

        BigDecimal requiredPasswordsDecimal = attemptsCount.divide(settings.getProbability(), 0, RoundingMode.CEILING);

        BigInteger requiredPasswordsCount = requiredPasswordsDecimal.toBigIntegerExact();

        BigInteger alphabet = BigInteger.valueOf(alphabetPower);

        BigInteger passwordsCount = BigInteger.ONE;

        int calculatedPasswordLength = 0;

        while (passwordsCount.compareTo(requiredPasswordsCount) < 0) {
            passwordsCount = passwordsCount.multiply(alphabet);

            calculatedPasswordLength++;
        }

        int passwordLength = Math.max(calculatedPasswordLength, MIN_PASSWORD_LENGTH);

        passwordsCount = alphabet.pow(passwordLength);

        return new PasswordSecurityResult(alphabetPower, requiredPasswordsCount, calculatedPasswordLength, passwordLength, passwordsCount);
    }
}