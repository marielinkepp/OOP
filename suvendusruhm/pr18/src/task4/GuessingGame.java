package task4;
import task3.FeedbackInputChecker;

import java.util.Scanner;

/**
 * TASK 4.
 *
 * Use the FeedbackInputChecker to implement the number guessing game:
 * computer chooses a number 1..100,
 * the user starts to guess it and gets feedback like "Too small guess!" or "Too large guess!"
 * until guesses correctly.
 */
public class GuessingGame {
    public static void main(String[] args) {
        FeedbackInputChecker input = new FeedbackInputChecker(System.out, new Scanner(System.in)); // objekti loomine
        int number = (int) (Math.random() * 100 + 1); // pakutava suvalise arvu (vahemikus 1-100) genereerimine
        input.askInt("Choose a number between 1 and 100: ", i -> {return (i < number ? "Too small!" : (i > number ? "Too large!" : null)); });
        System.out.println("Correct answer!");
    }
}
