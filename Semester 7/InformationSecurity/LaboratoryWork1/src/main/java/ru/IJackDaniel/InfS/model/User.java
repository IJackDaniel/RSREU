package ru.IJackDaniel.InfS.model;

import java.util.List;

public record User(String login, List<String> passwords, String userData) {
}
