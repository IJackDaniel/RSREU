package ru.rsreu.pr5.model;

import javafx.beans.property.*;

public final class GramRow {
    private final StringProperty gram;
    private final StringProperty decoded;
    private final LongProperty count;

    public GramRow(String g, long n) {
        gram = new SimpleStringProperty(g);
        decoded = new SimpleStringProperty();
        count = new SimpleLongProperty(n);
    }

    public String getGram() {
        return gram.get();
    }

    public void setDecoded(String value) {
        decoded.set(value);
    }

    public StringProperty gramProperty() {
        return gram;
    }

    public StringProperty decodedProperty() {
        return decoded;
    }

    public LongProperty countProperty() {
        return count;
    }
}
