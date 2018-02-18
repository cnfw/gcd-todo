package uk.cw1998.gcd.todo.items;

import java.util.ArrayList;

public class ListTodo extends BaseTodo {

    private ArrayList<NormalTodo> checklist;

    public ListTodo(String title, String description, boolean completed) {
        super(title, description, completed);
    }

    public ListTodo(String title, String description) {
        this(title, description, false);
    }
}
