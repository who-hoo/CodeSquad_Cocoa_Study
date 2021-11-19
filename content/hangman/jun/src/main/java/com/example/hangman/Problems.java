package com.example.hangman;

import java.util.HashMap;
import java.util.Map;

public class Problems {
    private Map<Integer, Problem> store = new HashMap<>();

    public Problem getProblem(int value) {
        return store.get(value);
    }

    public void putProblem(int number, String answer, String hint){
        store.put(number, new Problem(answer, hint));
    }
}
