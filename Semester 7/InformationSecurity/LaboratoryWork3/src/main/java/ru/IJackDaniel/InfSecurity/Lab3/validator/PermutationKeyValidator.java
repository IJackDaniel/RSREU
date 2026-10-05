package ru.IJackDaniel.InfSecurity.Lab3.validator;

public class PermutationKeyValidator {
    public void validate(int[] positions) {
        if (positions == null || positions.length < 2) {
            throw new IllegalArgumentException("Ключ должен содержать как минимум два числа.");
        }

        boolean[] seen = new boolean[positions.length + 1];
        for (int value : positions) {
            if (value < 1 || value > positions.length) {
                throw new IllegalArgumentException(
                        "Для ключа длины " + positions.length + " допустимы только числа от 1 до " + positions.length + "."
                );
            }
            if (seen[value]) {
                throw new IllegalArgumentException("Число " + value + " встречается в ключе больше одного раза.");
            }
            seen[value] = true;
        }
    }
}
