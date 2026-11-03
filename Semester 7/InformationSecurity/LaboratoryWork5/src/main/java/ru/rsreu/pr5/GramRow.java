package ru.rsreu.pr5;

import javafx.beans.property.*;

final class GramRow {
    private final StringProperty gram;
    private final StringProperty decoded;
    private final LongProperty count;

    public GramRow(String g, long n) {
        gram = new SimpleStringProperty(g);
        decoded = new SimpleStringProperty();
        count = new SimpleLongProperty(n);
    }

    String getGram() {
        return gram.get();
    }

    void setDecoded(String value) {
        decoded.set(value);
    }

    StringProperty gramProperty() {
        return gram;
    }

    StringProperty decodedProperty() {
        return decoded;
    }

    LongProperty countProperty() {
        return count;
    }
}
