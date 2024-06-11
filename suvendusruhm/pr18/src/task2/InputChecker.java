package task2;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * TASK 2.
 *
 * Write a helper method for asking user input (an integer) from a Scanner.
 * It should ask again if the entered number does not satisfy the given conditions (checked with a lambda).
 * It should ask again if the entered text is not a number at all.
 *
 * An example run of the given program should go as follows:
 *
 * Choose a number between 1 and 100: 0
 * Choose a number between 1 and 100: 1234
 * Choose a number between 1 and 100: -10
 * Choose a number between 1 and 100: text
 * Choose a number between 1 and 100: 12
 * Wrong answer!
 */
public class InputChecker {
    private final PrintStream out;
    private final Scanner scanner;

    public InputChecker(PrintStream out, Scanner scanner) {
        this.out = out;
        this.scanner = scanner;
    }

    public int askInt(String prompt, Predicate<Integer> badPredicate) {

        while (true) { // tsükkel kestab kuni õiges vahemikus arvu sisestamiseni
            System.out.print(prompt); //  kasutajalt küsitakse sisendiks arvu
            int num = this.scanner.nextInt(); // ku isend on täisarv, siis kontrollitakse vastavust predikaadile
            if (!badPredicate.test(num)) {return num;}
            System.out.print(prompt);
            this.scanner.next();
        }
    }

    public static void main(String[] args) {
        InputChecker input = new InputChecker(System.out, new Scanner(System.in));
        int num = input.askInt("Choose a number between 1 and 100: ", i -> i < 1 || i > 100);
        System.out.println(num == 42 ? "Correct answer!" : "Wrong answer!");
    }
}
