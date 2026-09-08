package ru.IJackDaniel.InfS.service;

import ru.IJackDaniel.InfS.model.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AuthService {

    private static final int MAX_ATTEMPTS = 3;

    private static final long INITIAL_BLOCK_SECONDS = 10;
    private static final long MAX_BLOCK_SECONDS = 300;

    private final List<User> users;
    private final UserService userService;

    private final Map<String, Integer> failedAttempts = new HashMap<>();
    private final Map<String, LocalDateTime> blockedUntil = new HashMap<>();

    public AuthService(List<User> users, UserService userService) {
        this.users = users;
        this.userService = userService;
    }

    public LoginResult login(String login, String password) {
        User user = findUser(login);

        if (user == null) {
            return new LoginResult(
                    LoginResult.Status.USER_NOT_FOUND,
                    null,
                    0,
                    0
            );
        }

        if (isBlocked(login)) {
            return new LoginResult(
                    LoginResult.Status.BLOCKED,
                    null,
                    0,
                    getRemainingBlockSeconds(login)
            );
        }

        if (!user.hasPasswords()) {
            return new LoginResult(
                    LoginResult.Status.NO_PASSWORDS,
                    null,
                    0,
                    0
            );
        }

        if (!Objects.equals(user.getCurrentPassword(), password)) {
            return handleWrongPassword(login);
        }

        resetAttempts(login);

        user.removeCurrentPassword();
        userService.saveUsers();

        return new LoginResult(
                LoginResult.Status.SUCCESS,
                user,
                MAX_ATTEMPTS,
                0
        );
    }

    private User findUser(String login) {
        for (User user : users) {
            if (Objects.equals(user.getLogin(), login)) {
                return user;
            }
        }

        return null;
    }

    private LoginResult handleWrongPassword(String login) {
        int attempts = failedAttempts.getOrDefault(login, 0) + 1;

        failedAttempts.put(login, attempts);

        if (attempts < MAX_ATTEMPTS) {
            return new LoginResult(
                    LoginResult.Status.INVALID_PASSWORD,
                    null,
                    MAX_ATTEMPTS - attempts,
                    0
            );
        }

        long blockSeconds = calculateBlockSeconds(attempts);

        blockedUntil.put(
                login,
                LocalDateTime.now().plusSeconds(blockSeconds)
        );

        return new LoginResult(
                LoginResult.Status.BLOCKED,
                null,
                0,
                blockSeconds
        );
    }

    private long calculateBlockSeconds(int attempts) {
        int additionalAttempts = attempts - MAX_ATTEMPTS;

        long blockSeconds =
                INITIAL_BLOCK_SECONDS * (1L << additionalAttempts);

        return Math.min(blockSeconds, MAX_BLOCK_SECONDS);
    }

    private boolean isBlocked(String login) {
        LocalDateTime blockEnd = blockedUntil.get(login);

        if (blockEnd == null) {
            return false;
        }

        if (LocalDateTime.now().isAfter(blockEnd)) {
            blockedUntil.remove(login);
            return false;
        }

        return true;
    }

    private long getRemainingBlockSeconds(String login) {
        LocalDateTime blockEnd = blockedUntil.get(login);

        if (blockEnd == null) {
            return 0;
        }

        long seconds = Duration.between(
                LocalDateTime.now(),
                blockEnd
        ).getSeconds();

        return Math.max(seconds, 1);
    }

    private void resetAttempts(String login) {
        failedAttempts.remove(login);
        blockedUntil.remove(login);
    }
}