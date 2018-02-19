package uk.cw1998.gcd.todo;

import uk.cw1998.gcd.todo.items.BaseTodo;
import uk.cw1998.gcd.todo.items.ListTodo;
import uk.cw1998.gcd.todo.items.NormalTodo;
import uk.cw1998.gcd.todo.items.Priority;
import uk.cw1998.gcd.todo.util.InputHelper;
import uk.cw1998.gcd.todo.util.XMLHelper;

import java.util.ArrayList;
import java.util.Scanner;

public class TodoApp {

    private static InputHelper inputHelper;

    private static ArrayList<BaseTodo> todoItems;
    private static String[] mainMenuOptions = new String[]{"New Todo", "Show Todo's", "Show Completed Todo's", "Exit"};
    private static String[] todoTypes = new String[]{"Normal Todo", "List Todo", "<- Back"};
    private static String[] normalTodoOptions = new String[]{"Toggle Completed", "Set priority", "<- Back"};
    private static String[] priorityOptions = new String[]{Priority.HIGH.getPriority(), Priority.MEDIUM.getPriority(), Priority.LOW.getPriority(), Priority.NONE.getPriority(), "<- Back"};

    public static void main(String[] args) {
        inputHelper = new InputHelper(new Scanner(System.in));
        todoItems = new ArrayList<>();

        todoItems = XMLHelper.getTodoArray();
        printStartup();

        // Start a loop that will get a main menu choice to start off
        for (int mainMenuChoice = getMainMenuChoice(); mainMenuChoice != mainMenuOptions.length; mainMenuChoice = getMainMenuChoice()) {
            processMainMenuChoice(mainMenuChoice);
        }

        System.out.println("Goodbye!");
        cleanupAndExit();
    }

    private static BaseTodo newTodo() {
        switch (inputHelper.buildMenu("Choose a type of Todo", todoTypes)) {
            case 1:
                return newNormalTodo();
            case 2:
                return newListTodo();
            default:
        }
        return null;
    }

    private static NormalTodo newNormalTodo() {
        String title = inputHelper.getString("Enter a title", true);
        String description = inputHelper.getString("Enter a description", false);

        return new NormalTodo(title, description);
    }

    private static ListTodo newListTodo() {
        String title = inputHelper.getString("Enter a title", true);
        String description = inputHelper.getString("Enter a description", false);

        return new ListTodo(title, description);
    }

    private static void processMainMenuChoice(int mainMenuChoice) {
        switch (mainMenuChoice) {
            case 1: // New
                BaseTodo todoToAdd = newTodo();
                if (todoToAdd == null)
                    return;
                else
                    todoItems.add(todoToAdd);
                break;
            case 2: // Show (uncompleted)
                processTodoChoice(false);
                break;
            case 3: // Show completed
                processTodoChoice(true);
                break;
            default:
        }
    }

    private static int getMainMenuChoice() {
        return inputHelper.buildMenu("Main Menu", mainMenuOptions);
    }

    private static int getTodoChoice(boolean completed) {
        return inputHelper.buildMenu("Choose a Todo", getListOfTodoTitles(completed));
    }

    private static void processTodoChoice(boolean fromCompletedList) {
        int todoChoice = getTodoChoice(fromCompletedList) - 1;
        if (todoChoice < 0) {
            System.out.println("There are no Todo's in this list. Returning to main menu.");
            return;
        }


        ArrayList<BaseTodo> todoItems = getArrayListOfTodos(fromCompletedList);
        BaseTodo todo = getArrayListOfTodos(fromCompletedList).get(todoChoice);

        printTodoInformation(todo);

        if (todo instanceof NormalTodo)
            for (int todoOptionChoice = getNormlTodoOption(); todoOptionChoice != normalTodoOptions.length; todoOptionChoice = getNormlTodoOption()) {
                switch (todoOptionChoice) {
                    case 1: // Toggle completed
                        System.out.println(((todo.isCompleted()) ? "Marking uncompleted" : "Marking completed"));

                        todo.toggleCompleted();

                        break;
                    case 2: // Set priority
                        System.out.println("Current Priority: " + ((NormalTodo) todo).getPriority().getPriority());

                        int priorityChoice = inputHelper.buildMenu("Choose a priority", priorityOptions);
                        if (priorityChoice == priorityOptions.length) // If back is chosen
                            continue;
                        else
                            ((NormalTodo) todo).setPriority(Priority.values()[priorityChoice - 1]);

                        break;
                }
                printTodoInformation(todo);
            }
    }

    private static void printTodoInformation(BaseTodo todo) {
        System.out.println("Selected Todo: " + todo.getTitle());
        System.out.println("Description: " + todo.getDescription());
        System.out.println("Completed: " + ((todo.isCompleted()) ? "Yes" : "No"));

        if (todo instanceof NormalTodo)
            System.out.println("Priority: " + ((NormalTodo) todo).getPriority().getPriority());
    }

    private static int getNormlTodoOption() {
        return inputHelper.buildMenu("Select an option for this Todo", normalTodoOptions);
    }

    private static String[] getListOfTodoTitles(boolean completed) {
        ArrayList<String> temp = new ArrayList<>();
        for (BaseTodo todo : getArrayListOfTodos(completed))
            temp.add(todo.getTitle());

        String[] output = new String[temp.size()];
        output = temp.toArray(output);

        return output;
    }

    private static ArrayList<BaseTodo> getArrayListOfTodos(boolean completed) {
        ArrayList<BaseTodo> temp = new ArrayList<>();
        for (BaseTodo todo : todoItems)
            if (todo.isCompleted() == completed)
                temp.add(todo);

        return temp;
    }

    private static void printStartup() {
        System.out.println("===============================");
        System.out.println(" __            ___ __  __  __  \n" +
                "(_ . _  _ | _   | /  \\|  \\/  \\ \n" +
                "__)|||||_)|(-   | \\__/|__/\\__/ \n" +
                "       |");
        System.out.println("===============================");
        System.out.println("Christopher Wilkinson");
        System.out.println("Version 0 - https://github.com/cw1998/gcd-todo/");
        System.out.println("-------------------------------\n");
    }

    private static void cleanupAndExit() {
        XMLHelper.writeTodoArrayToFile(todoItems);
        System.exit(0);
    }
}
