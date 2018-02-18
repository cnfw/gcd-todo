package uk.cw1998.gcd.todo.items;


public abstract class BaseTodo {

    private long id;
    private String title, description;
    private boolean completed;

    public BaseTodo(String title, String description, boolean completed) {
        this.id = System.currentTimeMillis() / 1000L;
        this.title = title;
        this.description = description;
        this.completed = completed;
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

}
