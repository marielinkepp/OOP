package task8;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TASK 8.
 *
 * Your task is to implement some infinite streams.
 * These are relatively obscure and impractical but great brain teasers.
 */
public class InfiniteStreams {
    /**
     * Returns an infinite stream of random integers (1…100).
     */
    private static Stream<Integer> randomInts() {
        // TODO: Implement
        throw new UnsupportedOperationException("Not implemented");

    }

    /**
     * Returns an infinite stream of all positive integers.
     */
    private static Stream<Integer> positiveInts() {
        // TODO: Implement
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Returns an infinite stream of squares of all positive integers.
     */
    private static Stream<Integer> positiveIntSquares() {
        // TODO: Implement
        // HINT: reuse positiveInts
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Returns an infinite stream of all prime numbers.
     */
    private static Stream<Integer> primes() {
        // TODO HARD: Implement
        // HINT: Create a helper method for checking whether a number is prime (also use a stream).
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Returns an infinite stream of all Fibonacci numbers.
     */
    private static Stream<Integer> fibonacci() {
        // TODO HARD: Implement
        // HINT: Create a helper class for a pair of consecutive Fibonacci numbers.
        throw new UnsupportedOperationException("Not implemented");
    }

    public static void main(String[] args) {
        outputInfinite("Random integers", InfiniteStreams::randomInts);
        outputInfinite("Positive integers", InfiniteStreams::positiveInts);
        outputInfinite("Positive integer squares", InfiniteStreams::positiveIntSquares);
        outputInfinite("Primes", InfiniteStreams::primes);
        outputInfinite("Fibonacci", InfiniteStreams::fibonacci);

        // TODO: Refactor this class to use the more efficient IntStream instead of Stream<Integer>.
    }

    private static <T> void outputInfinite(String prompt, Supplier<Stream<T>> streamSupplier) {
        Stream<T> stream = streamSupplier.get(); // TODO: Explain why Supplier is used here.
        String elemStr = stream
                .limit(20)
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        System.out.println(prompt + ":");
        System.out.println(elemStr + ", ...");
        System.out.println();
    }
}
