package uk.cw1998.gcd.todo.items;

import java.util.Date;

public class NormalTodo extends BaseTodo{

    private Date dueDate;
    private Priority priority;


    public NormalTodo(String title, String description, boolean completed) {
        super(title, description, completed);
    }

    public NormalTodo(String title) {
        this(title, "", false);
    }

    public NormalTodo(String title, String description) {
        this(title, description, false);
    }
}
