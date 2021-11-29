package example.throwing;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ThrowEx {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            int year = Integer.parseInt(sc.nextLine());
            if (!(0 <= year && year <= 9999)) {
                throw new InputMismatchException("범위(0~9999)가 유효하지 않습니다.");
            }
            System.out.println(year);
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException!");
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
    }
}
