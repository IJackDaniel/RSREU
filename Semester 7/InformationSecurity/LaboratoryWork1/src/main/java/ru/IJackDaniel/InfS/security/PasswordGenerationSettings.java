package ru.IJackDaniel.InfS.security;

import java.math.BigDecimal;

public class PasswordGenerationSettings {

    public static final String ENG_ALPHABET_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String ENG_ALPHABET_LOWER = "abcdefghijklmnopqrstuvwxyz";

    public static final String DIGITS = "0123456789";

    public static final String SPECIAL_SYMBOLS = "!@#$%^&*";

    private BigDecimal probability;
    private BigDecimal attemptsPerMinute;
    private BigDecimal validityDays;

    private boolean useLowercase;
    private boolean useUppercase;
    private boolean useDigits;
    private boolean useSpecialSymbols;

    public PasswordGenerationSettings(BigDecimal probability, BigDecimal attemptsPerMinute, BigDecimal validityDays, boolean useLowercase, boolean useUppercase, boolean useDigits, boolean useSpecialSymbols) {
        this.probability = probability;
        this.attemptsPerMinute = attemptsPerMinute;
        this.validityDays = validityDays;
        this.useLowercase = useLowercase;
        this.useUppercase = useUppercase;
        this.useDigits = useDigits;
        this.useSpecialSymbols = useSpecialSymbols;
    }

    public static PasswordGenerationSettings createDefault() {
        return new PasswordGenerationSettings(new BigDecimal("0.00001"), new BigDecimal("3"), new BigDecimal("10"), true, true, true, true);
    }

    public String getAlphabet() {
        StringBuilder alphabet = new StringBuilder();

        if (useLowercase) {
            alphabet.append(ENG_ALPHABET_LOWER);
        }

        if (useUppercase) {
            alphabet.append(ENG_ALPHABET_UPPER);
        }

        if (useDigits) {
            alphabet.append(DIGITS);
        }

        if (useSpecialSymbols) {
            alphabet.append(SPECIAL_SYMBOLS);
        }

        return alphabet.toString();
    }

    public int getAlphabetPower() {
        return getAlphabet().length();
    }

    public void copyFrom(PasswordGenerationSettings settings) {
        this.probability = settings.probability;
        this.attemptsPerMinute = settings.attemptsPerMinute;
        this.validityDays = settings.validityDays;

        this.useLowercase = settings.useLowercase;
        this.useUppercase = settings.useUppercase;
        this.useDigits = settings.useDigits;
        this.useSpecialSymbols = settings.useSpecialSymbols;
    }

    public BigDecimal getProbability() {
        return probability;
    }

    public BigDecimal getAttemptsPerMinute() {
        return attemptsPerMinute;
    }

    public BigDecimal getValidityDays() {
        return validityDays;
    }

    public boolean isUseLowercase() {
        return useLowercase;
    }

    public boolean isUseUppercase() {
        return useUppercase;
    }

    public boolean isUseDigits() {
        return useDigits;
    }

    public boolean isUseSpecialSymbols() {
        return useSpecialSymbols;
    }
}