package uk.cw1998.gcd.todo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.cw1998.gcd.todo.items.BaseTodo;
import uk.cw1998.gcd.todo.items.ListTodo;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

class ListTodoTest {

    private LocalDate localDate = LocalDate.of(2018, 2, 22);

    private ListTodo listTodo;
    private BaseTodo baseTodo, baseTodo2, baseTodo3;

    @BeforeEach
    void setUp() {
        listTodo = new ListTodo("Title", "Description", false, localDate);
        baseTodo = new BaseTodo("","");
        baseTodo2 = new BaseTodo("", "", true, null);
        baseTodo3 = new BaseTodo("","");

        listTodo.addToCheckList(baseTodo);
        listTodo.addToCheckList(baseTodo2);
        listTodo.addToCheckList(baseTodo3);
    }

    @Test
    void getChecklist_checklistContainsItems() {
        Assertions.assertTrue(listTodo.getChecklist().contains(baseTodo));
        Assertions.assertTrue(listTodo.getChecklist().contains(baseTodo2));
        Assertions.assertTrue(listTodo.getChecklist().contains(baseTodo3));
    }

    @Test
    void getChecklistItems_itemsNotCompleted_returnsCorrectResults() {
        ArrayList<BaseTodo> items = listTodo.getChecklistItems(false);

        Assertions.assertTrue(items.contains(baseTodo));
        Assertions.assertFalse(items.contains(baseTodo2));
        Assertions.assertTrue(items.contains(baseTodo3));
    }

    @Test
    void getChecklistItems_itemsCompleted_returnsCorrectResults() {
        ArrayList<BaseTodo> items = listTodo.getChecklistItems(true);

        Assertions.assertFalse(items.contains(baseTodo));
        Assertions.assertTrue(items.contains(baseTodo2));
        Assertions.assertFalse(items.contains(baseTodo3));
    }

    @Test
    void getChecklistItems_noItemsCompleted_returnsEmptyList() {
        baseTodo2.toggleCompleted(); // Set this to false
        ArrayList<BaseTodo> items = listTodo.getChecklistItems(true);

        Assertions.assertTrue(items.isEmpty());
    }

    @Test
    void addToCheckList_addItemAlreadyInList_doesNotAdd() {
        int previousSize = listTodo.getChecklist().size();
        listTodo.addToCheckList(baseTodo);

        Assertions.assertEquals(previousSize, listTodo.getChecklist().size());
    }

    @Test
    void addToCheckList_addNewItem_addsSuccessfully() {
        BaseTodo newTodo = new BaseTodo("","");
        boolean newTodoWasInChecklist = listTodo.getChecklist().contains(newTodo); // False

        listTodo.addToCheckList(newTodo);
        Assertions.assertNotEquals(newTodoWasInChecklist, listTodo.getChecklist().contains(newTodo));
    }

    @Test
    void addToCheckList_addNull_doesNotAdd() {
        int previousSize = listTodo.getChecklist().size();
        listTodo.addToCheckList(null);

        Assertions.assertEquals(previousSize, listTodo.getChecklist().size());
    }
}
