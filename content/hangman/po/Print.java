package week2.day6.hangman;

import java.util.Collections;
import java.util.List;

public class Print {

  String answer;
  String answerBlank;

  public void printUserLife(User user) {
    System.out.println("(남은 기회 " + user.getLife() + "회)");
  }

  public void printHangman(HangMan hangMan) {
    System.out.println(hangMan);
  }

  public void printAnswerBlank() {
    System.out.print("문제 :   " + answerBlank + "           ");
  }

  public void addAnswer(String answer) {
    this.answer = answer;
  }

  public void makeBlankVisibleAlphabet(String alphabet) {
    int index = 0;
    String[] dividedAnswerBlank = divideSomeWord(answerBlank, " ");

    while ((index = answer.indexOf(alphabet, index)) > -1) {
      dividedAnswerBlank[index] = alphabet;
      index++;
    }

    answerBlank = String.join(" ", dividedAnswerBlank);
  }

  public String[] divideSomeWord(String word, String delimeter) {
    return word.split(delimeter);
  }

  public void makeAnswerBlank() {
    answerBlank = "_ ".repeat(answer.length());
    answerBlank = answerBlank.substring(0, answerBlank.length() - 1);
  }

  public void printHint(String hint) {
    System.out.println("*** 힌트 : " + hint + " ***");
  }

  public void printUsedAlphabet(List<String> usedAlphabets) {
    StringBuilder sb = new StringBuilder();
    sb.append("사용된 알파벳 :");

    Collections.sort(usedAlphabets);
    for (String alphabet : usedAlphabets) {
      sb.append(" [" + alphabet + "]");
    }

    System.out.println(sb.toString());
  }
}
