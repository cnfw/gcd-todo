package uk.cw1998.gcd.todo.items;


import java.time.LocalDate;

public class BaseTodo {

    private long id;
    private String title, description;
    private boolean completed;
    private LocalDate dueDate;
    private Priority priority;
    String tagName;

    public BaseTodo(String title, String description, boolean completed, LocalDate dueDate) {
        this.id = System.currentTimeMillis() / 1000L;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.dueDate = dueDate;
        this.priority = Priority.NONE;
        this.tagName = "BaseTodo";
    }

    public BaseTodo(String title, String description) {
        this(title, description, false, null);
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

    /**
     * Compares the system's current time with the time stored with this object
     * @return false if object has no due date or if due date is in the future, otherwise true
     */
    public boolean isDue() {
        LocalDate now = LocalDate.now();
        return dueDate != null && (dueDate.isBefore(now));
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public void setDescription(String newDescription) {
        description = newDescription;
    }

    /**
     * Swap {@link #completed completed} status
     * @return new completed status
     */
    public boolean toggleCompleted() {
        completed = !completed;
        return completed;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Gets a string of the current date in UK format if it exists
     * @return current {@link #dueDate due date} in UK format if it exists
     */
    public String getUKDate() {
        if (dueDate == null)
            return "No Due Date";
        return dueDate.getDayOfMonth() + "-" + dueDate.getMonthValue() + "-" + dueDate.getYear();
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getTagName() {
        return tagName;
    }
}
