package ru.IJackDaniel.InfS.security;

import java.math.BigInteger;

public class PasswordSecurityResult {

    private final int alphabetPower;
    private final BigInteger requiredPasswordsCount;
    private final int calculatedPasswordLength;
    private final int passwordLength;
    private final BigInteger passwordsCount;

    public PasswordSecurityResult(int alphabetPower, BigInteger requiredPasswordsCount, int calculatedPasswordLength, int passwordLength, BigInteger passwordsCount) {
        this.alphabetPower = alphabetPower;
        this.requiredPasswordsCount = requiredPasswordsCount;
        this.calculatedPasswordLength = calculatedPasswordLength;
        this.passwordLength = passwordLength;
        this.passwordsCount = passwordsCount;
    }

    public int getAlphabetPower() {
        return alphabetPower;
    }

    public BigInteger getRequiredPasswordsCount() {
        return requiredPasswordsCount;
    }

    public int getCalculatedPasswordLength() {
        return calculatedPasswordLength;
    }

    public int getPasswordLength() {
        return passwordLength;
    }

    public BigInteger getPasswordsCount() {
        return passwordsCount;
    }
}