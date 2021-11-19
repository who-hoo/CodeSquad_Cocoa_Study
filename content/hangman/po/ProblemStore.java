package week2.day6.hangman;

import java.util.*;

public class ProblemStore {
  private List<Problem> problems = new ArrayList<>();

  public Problem getRandomProblem() {
    Random random = new Random();
    return problems.get(random.nextInt(problems.size()));
  }

  public List<Problem> getProblems() {
    return problems;
  }
}
