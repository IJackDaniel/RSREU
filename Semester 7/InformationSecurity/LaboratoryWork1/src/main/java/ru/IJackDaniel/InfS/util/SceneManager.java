package ru.IJackDaniel.InfS.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.IJackDaniel.InfS.Main;
import ru.IJackDaniel.InfS.controller.GeneratorSettingsController;
import ru.IJackDaniel.InfS.controller.LoginController;
import ru.IJackDaniel.InfS.controller.RegisterController;
import ru.IJackDaniel.InfS.controller.WorkspaceController;
import ru.IJackDaniel.InfS.model.User;
import ru.IJackDaniel.InfS.security.PasswordGenerationSettings;
import ru.IJackDaniel.InfS.security.PasswordSecurityCalculator;
import ru.IJackDaniel.InfS.service.AuthService;
import ru.IJackDaniel.InfS.service.UserService;

import java.io.IOException;

public class SceneManager {

    private static Stage stage;

    private static UserService userService;
    private static AuthService authService;

    private static PasswordGenerationSettings passwordSettings;
    private static PasswordSecurityCalculator passwordSecurityCalculator;

    public static void initialize(Stage stage, UserService userService, AuthService authService, PasswordGenerationSettings passwordSettings, PasswordSecurityCalculator passwordSecurityCalculator) {
        SceneManager.stage = stage;
        SceneManager.userService = userService;
        SceneManager.authService = authService;

        SceneManager.passwordSettings = passwordSettings;

        SceneManager.passwordSecurityCalculator = passwordSecurityCalculator;
    }

    public static void showLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("login-view.fxml"));

            Scene scene = new Scene(loader.load());

            LoginController controller = loader.getController();

            controller.setAuthService(authService);

            stage.setTitle("Парольная защита");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("register-view.fxml"));

            Scene scene = new Scene(loader.load());

            RegisterController controller = loader.getController();

            controller.setUserService(userService);

            stage.setTitle("Регистрация");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showGeneratorSettings() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("generator-settings-view.fxml"));

            Scene scene = new Scene(loader.load());

            GeneratorSettingsController controller = loader.getController();

            controller.setDependencies(passwordSettings, passwordSecurityCalculator);

            Stage settingsStage = new Stage();

            settingsStage.setTitle("Параметры генератора");

            settingsStage.initOwner(stage);

            settingsStage.initModality(Modality.WINDOW_MODAL);

            settingsStage.setScene(scene);
            settingsStage.setResizable(false);

            settingsStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showWorkspace(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("workspace-view.fxml"));

            Scene scene = new Scene(loader.load());

            WorkspaceController controller = loader.getController();

            controller.setUserService(userService);
            controller.setUser(user);

            stage.setTitle("Рабочая область");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}