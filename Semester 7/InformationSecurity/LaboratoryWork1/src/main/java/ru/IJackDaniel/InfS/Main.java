package ru.IJackDaniel.InfS;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.IJackDaniel.InfS.converter.UserConverter;
import ru.IJackDaniel.InfS.file.UserFileReader;
import ru.IJackDaniel.InfS.generator.OneTimePasswordGenerator;
import ru.IJackDaniel.InfS.generator.PasswordGenerator;
import ru.IJackDaniel.InfS.model.User;
import ru.IJackDaniel.InfS.security.PasswordGenerationSettings;
import ru.IJackDaniel.InfS.security.PasswordSecurityCalculator;
import ru.IJackDaniel.InfS.service.AuthService;
import ru.IJackDaniel.InfS.service.UserService;
import ru.IJackDaniel.InfS.util.SceneManager;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        List<String> data =
                UserFileReader.readData();

        List<User> users =
                UserConverter.toUsers(data);

        PasswordGenerationSettings passwordSettings =
                PasswordGenerationSettings.createDefault();

        PasswordSecurityCalculator passwordSecurityCalculator =
                new PasswordSecurityCalculator();

        PasswordGenerator generator =
                new OneTimePasswordGenerator(
                        passwordSettings,
                        passwordSecurityCalculator
                );

        UserService userService =
                new UserService(
                        users,
                        generator
                );

        AuthService authService =
                new AuthService(
                        users,
                        userService
                );

        SceneManager.initialize(
                stage,
                userService,
                authService,
                passwordSettings,
                passwordSecurityCalculator
        );

        SceneManager.showLogin();
    }

    public static void main(String[] args) {
        launch();
    }
}