package ru.rsreu.pr5;

import javafx.beans.property.*;

final class SymbolRow {
    private final StringProperty code;
    private final LongProperty count;
    private final StringProperty frequency;
    private final StringProperty letter;

    public SymbolRow(String c, long n, String f, Character l) {
        code = new SimpleStringProperty(c);
        count = new SimpleLongProperty(n);
        frequency = new SimpleStringProperty(f);
        letter = new SimpleStringProperty(l == null ? "" : String.valueOf(l));
    }

    String getCode() {
        return code.get();
    }

    String getLetter() {
        return letter.get();
    }

    void setLetter(String x) {
        letter.set(x);
    }

    StringProperty codeProperty() {
        return code;
    }

    LongProperty countProperty() {
        return count;
    }

    StringProperty frequencyProperty() {
        return frequency;
    }

    StringProperty letterProperty() {
        return letter;
    }
}
