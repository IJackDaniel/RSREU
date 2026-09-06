package ru.IJackDaniel.InfS.generator;

import java.util.Random;

public class OneTimePasswordGenerator implements PasswordGenerator {
    @Override
    public String generatePassword() {
        
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomSymbolIndex = random.nextInt(ALPHABET_POWER);
            password.append(PASSWORD_SYMBOLS.charAt(randomSymbolIndex));
        }
        
        return password.toString();
    }
}
