package ru.IJackDaniel.InfS.controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import ru.IJackDaniel.InfS.service.AuthService;
import ru.IJackDaniel.InfS.service.LoginResult;
import ru.IJackDaniel.InfS.util.SceneManager;

public class LoginController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button loginButton;

    private AuthService authService;

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @FXML
    private void onLoginButtonClick() {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login.isBlank() || password.isBlank()) {
            messageLabel.setText("Введите логин и пароль");
            return;
        }

        LoginResult result = authService.login(login, password);

        switch (result.getStatus()) {

            case SUCCESS -> {
                SceneManager.showWorkspace(result.getUser());
            }

            case USER_NOT_FOUND -> {
                messageLabel.setText("Пользователь с таким логином не найден");

                passwordField.clear();
            }

            case INVALID_PASSWORD -> {
                messageLabel.setText("Неверный пароль. Осталось попыток: " + result.getAttemptsRemaining());

                passwordField.clear();
            }

            case BLOCKED -> {
                passwordField.clear();

                blockLogin(result.getBlockSeconds());
            }

            case NO_PASSWORDS -> {
                messageLabel.setText("У пользователя отсутствуют одноразовые пароли");
            }
        }
    }

    private void blockLogin(long seconds) {
        loginButton.setDisable(true);
        loginField.setDisable(true);
        passwordField.setDisable(true);

        messageLabel.setText("Вход временно заблокирован. Повторите через " + seconds + " сек.");

        PauseTransition pause = new PauseTransition(Duration.seconds(seconds));

        pause.setOnFinished(event -> {
            loginButton.setDisable(false);
            loginField.setDisable(false);
            passwordField.setDisable(false);

            messageLabel.setText("Блокировка снята. Можно повторить попытку.");
        });

        pause.play();
    }

    @FXML
    private void onRegisterButtonClick() {
        SceneManager.showRegister();
    }
}