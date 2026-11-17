package ru.rsreu.pr5.app;

import ru.rsreu.pr5.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Точка входа: загружает FXML, управление передаёт MainController. */
public class CryptoApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/rsreu/pr5/view/main-view.fxml"));

        Parent root = loader.load();
        stage.setTitle("ПР5 - Криптоанализ простой замены | Вариант 2");
        stage.setScene(new Scene(root, 1370, 755));

        MainController controller = loader.getController();

        controller.setStage(stage);
        stage.show();
        controller.loadDefault();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
