package ru.IJackDaniel.InfS.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private final String login;
    private List<String> passwords = new ArrayList<>();
    private String userData = "";

    public User(String login) {
        this.login = login;
    }

    public String getCurrentPassword() {
        if (this.hasPasswords()) {
            return this.passwords.getFirst();
        }
        return null;
    }

    public boolean hasPasswords() {
        return !this.passwords.isEmpty();
    }

    public void removeCurrentPassword() {
        if (this.hasPasswords()) {
            this.passwords.removeFirst();
        }
    }

    public String getLogin() {
        return login;
    }

    public String getText() {
        return userData;
    }

    public List<String> getPasswords() {
        return passwords;
    }

    public void setPasswords(List<String> passwords) {
        this.passwords = passwords;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }
}
