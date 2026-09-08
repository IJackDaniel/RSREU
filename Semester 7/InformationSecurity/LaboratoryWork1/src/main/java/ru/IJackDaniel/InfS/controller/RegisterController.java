package ru.IJackDaniel.InfS.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.IJackDaniel.InfS.service.UserService;
import ru.IJackDaniel.InfS.util.SceneManager;

import java.util.List;

public class RegisterController {

    @FXML
    private TextField loginField;

    @FXML
    private Label messageLabel;

    @FXML
    private TextArea passwordsTextArea;

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @FXML
    private void onRegisterButtonClick() {
        String login = loginField.getText();

        if (login.isBlank()) {
            messageLabel.setText("Введите логин");
            return;
        }

        if (userService.isLoginExists(login)) {
            messageLabel.setText("Пользователь с таким логином уже существует");
            return;
        }

        List<String> passwords = userService.register(login);

        messageLabel.setText("Пользователь зарегистрирован. " + "Сохраните одноразовые пароли.");

        passwordsTextArea.setText(String.join(System.lineSeparator(), passwords));

        loginField.setDisable(true);
    }

    @FXML
    private void onGeneratorSettingsButtonClick() {
        SceneManager.showGeneratorSettings();
    }

    @FXML
    private void onBackButtonClick() {
        SceneManager.showLogin();
    }
}