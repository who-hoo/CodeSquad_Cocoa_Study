package week2.day6.hangman;

public class User {
  private String name;
  private int life = 10;
  private int answerCount = 0;

  public User(String name) {
    this.name = name;
  }

  public void minusLife() {
    life--;
  }

  public String getName() {
    return name;
  }

  public int getLife() {
    return life;
  }

  public int getAnswerCount() {
    return answerCount;
  }

  public void answerCountUp() {
    answerCount++;
  }

  public void initializeLife() {
    life = 10;
  }
}
