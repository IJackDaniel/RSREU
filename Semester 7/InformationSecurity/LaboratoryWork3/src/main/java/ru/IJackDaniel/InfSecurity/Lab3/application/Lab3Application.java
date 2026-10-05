package ru.IJackDaniel.InfSecurity.Lab3.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Lab3Application extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                Lab3Application.class.getResource("/ru/IJackDaniel/InfSecurity/Lab3/main-view.fxml")
        );

        Scene scene = new Scene(loader.load(), 640, 500);
        stage.setTitle("ЛР3, гр. 3413, Афонин Д.О., Барышев Г.А., Вариант 2");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
