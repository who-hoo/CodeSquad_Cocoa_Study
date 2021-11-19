package week2.day6.hangman;

public class Problem {
  String answer;
  String hint;

  public Problem(String answer, String hint) {
    this.answer = answer;
    this.hint = hint;
  }

  public String getAnswer() {
    return answer;
  }

  public String getHint() {
    return hint;
  }
}
