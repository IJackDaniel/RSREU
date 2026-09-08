package ru.IJackDaniel.InfS.service;

import ru.IJackDaniel.InfS.model.User;

public class LoginResult {

    public enum Status {
        SUCCESS,
        USER_NOT_FOUND,
        INVALID_PASSWORD,
        BLOCKED,
        NO_PASSWORDS
    }

    private final Status status;
    private final User user;
    private final int attemptsRemaining;
    private final long blockSeconds;

    public LoginResult(
            Status status,
            User user,
            int attemptsRemaining,
            long blockSeconds
    ) {
        this.status = status;
        this.user = user;
        this.attemptsRemaining = attemptsRemaining;
        this.blockSeconds = blockSeconds;
    }

    public Status getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public int getAttemptsRemaining() {
        return attemptsRemaining;
    }

    public long getBlockSeconds() {
        return blockSeconds;
    }
}