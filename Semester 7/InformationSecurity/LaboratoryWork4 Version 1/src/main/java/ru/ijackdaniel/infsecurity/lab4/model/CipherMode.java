package ru.ijackdaniel.infsecurity.lab4.model;

public enum CipherMode {
    STANDARD("Обычное гаммирование"),
    FEEDBACK("С обратной связью (вариант 2)");

    private final String title;
    CipherMode(String title) { this.title = title; }
    @Override public String toString() { return title; }
}
