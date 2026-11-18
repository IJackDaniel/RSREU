package ru.rsreu.feistel.model;

public enum CipherDirection {
    ENCRYPT("Шифрование"),
    DECRYPT("Дешифрование");

    private final String displayName;

    CipherDirection(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}
