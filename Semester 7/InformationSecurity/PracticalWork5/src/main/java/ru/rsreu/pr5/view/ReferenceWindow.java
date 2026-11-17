package ru.rsreu.pr5.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.IOException;

/** Открывает отдельное FXML-окно справочных частот. */
public final class ReferenceWindow {
    private ReferenceWindow() {}

    public static void show(Stage owner) {
        try {
            Parent root = FXMLLoader.load(ReferenceWindow.class.getResource("/ru/rsreu/pr5/view/reference-view.fxml"));
            Stage dialog = new Stage();
            dialog.initOwner(owner);
            dialog.setTitle("Справочник частот русского языка — ПР5");
            dialog.setScene(new Scene(root, 570, 600));
            dialog.show();
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось загрузить справочник частот", e);
        }
    }
}
