package ru.ijackdaniel.infsecurity.lab4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override public void start(Stage stage) throws Exception {
        var fxml = MainApplication.class.getResource("main-view.fxml");
        Scene scene = new Scene(FXMLLoader.load(fxml), 1040, 780);
        stage.setTitle("ЛР4 — Шифр гаммирования | Вариант 2");
        stage.setScene(scene);
        stage.setMinWidth(860);
        stage.setMinHeight(670);
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
