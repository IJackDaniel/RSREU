package ru.rsreu.rsa.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class RsaApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/rsreu/rsa/view/main-view.fxml"));
        Scene scene = new Scene(loader.load(), 960, 740);
        scene.getStylesheets().add(getClass().getResource("/ru/rsreu/rsa/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("ЛР №4 — RSA (вариант 2)");
        stage.setMinWidth(790);
        stage.setMinHeight(610);
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
