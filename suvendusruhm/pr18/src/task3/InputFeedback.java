package task3;

/**
 * Represents a function that accepts an input value and gives feedback.
 * @param <T> type of the input value
 */
@FunctionalInterface
public interface InputFeedback<T> {
    /**
     * Returns feedback string if the given input value is invalid.
     * @param value the input value
     * @return feedback if input is invalid, or {@code null} if valid
     */
    String check(T value);
}
