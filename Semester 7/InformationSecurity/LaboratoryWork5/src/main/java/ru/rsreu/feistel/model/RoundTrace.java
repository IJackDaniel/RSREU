package ru.rsreu.feistel.model;

public record RoundTrace(
        CipherDirection direction, int blockNumber, int roundNumber,
        int[] beforeBranches, int[] afterBranches,
        int leftKeyPart, int rightKeyPart,
        int rotatedLeftPart, int rotatedRightPart,
        int roundParameter, int functionInput, int functionResult
) {
    public RoundTrace {
        beforeBranches = beforeBranches.clone();
        afterBranches = afterBranches.clone();
    }
    @Override public int[] beforeBranches() { return beforeBranches.clone(); }
    @Override public int[] afterBranches() { return afterBranches.clone(); }
}
