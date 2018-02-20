package uk.cw1998.gcd.todo.items;

import java.util.ArrayList;

public class ListTodo extends BaseTodo {

    private ArrayList<BaseTodo> checklist;

    public ListTodo(String title, String description, boolean completed) {
        super(title, description, completed);
        checklist = new ArrayList<>();
        tagName = "ListTodo";
    }

    public ListTodo(String title, String description) {
        this(title, description, false);
    }

    public ArrayList<BaseTodo> getChecklist() {
        return checklist;
    }

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
