package ru.rsreu.feistel.app;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public final class FeistelApplication extends Application {
    @Override public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/rsreu/feistel/view/main-view.fxml"));
        Scene scene = new Scene(loader.load(), 1100, 780);
        scene.getStylesheets().add(getClass().getResource("/ru/rsreu/feistel/style/app.css").toExternalForm());
        stage.setTitle("Сеть Фейштеля - ЛР №3, вариант 2, Афонин Д.О., Барышев Г.А.");
        stage.setMinWidth(920); stage.setMinHeight(650); stage.setScene(scene); stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
