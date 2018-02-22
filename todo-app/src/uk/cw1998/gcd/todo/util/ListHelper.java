package uk.cw1998.gcd.todo.util;

import uk.cw1998.gcd.todo.items.BaseTodo;

import java.util.ArrayList;

public class ListHelper {

    /**
     * Gets a list of {@link BaseTodo} items' titles as strings
     * Always add a "back" option to the end of the list - even if the list is empty
     * @param todoItems list of items to get the titles of
     * @return list of titles and a back option
     */
    public static String[] getListOfTodoTitles(ArrayList<BaseTodo> todoItems) {
        ArrayList<String> temp = new ArrayList<>();
        if (todoItems == null)
            return new String[]{"<- Back"};

        for (BaseTodo todo : todoItems)
            temp.add(todo.getTitle());

        temp.add("<- Back");

        String[] output = new String[temp.size()];
        output = temp.toArray(output);

        return output;
    }

    /**
     * Gets a list of {@link BaseTodo} items that are {@link BaseTodo#isDue() due} and have not already been
     * {@link BaseTodo#completed completed}
     * @param todoItems list of items to process
     * @return list of items that match conditions
     */
    public static ArrayList<BaseTodo> getArrayListOfDueTodoItems(ArrayList<BaseTodo> todoItems) {
        ArrayList<BaseTodo> temp = new ArrayList<>();
        if (todoItems == null)
            return temp;

        for (BaseTodo todo : todoItems)
            if (!todo.isCompleted() && todo.isDue())
                temp.add(todo);

        return temp;
    }

    /**
     * Gets a list of {@link BaseTodo} items that match the provided completed status
     * @param todoItems list of items to process
     * @param completed status that items in the returned list should have
     * @return list of items that match conditions
     */
    public static ArrayList<BaseTodo> getArrayListOfTodoItems(ArrayList<BaseTodo> todoItems, boolean completed) {
        ArrayList<BaseTodo> temp = new ArrayList<>();
        if (todoItems == null)
            return temp;
        for (BaseTodo todo : todoItems)
            if (todo.isCompleted() == completed)
                temp.add(todo);

        return temp;
    }
}
