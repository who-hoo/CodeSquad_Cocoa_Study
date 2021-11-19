package com.example.hangman;

public class User {
    private String name;
    private String password;
    private String word;
    private int life = 10;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int checkLife() {
        return life;
    }

    public void initStatus(int digit) {
        String currentStatus = "";
        for(int i=0; i<digit; i++){
            currentStatus+='_';
        }
        this.word = currentStatus;
    }

    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        return "Name=" + name + ", Life= " + life;
    }
}
