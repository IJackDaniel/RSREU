package ru.IJackDaniel.InfSecurity.Lab3.model;

import java.util.Arrays;

public final class PermutationKey {
    private final int[] positions;

    public PermutationKey(int[] positions) {
        this.positions = Arrays.copyOf(positions, positions.length);
    }

    public int size() {
        return positions.length;
    }

    public int sourceIndexForResultIndex(int resultIndex) {
        return positions[resultIndex] - 1;
    }


    public PermutationKey inverse() {
        int[] inverse = new int[positions.length];
        for (int resultIndex = 0; resultIndex < positions.length; resultIndex++) {
            int sourceIndex = positions[resultIndex] - 1;
            inverse[sourceIndex] = resultIndex + 1;
        }
        return new PermutationKey(inverse);
    }

    @Override
    public String toString() {
        return Arrays.toString(positions);
    }
}
