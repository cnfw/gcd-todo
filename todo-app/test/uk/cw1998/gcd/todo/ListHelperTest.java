package uk.cw1998.gcd.todo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.cw1998.gcd.todo.items.BaseTodo;
import uk.cw1998.gcd.todo.util.ListHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class ListHelperTest {

    private ArrayList<BaseTodo> todoItems;
    private BaseTodo baseTodo, baseTodo2, baseTodo3;

    @BeforeEach
    void setUp() {
        LocalDate pastDate = LocalDate.of(2018, 1, 1);
        LocalDate futureDate = LocalDate.of(3000, 1, 1); // Yeah, this will need changed in a thousand years
        todoItems = new ArrayList<>();

        baseTodo = new BaseTodo("Title", "Description", true, pastDate);
        baseTodo2 = new BaseTodo("Title 2", "Description2", false, futureDate);
        baseTodo3 = new BaseTodo("Title 3", "Description3");

        todoItems.add(baseTodo);
        todoItems.add(baseTodo2);
        todoItems.add(baseTodo3);
    }

    @Test
    void getListOfTodoTitles_returnsCorrectList() {
        String[] expected = new String[]{"Title", "Title 2", "Title 3", "<- Back"};

        Assertions.assertArrayEquals(expected, ListHelper.getListOfTodoTitles(todoItems));
    }

    @Test
    void getListOfTodoTitles_nullInput_returnsEmptyList() {
        String[] expected = new String[]{"<- Back"};

        Assertions.assertArrayEquals(expected, ListHelper.getListOfTodoTitles(null));
    }

    @Test
    void getListOfTodoTitles_emptyList_returnsEmptyList() {
        String[] expected = new String[]{"<- Back"};

        Assertions.assertArrayEquals(expected, ListHelper.getListOfTodoTitles(new ArrayList<>()));
    }

    @Test
    void getArrayListOfDueTodoItems_returnsCorrectList() {
        ArrayList<BaseTodo> output = ListHelper.getArrayListOfDueTodoItems(todoItems);

        Assertions.assertFalse(output.contains(baseTodo));
        Assertions.assertFalse(output.contains(baseTodo2));
        Assertions.assertFalse(output.contains(baseTodo3));
    }

    @Test
    void getArrayListOfDueTodoItems_oneDueItem_returnsCorrectList() {
        baseTodo.toggleCompleted(); // Toggle completed so it will be marked as due
        ArrayList<BaseTodo> output = ListHelper.getArrayListOfDueTodoItems(todoItems);

        Assertions.assertTrue(output.contains(baseTodo));
        Assertions.assertFalse(output.contains(baseTodo2));
        Assertions.assertFalse(output.contains(baseTodo3));
    }

    @Test
    void getArrayListOfDueTodoItems_nullInput_returnsEmptyArrayList() {
        Assertions.assertTrue(ListHelper.getArrayListOfDueTodoItems(null).isEmpty());
    }

    @Test
    void getArrayListOfDueTodoItems_emptyInput_returnsEmptyArrayList() {
        Assertions.assertTrue(ListHelper.getArrayListOfDueTodoItems(new ArrayList<>()).isEmpty());
    }

    @Test
    void getArrayListOfTodoItems_completed_returnsCorrectList() {
        Assertions.assertTrue(ListHelper.getArrayListOfTodoItems(todoItems, true).contains(baseTodo));
    }

    @Test
    void getArrayListOfTodoItems_notCompleted_returnsCorrectList() {
        ArrayList<BaseTodo> output = ListHelper.getArrayListOfTodoItems(todoItems, false);

        Assertions.assertFalse(output.contains(baseTodo));
        Assertions.assertTrue(output.contains(baseTodo2));
        Assertions.assertTrue(output.contains(baseTodo3));
    }

    @Test
    void getArrayListOfTodoItems_nullInput_returnsEmptyList() {
        Assertions.assertTrue(ListHelper.getArrayListOfTodoItems(null, true).isEmpty());
    }

    @Test
    void getArrayListOfTodoItems_emptyInput_returnsEmptyList() {
        Assertions.assertTrue(ListHelper.getArrayListOfTodoItems(new ArrayList<>(), true).isEmpty());
    }
}
