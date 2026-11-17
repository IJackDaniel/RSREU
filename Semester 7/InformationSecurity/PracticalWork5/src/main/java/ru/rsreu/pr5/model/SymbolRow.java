package ru.rsreu.pr5.model;

import javafx.beans.property.*;

public final class SymbolRow {
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

    public String getCode() {
        return code.get();
    }

    public String getLetter() {
        return letter.get();
    }

    public void setLetter(String x) {
        letter.set(x);
    }

    public StringProperty codeProperty() {
        return code;
    }

    public LongProperty countProperty() {
        return count;
    }

    public StringProperty frequencyProperty() {
        return frequency;
    }

    public StringProperty letterProperty() {
        return letter;
    }
}
