package ru.rsreu.pr5;

import javafx.scene.control.Label;
import javafx.scene.text.*;
import javafx.geometry.Insets;
import java.util.regex.*;

/** Highlights matching cipher tokens and the corresponding plaintext positions. */
public final class CodeHighlighter {
    private CodeHighlighter() {}
    private static final Pattern CODE_PATTERN = Pattern.compile("(?<!\\d)\\d{2}(?!\\d)");
    public static void update(String raw, String decoded, String selectedCode, TextFlow cipherHighlighted, TextFlow plainHighlighted) {
        cipherHighlighted.getChildren().clear();
        plainHighlighted.getChildren().clear();
        Matcher matcher = CODE_PATTERN.matcher(raw);
        int last = 0;
        int decodedPos = 0;
        while (matcher.find()) {
            String between = raw.substring(last, matcher.start());
            addHighlighted(cipherHighlighted, between, false);
            String punctuation = between.replaceAll("\\s+", "");
            addHighlighted(plainHighlighted, punctuation, false);
            decodedPos += punctuation.length();
            boolean active = matcher.group().equals(selectedCode);
            addHighlighted(cipherHighlighted, matcher.group(), active);
            if (decodedPos < decoded.length()) {
                addHighlighted(plainHighlighted, decoded.substring(decodedPos, decodedPos + 1), active);
                decodedPos++;
            }
            last = matcher.end();
        }
        String end = raw.substring(last);
        addHighlighted(cipherHighlighted, end, false);
        addHighlighted(plainHighlighted, end.replaceAll("\\s+", ""), false);
    }

    private static void addHighlighted(TextFlow flow, String value, boolean active) {
        if (value.isEmpty()) return;
        if (active) {
            Label label = new Label(value);
            label.setPadding(new Insets(0, 1, 0, 1));
            label.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px; " +
                    "-fx-text-fill: #753900; -fx-font-weight: bold; " +
                    "-fx-background-color: #ffe187; -fx-background-radius: 2;");
            flow.getChildren().add(label);
        } else {
            Text text = new Text(value);
            text.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px; -fx-fill: #202c38;");
            flow.getChildren().add(text);
        }
    }

}
