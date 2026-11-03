package ru.rsreu.pr5;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.util.*;
import java.util.function.BiConsumer;

/** A compact pop-up grid for choosing a substitution symbol. */
public final class CharacterPicker {
    private CharacterPicker() {}
    public static void show(Button anchor, String code, Map<String, Character> mappings, BiConsumer<String, Character> onSelect) {
        Popup popup = new Popup();
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);
        VBox content = new VBox(9);
        content.setPadding(new Insets(12));
        content.setStyle("-fx-background-color: white; -fx-border-color: #98aabd; " +
                "-fx-border-radius: 7; -fx-background-radius: 7; -fx-effect: dropshadow(gaussian, #00000033, 12, 0, 2, 3);");
        Label heading = new Label("Замена для кода " + code);
        heading.setStyle("-fx-font-weight: bold;");
        Label hint = new Label("␣ — пробел, — — убрать замену");
        hint.setStyle("-fx-text-fill: #667788; -fx-font-size: 11;");
        GridPane grid = new GridPane();
        grid.setHgap(4); grid.setVgap(4);
        List<Character> options = new ArrayList<>();
        for(char ch : CryptoAnalyzer.ALPHABET.toCharArray()) if(ch != ' ') options.add(ch);
        options.add(' ');
        for(int i=0; i<=options.size(); i++) {
            Character choice = i == options.size() ? null : options.get(i);
            String label = choice == null ? "—" : choice == ' ' ? "␣" : String.valueOf(choice);
            Button button = new Button(label);
            button.setPrefSize(39, 33);
            button.setMinSize(39, 33);
            boolean isCurrent = Objects.equals(mappings.get(code), choice) &&
                    (choice != null || !mappings.containsKey(code));
            boolean occupied = choice != null && mappings.entrySet().stream()
                    .anyMatch(entry -> !entry.getKey().equals(code) && choice.equals(entry.getValue()));
            if(isCurrent) button.setStyle("-fx-background-color: #b8d9fa; -fx-border-color: #347fca;");
            else if(occupied) button.setStyle("-fx-background-color: #f1e6cb;");
            button.setTooltip(new Tooltip(occupied ? "Уже назначен другому коду; прежняя замена будет удалена" : label));
            button.setOnAction(e -> { onSelect.accept(code, choice); popup.hide(); });
            grid.add(button, i % 6, i / 6);
        }
        content.getChildren().addAll(heading, hint, grid);
        popup.getContent().add(content);
        javafx.geometry.Bounds b = anchor.localToScreen(anchor.getBoundsInLocal());
        if(b != null) popup.show(anchor, b.getMinX(), b.getMaxY()+3);
    }

}
