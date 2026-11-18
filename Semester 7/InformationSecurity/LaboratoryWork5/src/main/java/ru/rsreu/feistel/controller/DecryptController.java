package ru.rsreu.feistel.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import ru.rsreu.feistel.crypto.TextCipherService;

public final class DecryptController {
    @FXML private TextArea ciphertextArea;
    @FXML private TextArea plaintextArea;
    @FXML private Label decryptionSummaryLabel;
    private MainController mainController;
    void setMainController(MainController controller) { mainController = controller; }
    void setCiphertext(String text) { ciphertextArea.setText(text); }
    @FXML private void decryptText() {
        try {
            int rounds = mainController.readRounds();
            TextCipherService.DecryptedText result = mainController.getTextCipherService()
                    .decryptText(ciphertextArea.getText(), mainController.readKey(), rounds);
            plaintextArea.setText(result.plaintext()); mainController.displayTrace(result.operation());
            decryptionSummaryLabel.setText("Блоков: " + result.operation().blockCount() + " · PKCS#7 удалён");
            mainController.setStatus("Дешифрование выполнено. Расчёты обновлены.");
        } catch (IllegalArgumentException e) { mainController.showError("Ошибка дешифрования", e.getMessage()); }
    }
    @FXML private void showRounds() { mainController.openRoundsTab(); }
    @FXML private void clearFields() {
        ciphertextArea.clear(); plaintextArea.clear(); decryptionSummaryLabel.setText("");
        mainController.setStatus("Поля дешифрования очищены.");
    }
}
