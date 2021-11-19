package com.example.hangman.domain;

public enum RULE {
    REGISTER("등록", 1),
    LOGIN("이어하기", 2),
    QUIT("종료", 3);

    private final String description;
    private final int number;

    RULE(String description, int number) {
        this.description = description;
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return description + "(" + number + ")";
    }
}
