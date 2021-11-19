package week2.day6.hangman;

public class HangMan {
  String currentHangManStack = "";
  String[] hangmanPrintArr = {
    "   ____________",
    "   |          _|_",
    "   |         /   \\",
    "   |        |     |",
    "   |         \\_ _/",
    "   |        ---|---",
    "   |           |",
    "   |          / \\ ",
    "   |         /   \\",
    "___|______"
  };

  public String[] getHangmanPrintArr() {
    return hangmanPrintArr;
  }

  public void stackUpHangMan(int life) {
    currentHangManStack = hangmanPrintArr[life] + "\n" + currentHangManStack;
    System.out.println("   ____________");
    System.out.println("   |          _|_");
    System.out.println("   |         /   \\");
    System.out.println("   |        |     |");
    System.out.println("   |         \\_ _/");
    System.out.println("   |        ---|---");
    System.out.println("   |           |");
    System.out.println("   |          / \\ ");
    System.out.println("   |         /   \\");
    System.out.println("___|______");
  }

  @Override
  public String toString() {
        return currentHangManStack;
  }
}
