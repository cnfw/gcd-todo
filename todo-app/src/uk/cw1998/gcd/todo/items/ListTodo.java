package uk.cw1998.gcd.todo.items;

import java.time.LocalDate;
import java.util.ArrayList;

public class ListTodo extends BaseTodo {

    private ArrayList<BaseTodo> checklist;

    public ListTodo(String title, String description, boolean completed, LocalDate dueDate) {
        super(title, description, completed, dueDate);
        checklist = new ArrayList<>();
        tagName = "ListTodo";
    }

    public ListTodo(String title, String description, boolean completed) {
        this(title, description, completed, null);
    }

    public ListTodo(String title, String description) {
        this(title, description, false, null);
    }

    public ListTodo(BaseTodo baseTodo) { // For converting base to list
        this(baseTodo.getTitle(), baseTodo.getDescription(), baseTodo.isCompleted(), baseTodo.getDueDate());
    }

    public ArrayList<BaseTodo> getChecklist() {
        return checklist;
    }

    /**
     * Get an {@link ArrayList} of {@link BaseTodo} objects from the {@link #checklist} with the status matching
     * {@param completed} @param completed status that a {@link BaseTodo} object must have to be included in the output
     * @return list of {@link BaseTodo} objects
     */
    public ArrayList<BaseTodo> getChecklistItems(boolean completed) {
        ArrayList<BaseTodo> temp = new ArrayList<>();
        for (BaseTodo todo : checklist)
            if (todo.isCompleted() == completed)
                temp.add(todo);

        return temp;
    }

    public void addToCheckList(BaseTodo todo) {
        checklist.add(todo);
    }
}
