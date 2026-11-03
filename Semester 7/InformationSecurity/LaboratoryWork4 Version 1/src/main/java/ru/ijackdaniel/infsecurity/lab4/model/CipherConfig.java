package ru.ijackdaniel.infsecurity.lab4.model;

import java.util.Objects;

public record CipherConfig(int a, int c, int seed, CipherMode mode) {
    public static final int BITS = 7;
    public static final int MODULUS = 1 << BITS;

    public CipherConfig {
        Objects.requireNonNull(mode, "Режим не задан");
        if (a < 0 || a >= MODULUS || c < 0 || c >= MODULUS || seed < 0 || seed >= MODULUS) {
            throw new IllegalArgumentException("A, C и T0 должны быть в диапазоне 0..127");
        }
    }

    public int bits() { return BITS; }
    public int modulus() { return MODULUS; }
    public boolean hasFullPeriod() { return (c & 1) == 1 && (a & 3) == 1; }
}
