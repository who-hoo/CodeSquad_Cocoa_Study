package example.throwing;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ThrowsEx {

    public static int getYear() throws NumberFormatException, InputMismatchException {
        Scanner sc = new Scanner(System.in);
        int year = Integer.parseInt(sc.nextLine());
        if (!(0 <= year && year <= 9999)) {
            throw new InputMismatchException("범위(0~9999)가 유효하지 않습니다.");
        }
        return year;
    }

    public static void printYear() throws NumberFormatException, InputMismatchException {
        try {
            int year = getYear();
            System.out.println(year);
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력");
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        printYear();
    }
}
