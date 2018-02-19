package uk.cw1998.gcd.todo.items;

public enum Priority {
    HIGH("High"), MEDIUM("Medium"), LOW("Low"), NONE("None");
    private String priority;

    Priority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return this.priority;
    }
}
