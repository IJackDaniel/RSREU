package ru.IJackDaniel.InfSecurity.Lab3.service;

import ru.IJackDaniel.InfSecurity.Lab3.model.PermutationKey;

public class PermutationCipherService {
    public String encrypt(String text, PermutationKey key) {
        String padded = padToBlockSize(text, key.size());
        return applyPermutation(padded, key);
    }

    public String decrypt(String cipherText, PermutationKey key) {
        if (cipherText.length() % key.size() != 0) {
            throw new IllegalArgumentException(
                    "Длина шифротекста должна быть кратна размеру блока " + key.size() + "."
            );
        }
        return applyPermutation(cipherText, key.inverse());
    }

    private String applyPermutation(String text, PermutationKey key) {
        StringBuilder result = new StringBuilder(text.length());
        int blockSize = key.size();

        for (int blockStart = 0; blockStart < text.length(); blockStart += blockSize) {
            for (int resultIndex = 0; resultIndex < blockSize; resultIndex++) {
                int sourceIndex = key.sourceIndexForResultIndex(resultIndex);
                result.append(text.charAt(blockStart + sourceIndex));
            }
        }

        return result.toString();
    }

    private String padToBlockSize(String text, int blockSize) {
        int remainder = text.length() % blockSize;
        if (remainder == 0) {
            return text;
        }

        int spacesToAdd = blockSize - remainder;
        return text + " ".repeat(spacesToAdd);
    }
}
