package ru.rsreu.pr5.controller;

import ru.rsreu.pr5.service.CryptoAnalyzer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

/** Формирует кнопки букв и сообщает о выборе главному контроллеру. */
public class PickerController {
    @FXML private Label heading;
    @FXML private GridPane lettersGrid;

    public void configure(String code, Map<String, Character> mappings,
                   BiConsumer<String, Character> onSelect, Popup popup) {
        heading.setText("Замена для кода " + code);
        List<Character> options = new ArrayList<>();
        for (char letter : CryptoAnalyzer.ALPHABET.toCharArray()) {
            if (letter != ' ') options.add(letter);
        }
        options.add(' ');
        for (int i = 0; i <= options.size(); i++) {
            Character letter = i == options.size() ? null : options.get(i);
            String caption = letter == null ? "—" : letter == ' ' ? "␣" : String.valueOf(letter);
            Button button = new Button(caption);
            button.setPrefSize(39, 33);
            button.setMinSize(39, 33);
            boolean current = Objects.equals(mappings.get(code), letter)
                    && (letter != null || !mappings.containsKey(code));
            boolean occupied = letter != null && mappings.entrySet().stream()
                    .anyMatch(entry -> !entry.getKey().equals(code) && letter.equals(entry.getValue()));
            if (current) button.setStyle("-fx-background-color: #b8d9fa; -fx-border-color: #347fca;");
            else if (occupied) button.setStyle("-fx-background-color: #f1e6cb;");
            button.setTooltip(new Tooltip(occupied
                    ? "Уже назначен другому коду; прежняя замена будет удалена" : caption));
            button.setOnAction(event -> {
                onSelect.accept(code, letter);
                popup.hide();
            });
            lettersGrid.add(button, i % 6, i / 6);
        }
    }
}
