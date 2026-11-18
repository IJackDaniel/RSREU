package ru.rsreu.feistel.crypto;

import ru.rsreu.feistel.model.OperationResult;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class TextCipherService {
    private final FeistelCipher feistelCipher = new FeistelCipher();

    public EncryptedText encryptText(String text, byte[] key, int rounds) {
        if (text == null) {
            throw new IllegalArgumentException("Введите исходный текст.");
        }
        OperationResult operation = feistelCipher.encrypt(text.getBytes(StandardCharsets.UTF_8), key, rounds);
        return new EncryptedText(Base64.getEncoder().encodeToString(operation.output()), operation);
    }

    public DecryptedText decryptText(String encoded, byte[] key, int rounds) {
        if (encoded == null || encoded.isBlank()) {
            throw new IllegalArgumentException("Введите шифротекст в Base64.");
        }
        byte[] ciphertext;
        try {
            ciphertext = Base64.getDecoder().decode(encoded.replaceAll("\\s", ""));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Шифротекст не соответствует формату Base64.", ex);
        }
        OperationResult operation = feistelCipher.decrypt(ciphertext, key, rounds);
        try {
            String decoded = StandardCharsets.UTF_8.newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT)
                    .decode(ByteBuffer.wrap(operation.output())).toString();
            return new DecryptedText(decoded, operation);
        } catch (CharacterCodingException ex) {
            throw new IllegalArgumentException("Неверный ключ или повреждённые данные: некорректный UTF-8.", ex);
        }
    }

    public record EncryptedText(String base64, OperationResult operation) {
    }

    public record DecryptedText(String plaintext, OperationResult operation) {
    }
}
