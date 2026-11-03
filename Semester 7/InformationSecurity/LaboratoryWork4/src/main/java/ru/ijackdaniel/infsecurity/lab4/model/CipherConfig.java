package ru.ijackdaniel.infsecurity.lab4.model;

import java.util.Objects;

public record CipherConfig(int a, int c, int seed, int bits, CipherMode mode) {
    public CipherConfig {
        Objects.requireNonNull(mode, "Режим не задан");
        if (bits < 1 || bits > 16) throw new IllegalArgumentException("b должно быть от 1 до 16");
        int modulus = 1 << bits;
        if (a < 0 || a >= modulus || c < 0 || c >= modulus || seed < 0 || seed >= modulus) {
            throw new IllegalArgumentException("A, C и T0 должны находиться в диапазоне 0.." + (modulus - 1));
        }
    }
    public int modulus() { return 1 << bits; }
    public boolean hasFullPeriod() { return bits >= 2 && (c & 1) == 1 && (a & 3) == 1; }
}
