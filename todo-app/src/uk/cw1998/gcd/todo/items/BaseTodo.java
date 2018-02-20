package uk.cw1998.gcd.todo.items;


import java.util.Date;

public class BaseTodo {

    private long id;
    private String title, description;
    private boolean completed;
    private Date dueDate;
    private Priority priority;
    String tagName;

    public BaseTodo(String title, String description, boolean completed) {
        this.id = System.currentTimeMillis() / 1000L;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.priority = Priority.NONE;
        this.tagName = "BaseTodo";
    }

    public BaseTodo(String title, String description) {
        this(title, description, false);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public void setDescription(String newDescription) {
        description = newDescription;
    }

    public boolean toggleCompleted() {
        completed = !completed;
        return completed;
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

    public String getTagName() {
        return tagName;
    }

}
