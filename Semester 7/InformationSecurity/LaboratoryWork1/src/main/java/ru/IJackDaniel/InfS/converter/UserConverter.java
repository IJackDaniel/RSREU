package ru.IJackDaniel.InfS.converter;

import ru.IJackDaniel.InfS.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserConverter {

    public static List<User> toUsers(List<String> data) {
        List<User> users = new ArrayList<>();

        for (int i = 0; i + 2 < data.size(); i += 3) {
            String login = data.get(i);
            List<String> passwords = separatePasswords(data.get(i + 1));
            String text = data.get(i + 2);

            User user = new User(login);
            user.setPasswords(passwords);
            user.setUserData(text);

            users.add(user);
        }

        return users;
    }

    public static List<String> toData(List<User> users) {
        List<String> data = new ArrayList<>();

        for (User user : users) {
            data.add(user.getLogin());
            data.add(String.join(";", user.getPasswords()));
            data.add(user.getText() == null ? "" : user.getText());
        }

        return data;
    }

    private static List<String> separatePasswords(String passwordsString) {
        if (passwordsString.isEmpty()) {
            return new ArrayList<>();
        }

        return new ArrayList<>(List.of(passwordsString.split(";")));
    }
}