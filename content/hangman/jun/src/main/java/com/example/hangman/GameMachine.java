package com.example.hangman;

public class GameMachine {

    public boolean contains(Problem problem, char input) {
        return problem.hasAlphabet(input);
    }
}
