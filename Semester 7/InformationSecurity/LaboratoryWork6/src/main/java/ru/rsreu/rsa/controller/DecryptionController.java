package ru.rsreu.rsa.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.rsreu.rsa.model.RsaKeyPair;
import ru.rsreu.rsa.model.TextOperationResult;
import ru.rsreu.rsa.service.TextEncryptionService;
import java.util.function.Consumer;

public final class DecryptionController {
    @FXML private TextArea ciphertextArea;
    @FXML private TextArea plaintextArea;
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
                "Закрытый ключ: d = " + key.privateExponent() + "; n = " + key.modulus());
    }
    public void setCiphertext(String value) { ciphertextArea.setText(value); }
    @FXML private void decrypt() {
        if (key == null) { statusLabel.setText("Сначала сформируйте ключи."); return; }
        try {
            TextOperationResult result = service.decryptText(ciphertextArea.getText(), key, blockSizeBox.getValue());
            plaintextArea.setText(result.text());
            statusLabel.setText("Расшифровано блоков: " + result.blocks().size());
            onResult.accept(result);
        } catch (IllegalArgumentException ex) { statusLabel.setText(ex.getMessage()); plaintextArea.clear(); }
    }
    @FXML private void clear() { ciphertextArea.clear(); plaintextArea.clear(); statusLabel.setText(""); }
}
