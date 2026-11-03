package ru.ijackdaniel.infsecurity.lab4.model;

import java.util.List;

public record CipherResult(byte[] bytes, List<CipherStep> steps) {
    public CipherResult {
        bytes = bytes.clone();
        steps = List.copyOf(steps);
    }
    @Override public byte[] bytes() { return bytes.clone(); }
}
