package ru.IJackDaniel.InfSecurity.Lab3.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.IJackDaniel.InfSecurity.Lab3.model.PermutationKey;
import ru.IJackDaniel.InfSecurity.Lab3.service.KeyParserService;
import ru.IJackDaniel.InfSecurity.Lab3.service.PermutationCipherService;
import ru.IJackDaniel.InfSecurity.Lab3.util.HelpText;
import ru.IJackDaniel.InfSecurity.Lab3.validator.AsciiValidator;

public class MainController {
    @FXML
    private TextArea inputTextArea;

    @FXML
    private TextField keyField;

    @FXML
    private TextArea resultTextArea;

    private final KeyParserService keyParserService = new KeyParserService();
    private final PermutationCipherService cipherService = new PermutationCipherService();
    private final AsciiValidator asciiValidator = new AsciiValidator();

    @FXML
    private void initialize() {
        keyField.setText("3 5 2 6 1 4");
    }

    @FXML
    private void onEncrypt() {
        process(true);
    }

    @FXML
    private void onDecrypt() {
        process(false);
    }

    @FXML
    private void onHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Справка");
        alert.setHeaderText("Допустимые символы и формат ключа");
        alert.setContentText(HelpText.CONTENT);
        alert.showAndWait();
    }

    @FXML
    private void onClear() {
        inputTextArea.clear();
        resultTextArea.clear();
    }

    private void process(boolean encrypt) {
        try {
            String text = inputTextArea.getText();
            asciiValidator.validate(text);

            PermutationKey key = keyParserService.parse(keyField.getText());
            String result = encrypt
                    ? cipherService.encrypt(text, key)
                    : cipherService.decrypt(text, key);

            resultTextArea.setText(result);
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Не удалось выполнить операцию");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
