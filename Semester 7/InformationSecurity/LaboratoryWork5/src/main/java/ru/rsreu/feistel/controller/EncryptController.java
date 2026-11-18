package ru.rsreu.feistel.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import ru.rsreu.feistel.crypto.TextCipherService;

public final class EncryptController {
    @FXML
    private TextArea plaintextArea;
    @FXML
    private TextArea ciphertextArea;
    @FXML
    private Label encryptionSummaryLabel;
    private MainController mainController;

    void setMainController(MainController controller) {
        mainController = controller;
    }

    @FXML
    private void encryptText() {
        try {
            int rounds = mainController.readRounds();
            TextCipherService.EncryptedText result = mainController.getTextCipherService()
                    .encryptText(plaintextArea.getText(), mainController.readKey(), rounds);
            ciphertextArea.setText(result.base64());
            mainController.displayTrace(result.operation());
            encryptionSummaryLabel.setText("Блоков: " + result.operation().blockCount() + " · Раундов на блок: " + rounds);
            mainController.setStatus("Шифрование выполнено. Расчёты доступны на вкладке «Раунды».");
        } catch (IllegalArgumentException e) {
            mainController.showError("Ошибка шифрования", e.getMessage());
        }
    }

    @FXML
    private void copyCiphertext() {
        if (ciphertextArea.getText().isBlank()) return;
        ClipboardContent content = new ClipboardContent();
        content.putString(ciphertextArea.getText());
        Clipboard.getSystemClipboard().setContent(content);
        mainController.setStatus("Шифротекст скопирован.");
    }

    @FXML
    private void showRounds() {
        mainController.openRoundsTab();
    }

    @FXML
    private void sendToDecryption() {
        mainController.openDecryptTabWithCiphertext(ciphertextArea.getText());
    }

    @FXML
    private void clearFields() {
        plaintextArea.clear();
        ciphertextArea.clear();
        encryptionSummaryLabel.setText("");
        mainController.setStatus("Поля шифрования очищены.");
    }
}
