package ru.IJackDaniel.InfSecurity.Lab3.service;

import ru.IJackDaniel.InfSecurity.Lab3.model.PermutationKey;

public class SimplePermutationCipherService {

    public String encrypt(String text, PermutationKey key) {
        validateTextLength(text, key);
        return applyPermutation(text, key);
    }

    public String decrypt(String cipherText, PermutationKey key) {
        validateTextLength(cipherText, key);
        return applyPermutation(cipherText, key.inverse());
    }

    private String applyPermutation(String text, PermutationKey key) {
        StringBuilder result = new StringBuilder(text.length());

        for (int resultIndex = 0; resultIndex < key.size(); resultIndex++) {
            int sourceIndex = key.sourceIndexForResultIndex(resultIndex);
            result.append(text.charAt(sourceIndex));
        }

        return result.toString();
    }

    private void validateTextLength(String text, PermutationKey key) {
        if (text.length() != key.size()) {
            throw new IllegalArgumentException(
                    "В обычном режиме длина текста должна совпадать с длиной ключа."
            );
        }
    }
}