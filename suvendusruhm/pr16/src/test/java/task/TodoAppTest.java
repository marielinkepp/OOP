package task;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class TodoAppTest {

//    Create unit tests for the `TodoApp` class.
//    Make the class more easily testable by mocking the file system operations.
//    The `todo.bin` file should not be touched in the tests.
//    Instead, create a mock that can store the values in-memory (in a simple field).
//
//    Don't use mockito for this task.
//
//    Create at least the following tests:
//            1. itemIsInListAfterBeingAdded
//2. itemNotInListAfterBeingRemoved
//3. addingDuplicateItemDoesNotChangeStoredData

    public static void itemIsInListAfterBeingAdded(TodoApp ta) throws IOException {
        ta.addTodoItem("item1");
        Set<String> todosAfter = ta.getTodoList();
        assertTrue(todosAfter.contains("item1"));

    }

    public static void itemNotInListAfterBeingRemoved(TodoApp ta) throws IOException {
        ta.addTodoItem("item1");
        ta.removeTodoItem("item1");
        Set<String> todosAfter = ta.getTodoList();
        assertFalse(todosAfter.contains("item1"));

    }

    public static void addingDuplicateItemDoesNotChangeStoredData(TodoApp ta) {
        try {
            ta.addTodoItem("item1");
            Set<String> todosAfter = ta.getTodoList();
            ta.addTodoItem("item1");
            Set<String> todosAfter2 = ta.getTodoList();
            assertEquals(todosAfter, todosAfter2);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        TodoApp ta = new TodoApp();
        itemIsInListAfterBeingAdded(ta);
        itemNotInListAfterBeingRemoved(ta);
        addingDuplicateItemDoesNotChangeStoredData(ta);
    }

}
