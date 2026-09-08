package ru.IJackDaniel.InfS.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import ru.IJackDaniel.InfS.model.User;
import ru.IJackDaniel.InfS.service.UserService;
import ru.IJackDaniel.InfS.util.SceneManager;

import java.util.List;

public class WorkspaceController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private TextArea userTextArea;

    @FXML
    private Label messageLabel;

    @FXML
    private TextArea newPasswordsTextArea;

    private UserService userService;

    private User user;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setUser(User user) {
        this.user = user;

        welcomeLabel.setText(
                "Добро пожаловать, " + user.getLogin() + "!"
        );

        userTextArea.setText(user.getText());

        if (!user.hasPasswords()) {
            List<String> passwords =
                    userService.regeneratePasswords(user);

            messageLabel.setText(
                    "Одноразовые пароли закончились. " +
                            "Сохраните новый набор."
            );

            newPasswordsTextArea.setText(
                    String.join(System.lineSeparator(), passwords)
            );

            newPasswordsTextArea.setVisible(true);
            newPasswordsTextArea.setManaged(true);
        }
    }

    @FXML
    private void onSaveButtonClick() {
        String text = userTextArea.getText()
                .replace("\r", " ")
                .replace("\n", " ");

        userTextArea.setText(text);

        userService.saveText(user, text);

        messageLabel.setText("Данные сохранены");
    }

    @FXML
    private void onLogoutButtonClick() {
        SceneManager.showLogin();
    }
}