package ru.rsreu.pr5.view;

import ru.rsreu.pr5.controller.PickerController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Popup;
import java.io.IOException;
import java.util.Map;
import java.util.function.BiConsumer;

/** Открывает всплывающий выбор буквы из FXML. */
public final class CharacterPicker {
    private CharacterPicker() {}

    public static void show(Button anchor, String code, Map<String, Character> mappings,
                            BiConsumer<String, Character> onSelect) {
        try {
            FXMLLoader loader = new FXMLLoader(CharacterPicker.class.getResource("/ru/rsreu/pr5/view/picker-view.fxml"));
            Parent content = loader.load();
            Popup popup = new Popup();
            popup.setAutoHide(true);
            popup.setHideOnEscape(true);
            PickerController controller = loader.getController();
            controller.configure(code, mappings, onSelect, popup);
            popup.getContent().add(content);
            Bounds bounds = anchor.localToScreen(anchor.getBoundsInLocal());
            if (bounds != null) popup.show(anchor, bounds.getMinX(), bounds.getMaxY() + 3);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось загрузить выбор символа", e);
        }
    }
}
