package uk.cw1998.gcd.todo.util;

import uk.cw1998.gcd.todo.items.BaseTodo;

import java.util.ArrayList;

public class ListHelper {

    public static String[] getListOfTodoTitles(ArrayList<BaseTodo> todoItems) {
        ArrayList<String> temp = new ArrayList<>();
        for (BaseTodo todo : todoItems)
            temp.add(todo.getTitle());

        temp.add("<- Back");

        String[] output = new String[temp.size()];
        output = temp.toArray(output);

        return output;
    }

    public static ArrayList<BaseTodo> getArrayListOfTodoItems(ArrayList<BaseTodo> todoItems, boolean completed) {
        ArrayList<BaseTodo> temp = new ArrayList<>();
        for (BaseTodo todo : todoItems)
            if (todo.isCompleted() == completed)
                temp.add(todo);

        return temp;
    }

}
