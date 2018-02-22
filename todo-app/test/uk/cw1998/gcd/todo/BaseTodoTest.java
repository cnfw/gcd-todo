package uk.cw1998.gcd.todo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.cw1998.gcd.todo.items.BaseTodo;
import uk.cw1998.gcd.todo.items.Priority;

import java.time.LocalDate;

class BaseTodoTest {

    private LocalDate localDate =  LocalDate.of(2018, 2, 22);

    private BaseTodo baseTodo;

    @BeforeEach
    void setUp() {
        baseTodo = new BaseTodo("Name", "Description", false, localDate);
    }

    @Test
    void getTitle_correctOutput() {
        Assertions.assertEquals("Name", baseTodo.getTitle());
    }

    @Test
     void getDescription_correctOutput() {
        Assertions.assertEquals("Description", baseTodo.getDescription());
    }

    @Test
     void isCompleted_correctOutput() {
        Assertions.assertEquals(false, baseTodo.isCompleted());
    }

    @Test
     void setTitle_normalTitle_correctlySets() {
        String newTitle = "A new title";
        baseTodo.setTitle(newTitle);
        Assertions.assertEquals(newTitle, baseTodo.getTitle());
    }

    @Test
     void setTitle_blankTitle_correctlySets() {
        String newTitle = "";
        baseTodo.setTitle(newTitle);
        Assertions.assertEquals(newTitle, baseTodo.getTitle());
    }

    @Test
     void setTitle_nullTitle_setsTitleToEmptyString() {
        baseTodo.setTitle(null);
        Assertions.assertEquals("", baseTodo.getTitle());
    }

    @Test
     void setDescription_normalDescription_correctlySets() {
        String newDescription = "A new description";
        baseTodo.setTitle(newDescription);
        Assertions.assertEquals(newDescription, baseTodo.getTitle());
    }

    @Test
     void setDescription_blankDescription_correctlySets() {
        String newDescription = "";
        baseTodo.setDescription(newDescription);
        Assertions.assertEquals(newDescription, baseTodo.getDescription());
    }

    @Test
     void setDescription_nullDescription_setsDescriptionToEmptyString() {
        baseTodo.setDescription(null);
        Assertions.assertEquals("", baseTodo.getDescription());
    }

    @Test
     void toggleCompleted_correctlySwitches() {
        boolean previousState = baseTodo.isCompleted();
        baseTodo.toggleCompleted();

        Assertions.assertNotEquals(previousState, baseTodo.isCompleted());
    }

    @Test
     void toggleCompleted_returnsNewState() {
        boolean previousState = baseTodo.isCompleted();

        Assertions.assertNotEquals(previousState, baseTodo.toggleCompleted());
    }

    @Test
     void getPriority_returnsDefaultPriority() {
        Assertions.assertEquals(Priority.NONE, baseTodo.getPriority());
    }

    @Test
     void getDueDate_correctOutput() {
        Assertions.assertEquals(localDate, baseTodo.getDueDate());
    }

    @Test
     void getDueDate_noDueDate_returnsNull() {
        BaseTodo testTodo = new BaseTodo("", "");
        Assertions.assertNull(testTodo.getDueDate());
    }

    @Test
     void getUKDate_dueDateNotNull_returnsDate() {
        Assertions.assertEquals("22-2-2018", baseTodo.getUKDate());
    }

    @Test
     void getUKDate_dueDateNull_returnsCorrectText() {
        BaseTodo testTodo = new BaseTodo("", "");
        String expected = "No Due Date";
        Assertions.assertEquals(expected, testTodo.getUKDate());
    }

    @Test
     void setPriority_correctlySets() {
        Priority previousValue = baseTodo.getPriority();
        baseTodo.setPriority(Priority.HIGH);
        Assertions.assertEquals(Priority.HIGH, baseTodo.getPriority());
        Assertions.assertNotEquals(previousValue, baseTodo.getPriority());
    }

    @Test
     void setPriority_nullProvided_setsToNone() {
        baseTodo.setPriority(null);
        Assertions.assertEquals(Priority.NONE, baseTodo.getPriority());
    }

    @Test
     void setDueDate_normalDate_setsCorrectly() {
        LocalDate previousValue = baseTodo.getDueDate();
        baseTodo.setDueDate(LocalDate.of(2018, 1, 1));
        Assertions.assertEquals(LocalDate.of(2018, 1, 1), baseTodo.getDueDate());
        Assertions.assertNotEquals(previousValue, baseTodo.getDueDate());
    }

    @Test
     void setDueDate_nullProvded_setsToNull() {
        baseTodo.setDueDate(null);

        Assertions.assertNull(baseTodo.getDueDate());
    }

    @Test
     void getTagName_correctOutput() {
        Assertions.assertEquals("BaseTodo", baseTodo.getTagName());
    }
}
