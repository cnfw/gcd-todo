package uk.cw1998.gcd.todo.items;

import java.util.Date;

public class NormalTodo extends BaseTodo{

    private Date dueDate;
    private Priority priority;


    public NormalTodo(String title, String description, boolean completed) {
        super(title, description, completed);
        this.priority = Priority.NONE;
    }

    public NormalTodo(String title) {
        this(title, "", false);
    }

    public NormalTodo(String title, String description) {
        this(title, description, false);
    }

    public Priority getPriority() {
        return priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
