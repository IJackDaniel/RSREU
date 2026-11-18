package ru.rsreu.rsa.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.rsreu.rsa.model.RsaKeyPair;
import ru.rsreu.rsa.model.TextOperationResult;
import ru.rsreu.rsa.service.TextEncryptionService;
import java.util.function.Consumer;

public final class EncryptionController {
    @FXML private TextArea plaintextArea;
    @FXML private TextArea ciphertextArea;
    @FXML private Label keyLabel;
    @FXML private ComboBox<Integer> blockSizeBox;
    @FXML private Label statusLabel;
    private RsaKeyPair key;
    private Consumer<Integer> onBlockSizeChanged = unused -> { };
    public void setOnBlockSizeChanged(Consumer<Integer> callback) { this.onBlockSizeChanged = callback; }

    @FXML private void initialize() {
        blockSizeBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) onBlockSizeChanged.accept(newValue);
        });
    }

    public void setBlockSizes(int maxSize, int selected) {
        blockSizeBox.getItems().clear();
        for (int size = 1; size <= maxSize; size++) blockSizeBox.getItems().add(size);
        blockSizeBox.setDisable(maxSize == 0);
        if (maxSize > 0) blockSizeBox.setValue(Math.min(Math.max(selected, 1), maxSize));
        else blockSizeBox.setValue(null);
    }

    public void selectBlockSize(int selected) {
        if (blockSizeBox.getItems().contains(selected)) blockSizeBox.setValue(selected);
    }
    private final TextEncryptionService service = new TextEncryptionService();
    private Consumer<TextOperationResult> onResult = unused -> { };
    public void setOnResult(Consumer<TextOperationResult> onResult) { this.onResult = onResult; }
    public void setKey(RsaKeyPair key) {
        this.key = key;
        keyLabel.setText(key == null ? "Ключи не сформированы" :
                "Открытый ключ: e = " + key.publicExponent() + "; n = " + key.modulus());
    }
    @FXML private void encrypt() {
        if (key == null) { statusLabel.setText("Сначала сформируйте ключи."); return; }
        try {
            TextOperationResult result = service.encryptText(plaintextArea.getText(), key, blockSizeBox.getValue());
            ciphertextArea.setText(result.text());
            statusLabel.setText("Зашифровано блоков: " + result.blocks().size());
            onResult.accept(result);
        } catch (IllegalArgumentException ex) { statusLabel.setText(ex.getMessage()); }
    }
    @FXML private void clear() { plaintextArea.clear(); ciphertextArea.clear(); statusLabel.setText(""); }
    public String getCiphertext() { return ciphertextArea.getText(); }
}
