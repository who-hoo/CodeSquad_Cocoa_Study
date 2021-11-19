package com.example.hangman;

public class Print {
    static StringBuilder sb = new StringBuilder();

    static void printStep1() {
        sb.setLength(0);
        sb.append("Wrong guess, try again").append("\n")
                .append("\n")
                .append("\n")
                .append("\n")
                .append("\n")
                .append("\n")
                .append("___|___")
                .append("\n");
        System.out.println(sb);
    }

    static void printStep2() {
        sb.setLength(0);
        sb.append("Wrong guess, try again").append("\n\n")
                .append("   |\n")
                .append("   |\n")
                .append("   |\n")
                .append("   |\n")
                .append("   |\n")
                .append("   |\n")
                .append("   |\n")
                .append("___|___")
                .append("\n");
        System.out.println(sb);
    }

    static void printStep3() {
        sb.setLength(0);
        sb.append("Wrong guess, try again").append("\n")
                .append("   ____________\n")
                .append("   |\n")
                .append("   |\n")
                .append("   |\n")
                .append("   |\n")
                .append("   |\n")
                .append("   |\n")
                .append("   |\n")
                .append("___|___")
                .append("\n");
        System.out.println(sb);
    }

    static void printStep4() {
        sb.setLength(0);
        sb.append("Wrong guess, try again").append("\n")
                .append("   ____________").append("\n")
                .append("   |          _|_").append("\n")
                .append("   |         /   \\").append("\n")
                .append("   |        |     |").append("\n")
                .append("   |         \\_ _/").append("\n")
                .append("   |").append("\n")
                .append("   |").append("\n")
                .append("   |").append("\n")
                .append("___|___");
        System.out.println(sb);
    }

    static void printStep5() {
        sb.setLength(0);
        sb.append("Wrong guess, try again").append("\n").append("\n")
                .append("   ____________").append("\n").append("\n")
                .append("   |          _|_").append("\n")
                .append("   |         /   \\").append("\n")
                .append("   |        |     |").append("\n")
                .append("   |         \\_ _/").append("\n")
                .append("   |           |").append("\n")
                .append("   |           |").append("\n")
                .append("   |").append("\n")
                .append("___|___");
        System.out.println(sb);
    }

    static void printStep6() {
        sb.setLength(0);
        sb.append("Wrong guess, try again").append("\n")
                .append("   ____________").append("\n")
                .append("   |          _|_").append("\n")
                .append("   |         /   \\").append("\n")
                .append("   |        |     |").append("\n")
                .append("   |         \\_ _/").append("\n")
                .append("   |           |").append("\n")
                .append("   |           |").append("\n")
                .append("   |          / \\ ").append("\n")
                .append("___|___      /   \\");
        System.out.println(sb);
    }

    static void printStep7() {
        sb.setLength(0);
        sb.append("GAME OVER!").append("\n")
                .append("   _____________").append("\n")
                .append("   |          _ㅣ_").append("\n")
                .append("   |         /   \\").append("\n")
                .append("   |        |     |").append("\n")
                .append("   |         \\_ _/").append("\n")
                .append("   |          _|_").append("\n")
                .append("   |         / | \\").append("\n")
                .append("   |          / \\ ").append("\n")
                .append("___|___      /   \\").append("\n")
                .append("GAME OVER! The word was ");
        System.out.println(sb);
    }

    public static void main(String[] args) {
        printStep1();
        System.out.println("=============================================================================");
        printStep2();
        System.out.println("=============================================================================");
        printStep3();
        System.out.println("=============================================================================");
        printStep4();
        System.out.println("=============================================================================");
        printStep5();
        System.out.println("=============================================================================");
        printStep6();
        System.out.println("=============================================================================");
        printStep7();
    }
}