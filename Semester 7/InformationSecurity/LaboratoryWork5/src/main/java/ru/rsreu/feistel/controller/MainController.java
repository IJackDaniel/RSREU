package ru.rsreu.feistel.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.rsreu.feistel.crypto.FeistelCipher;
import ru.rsreu.feistel.crypto.TextCipherService;
import ru.rsreu.feistel.model.OperationResult;
import ru.rsreu.feistel.util.HexCodec;
import java.security.SecureRandom;

public final class MainController {
    @FXML private TextField keyHexField;
    @FXML private Spinner<Integer> roundsSpinner;
    @FXML private Label statusLabel;
    @FXML private TabPane mainTabs;
    @FXML private Tab decryptTab;
    @FXML private Tab roundsTab;
    @FXML private EncryptController encryptViewController;
    @FXML private DecryptController decryptViewController;
    @FXML private RoundsController roundsViewController;

    private final SecureRandom secureRandom = new SecureRandom();
    private final TextCipherService textCipherService = new TextCipherService();

    @FXML private void initialize() {
        roundsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                FeistelCipher.MIN_ROUNDS, FeistelCipher.MAX_ROUNDS, FeistelCipher.VARIANT_ROUNDS));
        roundsSpinner.setEditable(true);
        generateRandomKey();
        encryptViewController.setMainController(this);
        decryptViewController.setMainController(this);
        roundsViewController.setMainController(this);
        setStatus("Готово. Для варианта №2 используются 10 раундов и XOR.");
    }
    @FXML private void generateRandomKey() {
        byte[] key = new byte[8]; secureRandom.nextBytes(key);
        keyHexField.setText(HexCodec.formatBytes(key));
    }
    @FXML private void resetVariantRounds() {
        roundsSpinner.getValueFactory().setValue(FeistelCipher.VARIANT_ROUNDS);
        setStatus("Установлены 10 раундов по варианту №2.");
    }
    public byte[] readKey() { return HexCodec.parseKey(keyHexField.getText()); }
    public int readRounds() {
        try {
            int value = roundsSpinner.getValueFactory().getConverter().fromString(roundsSpinner.getEditor().getText());
            if (value < FeistelCipher.MIN_ROUNDS || value > FeistelCipher.MAX_ROUNDS) throw new NumberFormatException();
            roundsSpinner.getValueFactory().setValue(value);
            return value;
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Количество раундов должно быть целым числом от 1 до 128.");
        }
    }
    public TextCipherService getTextCipherService() { return textCipherService; }
    public void setStatus(String message) { statusLabel.setText(message); }
    public void displayTrace(OperationResult result) { roundsViewController.showOperation(result); }
    public void openRoundsTab() { mainTabs.getSelectionModel().select(roundsTab); }
    public void openDecryptTabWithCiphertext(String ciphertext) {
        decryptViewController.setCiphertext(ciphertext);
        mainTabs.getSelectionModel().select(decryptTab);
    }
    public void showError(String title, String message) {
        setStatus("Ошибка: " + message);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка"); alert.setHeaderText(title); alert.setContentText(message);
        alert.showAndWait();
    }
}
