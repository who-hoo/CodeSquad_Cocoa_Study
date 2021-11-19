package com.example.hangman;

public class Problem {
    private String answer;
    private String hint;

    public String getAnswer() {
        return answer;
    }

    public Problem(String answer, String hint) {
        this.answer = answer;
        this.hint = hint;
    }

    public int getDigit(){
        return answer.length();
    }
    public String offerHint() {
        return this.hint;
    }

    public String ofAnswer() {
        return this.answer;
    }

    public String currentState(String userWord, char inputValue) {
        String renew = "";
        for (int i = 0; i < answer.length(); i++) {
            char alpha = getAlpha(inputValue, userWord.charAt(i), answer.charAt(i));
            renew += alpha;
        }
        return renew;
    }

    public char getAlpha(char inputValue, char userWord, char answerValue) {
        return isBlank(userWord) && same(inputValue, answerValue) ? answerValue : userWord;
    }

    public boolean isBlank(char valueA) {
        return valueA == '_';
    }

    public boolean same(char valueA, char valueB) {
        return valueA == valueB;
    }

    public boolean hasAlphabet(char alpha) {
        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) == alpha) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "answer='" + answer + '\'' +
                ", hint='" + hint + '\'' +
                '}';
    }
}
