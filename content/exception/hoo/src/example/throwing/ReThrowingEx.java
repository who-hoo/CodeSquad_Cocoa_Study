package example.throwing;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ReThrowingEx {
    private static int year;

    public static int getYear() throws NumberFormatException, InputMismatchException {
        Scanner sc = new Scanner(System.in);
        try {
            int inputYear = Integer.parseInt(sc.nextLine());
            if (!(0 <= inputYear && inputYear <= 9999)) {
                throw new InputMismatchException("범위(0~9999)가 유효하지 않습니다.");
            }
            return inputYear;
        } catch (NumberFormatException e) {
            year = 2021;
            throw new NumberFormatException("NumberFormatException! 현재 년도로 초기화합니다.");
        } catch (InputMismatchException e) {
            year = 2021;
            throw new InputMismatchException("범위(0~9999)가 유효하지 않습니다. 현재 년도로 초기화합니다.");
        }
    }

    public static void printYear() {
        try {
            year = getYear();
            System.out.println(year);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            System.out.println(year);
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
            System.out.println(year);
        }
    }

    public static void main(String[] args) {
        printYear();
    }
}
