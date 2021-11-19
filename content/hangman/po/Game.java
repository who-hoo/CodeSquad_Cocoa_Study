package week2.day6.hangman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class Game {
  private ProblemStore problemStore;
  private User user;
  private Scanner sc = new Scanner(System.in);
  private Print print = new Print();
  private HangMan hangMan = new HangMan();
  private List<String> usedAlphabets;

  public void init() {
    user = new User("p.o");
    problemStore = new ProblemStore();
    List<Problem> problems = loadProblems("hangman-words");
    problemStore.getProblems().addAll((List) problems);
  }

  public void start() {
    user.initializeLife();
    usedAlphabets = new ArrayList<>();
    Problem problem = problemStore.getRandomProblem();
    String answer = problem.getAnswer();
    String input;
    int userLife;
    int neededAnswerCount = getNeededAnswerCount(answer);
    int currentAnswerCount = 0;

    print.addAnswer(answer);
    print.makeAnswerBlank();
    print.printAnswerBlank();
    print.printUserLife(user);

    while (user.getLife() != 0) {
      print.printUsedAlphabet(usedAlphabets);
      System.out.print("알파벳 입력 [1:힌트 / 2:다른 문제 / 0:종료] >>");
      input = sc.next();

      if (input.equals("1")) {
        String hint = problem.getHint();
        print.printHint(hint);
        print.printAnswerBlank();
        print.printUserLife(user);
        continue;
      }

      if (input.equals("2")) {
        start();
        return;
      }

      if (input.equals("0")) {
        System.out.println("프로그램 종료");
        return;
      }

      if (checkIsUsedAlphabet(input)) {
        System.out.println("이미 입력된 알파벳입니다. 다른 알파벳을 입력해주세요.\n");
        continue;
      }
      usedAlphabets.add(input);

      if (!answer.contains(input)) {
        System.out.println("[X]입력하신 알파벳이 없습니다.");
        user.minusLife();
        userLife = user.getLife();
        hangMan.stackUpHangMan(userLife);
        print.printHangman(hangMan);
        print.printAnswerBlank();
        print.printUserLife(user);

        continue;
      }

      if(++currentAnswerCount == neededAnswerCount) {
        System.out.println("[알파벳을 모두 맞췄습니다. 정답은 " + answer + "입니다]");
        break;
      }
      System.out.println("[O]알파벳을 찾았습니다.\n");
      print.makeBlankVisibleAlphabet(input);
      print.printAnswerBlank();
      print.printUserLife(user);
    }

    if (checkDoNextGame()) {
      start();
      return;
    };

    System.out.println("프로그램 종료");

  }//start()

  private boolean checkIsUsedAlphabet(String input) {
    return usedAlphabets.contains((String) input);
  }

  private int getNeededAnswerCount(String answer) {
    String[] alphabetArr = answer.split("");
    List<String> alphabetList = new ArrayList<>(Arrays.asList(alphabetArr));
    Set<String> alphabetSet = new HashSet<>(alphabetList);
    return alphabetSet.size();
  }

  private boolean checkDoNextGame() {
    System.out.println("Game Over. 다시 하시겠습니까?(y/Y) 그만하시려면 아무키나 입력해주세요.");
    sc.nextLine();
    String s = sc.nextLine().toUpperCase();
    return s.equals("Y") ? true : false;
  }

  public void showResult() {

  }

  private List<Problem> loadProblems(String fileName) {
    List<Problem> fileData = new ArrayList<>();

    try {
      String path = this.getClass().getResource("").getPath();
      System.out.println(path);
      FileReader fr = new FileReader(path + "static/" + fileName + ".txt", Charset.forName("UTF-8"));
      BufferedReader br = new BufferedReader(fr);
      String singleLine = "";

      while ((singleLine = br.readLine()) != null) {
        fileData.add(createNewProblem(singleLine));
      }
    } catch (IOException e) {
      System.out.println("문제 파일을 읽어오는 도중 에러가 발생하였습니다.");
    }

    return fileData;
  }

  private Problem createNewProblem(String singleLine) {
    String[] splitLine = singleLine.split(":");
    return new Problem(splitLine[0], splitLine[1]);
  }
}
