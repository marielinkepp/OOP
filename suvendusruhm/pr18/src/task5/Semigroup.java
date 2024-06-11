package task5;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

/**
 * TASK 5.
 *
 * Write a very generic static method for combining a list of elements with the given binary operation.
 * Example usages and outputs are given in the main method.
 */
public class Semigroup {


    public static void main(String[] args) {

        List<Integer> ints = List.of(1, 3, 10, -5);
        output("int sum",     combine(ints, (l, r) -> l + r)); // should be 9
        output("int product", combine(ints, (l, r) -> l * r)); // should be -150
        output("int min",     combine(ints, Math::min));       // should be -5
        output("int max",     combine(ints, Math::max));       // should be 10

        List<String> strings = List.of("foo", "bar", "baz");
        output("string concat", combine(strings, (l, r) -> l + r));        // should be "foobarbaz"
        output("string join",   combine(strings, (l, r) -> l + ", " + r)); // should be "foo, bar, baz"*/
    }

    private static <T> T combine(List<T> list, BiFunction<T, T, T> operation) {
        T result = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            result = operation.apply(result, list.get(i));
        }
        return result;
    }

    private static <T> T combineHard(List<T> list, BiFunction<T, T, T> operation) {
       return null;  // TODO HARD: Reimplement the method using streams
    }



    private static <T> void output(String prompt, T obj) {
        System.out.print(prompt + ": ");
        System.out.println(obj);
    }
}
