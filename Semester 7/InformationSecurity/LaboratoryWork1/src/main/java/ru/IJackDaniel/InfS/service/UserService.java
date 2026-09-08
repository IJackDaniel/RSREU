package ru.IJackDaniel.InfS.service;

import ru.IJackDaniel.InfS.converter.UserConverter;
import ru.IJackDaniel.InfS.file.UserFileWriter;
import ru.IJackDaniel.InfS.generator.PasswordGenerator;
import ru.IJackDaniel.InfS.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserService {

    private static final int PASSWORDS_COUNT = 5;

    private final List<User> users;
    private final PasswordGenerator generator;

    public UserService(List<User> users, PasswordGenerator generator) {
        this.users = users;
        this.generator = generator;
    }

    public boolean isLoginExists(String login) {
        for (User user : users) {
            if (Objects.equals(user.getLogin(), login)) {
                return true;
            }
        }

        return false;
    }

    public List<String> register(String login) {
        if (isLoginExists(login)) {
            return null;
        }

        User newUser = new User(login);

        List<String> passwords = generateNewPasswords(newUser);

        users.add(newUser);

        saveUsers();

        return passwords;
    }

    public List<String> generateNewPasswords(User user) {
        List<String> passwords = new ArrayList<>();

        for (int i = 0; i < PASSWORDS_COUNT; i++) {
            passwords.add(generator.generatePassword());
        }

        user.setPasswords(passwords);

        return passwords;
    }

    public void saveText(User user, String text) {
        user.setUserData(text);

        saveUsers();
    }

    public void saveUsers() {
        UserFileWriter.writeData(UserConverter.toData(users));
    }

    public List<String> regeneratePasswords(User user) {
        List<String> passwords = generateNewPasswords(user);
        saveUsers();
        return passwords;
    }
}