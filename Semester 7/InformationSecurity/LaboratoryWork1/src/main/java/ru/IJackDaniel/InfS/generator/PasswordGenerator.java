package ru.IJackDaniel.InfS.generator;

public interface PasswordGenerator {
    final static String ENG_ALPHABET_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final static String ENG_ALPHABET_LOWER = "abcdefghijklmnopqrstuvwxyz";
    final static String DIGITS = "0123456789";
    final static String SPECIAL_SYMBOLS = "!@#$%^&*";

    final static String PASSWORD_SYMBOLS = ENG_ALPHABET_LOWER + ENG_ALPHABET_UPPER + DIGITS + SPECIAL_SYMBOLS;
    final static int PASSWORD_LENGTH = 8;
    final static int ALPHABET_POWER = PASSWORD_SYMBOLS.length();

    String generatePassword();
}
