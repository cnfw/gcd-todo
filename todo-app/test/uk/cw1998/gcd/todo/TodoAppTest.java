package uk.cw1998.gcd.todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.cw1998.gcd.todo.items.BaseTodo;
import uk.cw1998.gcd.todo.items.ListTodo;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TodoAppTest {

    private BaseTodo baseTodo, baseTodo2, baseTodo3, baseTodo4, checkListTodo, checkListTodo2;
    private ListTodo listTodo;

    @BeforeEach
    void setUp() {
        baseTodo = new BaseTodo("Title", "Description");
        baseTodo2 = new BaseTodo("Title2", "Description2", false, null);
        baseTodo3 = new BaseTodo("Title3", "Description3", false, LocalDate.of(2018, 2, 1));
        baseTodo4 = new BaseTodo("Title4", "Description4", false, LocalDate.of(2019, 2, 1));

        listTodo = new ListTodo("List Title", "List Description");
        checkListTodo = new BaseTodo("Checklist Title", "Description");
        checkListTodo2 = new BaseTodo("Checklist Title2", "Description2");

    }

    @Test
    void aTest() {
        assertTrue(true);
    }
}