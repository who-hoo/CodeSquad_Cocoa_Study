package com.example.hangman;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Users {
    private final List<User> user = new ArrayList<>();

    public Optional<User> findUserByNameAndPassword(String name, String password) {
        return user.stream()
                .filter(user -> user.getName().equals(name) &&
                        user.getPassword().equals(password))
                .findAny();
    }
}
