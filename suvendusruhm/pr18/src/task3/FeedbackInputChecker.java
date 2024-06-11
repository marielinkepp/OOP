package task3;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * TASK 3.
 *
 * This task is an extension of task 2. Now we want to
 * show the user error messages if the input is bad but
 * still continue asking until satisfied.
 *
 * An example run of the given program should go as follows:
 *
 * Choose a number between 1 and 100: 0
 * Too small!
 * Choose a number between 1 and 100: 1234
 * Too large!
 * Choose a number between 1 and 100: -10
 * Too small!
 * Choose a number between 1 and 100: text
 * Not a number!
 * Choose a number between 1 and 100: 12
 * Wrong answer!
 */
public class FeedbackInputChecker {
    private final PrintStream out;
    private final Scanner scanner;

    public FeedbackInputChecker(PrintStream out, Scanner scanner) {
        this.out = out;
        this.scanner = scanner;
    }

    public int askInt(String prompt, InputFeedback<Integer> inputFeedback) {

        while (true) { // tsükkel kestab kuni õiges vahemikus arvu sisestamiseni
            try {
                this.out.print(prompt); //  kasutajalt küsitakse sisendiks arvu
                int num = this.scanner.nextInt(); // kui sisend on täisarv, siis kontrollitakse vastavust predikaadile
                if (inputFeedback.check(num) == null) {return num;}
                this.out.println(inputFeedback.check(num));
            } catch (Exception e) {
                this.out.println("Not a number!");
                this.scanner.next();
            }
        }
    }

    public static void main(String[] args) {
        FeedbackInputChecker input = new FeedbackInputChecker(System.out, new Scanner(System.in));
        int num = input.askInt("Choose a number between 1 and 100: ", i -> {return (i < 1 ? "Too small!" : (i > 100 ? "Too large!" : null)); });
        System.out.println(num == 42 ? "Correct answer!" : "Wrong answer!");
    }
}
