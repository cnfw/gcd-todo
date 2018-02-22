package uk.cw1998.gcd.todo.items;

public enum Priority {
    HIGH("High"), MEDIUM("Medium"), LOW("Low"), NONE("None");
    private String name;

    Priority(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
