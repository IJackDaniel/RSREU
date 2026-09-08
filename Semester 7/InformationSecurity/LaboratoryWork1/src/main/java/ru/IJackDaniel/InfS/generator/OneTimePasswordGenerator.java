package ru.IJackDaniel.InfS.generator;

import ru.IJackDaniel.InfS.security.PasswordGenerationSettings;
import ru.IJackDaniel.InfS.security.PasswordSecurityCalculator;
import ru.IJackDaniel.InfS.security.PasswordSecurityResult;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OneTimePasswordGenerator implements PasswordGenerator {

    private final PasswordGenerationSettings settings;
    private final PasswordSecurityCalculator calculator;

    private final SecureRandom random;

    public OneTimePasswordGenerator(PasswordGenerationSettings settings, PasswordSecurityCalculator calculator) {
        this.settings = settings;
        this.calculator = calculator;
        this.random = new SecureRandom();
    }

    @Override
    public String generatePassword() {
        PasswordSecurityResult result = calculator.calculate(settings);

        int passwordLength = result.getPasswordLength();

        String alphabet = settings.getAlphabet();

        List<String> enabledGroups = getEnabledGroups();

        List<Character> passwordCharacters = new ArrayList<>();

        for (String group : enabledGroups) {
            passwordCharacters.add(getRandomCharacter(group));
        }

        while (passwordCharacters.size() < passwordLength) {
            passwordCharacters.add(getRandomCharacter(alphabet));
        }

        Collections.shuffle(passwordCharacters, random);

        StringBuilder password = new StringBuilder();

        for (Character character : passwordCharacters) {
            password.append(character);
        }

        return password.toString();
    }

    private List<String> getEnabledGroups() {
        List<String> groups = new ArrayList<>();

        if (settings.isUseLowercase()) {
            groups.add(PasswordGenerationSettings.ENG_ALPHABET_LOWER);
        }

        if (settings.isUseUppercase()) {
            groups.add(PasswordGenerationSettings.ENG_ALPHABET_UPPER);
        }

        if (settings.isUseDigits()) {
            groups.add(PasswordGenerationSettings.DIGITS);
        }

        if (settings.isUseSpecialSymbols()) {
            groups.add(PasswordGenerationSettings.SPECIAL_SYMBOLS);
        }

        return groups;
    }

    private char getRandomCharacter(String symbols) {
        int index = random.nextInt(symbols.length());

        return symbols.charAt(index);
    }
}