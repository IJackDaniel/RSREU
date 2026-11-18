package ru.rsreu.feistel.model;

import java.util.List;

public record OperationResult(byte[] output, List<RoundTrace> rounds, int blockCount) {
    public OperationResult {
        output = output.clone();
        rounds = List.copyOf(rounds);
    }
    @Override public byte[] output() { return output.clone(); }
}
