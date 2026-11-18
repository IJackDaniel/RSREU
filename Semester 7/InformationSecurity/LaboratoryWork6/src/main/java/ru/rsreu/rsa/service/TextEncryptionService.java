package ru.rsreu.rsa.service;

import ru.rsreu.rsa.crypto.RsaCipher;
import ru.rsreu.rsa.model.BlockResult;
import ru.rsreu.rsa.model.RsaKeyPair;
import ru.rsreu.rsa.model.TextOperationResult;
import ru.rsreu.rsa.util.Pkcs7Padding;
import ru.rsreu.rsa.util.TextBlockCodec;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class TextEncryptionService {
    private final RsaCipher cipher = new RsaCipher();

    public TextOperationResult encryptText(String plaintext, RsaKeyPair key, int blockSize) {
        if (plaintext == null) throw new IllegalArgumentException("Введите текст.");
        TextBlockCodec.validateBlockSize(blockSize, key.modulus());
        byte[] originalBytes = plaintext.getBytes(StandardCharsets.UTF_8);
        byte[] paddedBytes = Pkcs7Padding.add(originalBytes, blockSize);
        List<BlockResult> records = new ArrayList<>();

        for (int start = 0; start < paddedBytes.length; start += blockSize) {
            BigInteger message = TextBlockCodec.toUnsignedNumber(paddedBytes, start, blockSize);
            BigInteger encrypted = cipher.encryptBlock(message, key);
            records.add(new BlockResult(records.size() + 1, blockSize, message, encrypted));
        }

        StringBuilder output = new StringBuilder();
        for (BlockResult record : records) {
            if (!output.isEmpty()) output.append(' ');
            output.append(record.output());
        }
        return new TextOperationResult(output.toString(), records, blockSize, originalBytes.length);
    }

    public TextOperationResult decryptText(String encoded, RsaKeyPair key, int blockSize) {
        if (encoded == null || encoded.isBlank()) throw new IllegalArgumentException("Введите числовые блоки шифротекста.");
        TextBlockCodec.validateBlockSize(blockSize, key.modulus());
        String[] tokens = encoded.strip().split("\\s+");
        ByteArrayOutputStream paddedBytes = new ByteArrayOutputStream();
        List<BlockResult> records = new ArrayList<>();

        for (int index = 0; index < tokens.length; index++) {
            BigInteger encrypted;
            try {
                encrypted = new BigInteger(tokens[index]);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Блок №" + (index + 1) + " не является целым числом.", ex);
            }
            BigInteger decrypted = cipher.decryptBlock(encrypted, key);
            paddedBytes.writeBytes(TextBlockCodec.toFixedLengthBytes(decrypted, blockSize));
            records.add(new BlockResult(index + 1, blockSize, encrypted, decrypted));
        }

        byte[] originalBytes = Pkcs7Padding.remove(paddedBytes.toByteArray(), blockSize);
        String text;
        try {
            text = StandardCharsets.UTF_8.newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT)
                    .decode(ByteBuffer.wrap(originalBytes)).toString();
        } catch (CharacterCodingException ex) {
            throw new IllegalArgumentException("Данные не являются корректным UTF-8. Возможно, указан неправильный ключ или размер блока.", ex);
        }
        return new TextOperationResult(text, records, blockSize, originalBytes.length);
    }
}
