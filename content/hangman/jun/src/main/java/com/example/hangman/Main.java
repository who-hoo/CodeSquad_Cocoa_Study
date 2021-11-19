package com.example.hangman;

public class Main {
    static Problems problems = new Problems();

    public static void main(String[] args) {
        int value = 3;
        String id = "아이디를을 입력해주세요";
        String name = "이름을 입력해주세요";
        String password = "비밀번호를 입력해주세요";
        User user = new User(name, password);
        problems.putProblem(value, "Apple", "Red");
        Problem problem = problems.getProblem(value);
        System.out.println(problem);

        user.initStatus(problem.getDigit());

        System.out.println(user.getWord());

        String me = "단어를 입력해주세요";
        char input = 'p';

        // ------

    }
}
