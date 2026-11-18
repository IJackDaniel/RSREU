package ru.rsreu.rsa.model;

import java.util.List;

public record TextOperationResult(String text, List<BlockResult> blocks, int blockBytes, int originalByteLength) {
    public TextOperationResult { blocks = List.copyOf(blocks); }
}
