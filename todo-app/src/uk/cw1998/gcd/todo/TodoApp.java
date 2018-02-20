package uk.cw1998.gcd.todo;

import uk.cw1998.gcd.todo.items.BaseTodo;
import uk.cw1998.gcd.todo.items.ListTodo;
import uk.cw1998.gcd.todo.items.Priority;
import uk.cw1998.gcd.todo.util.InputHelper;
import uk.cw1998.gcd.todo.util.XMLHelper;

import java.util.ArrayList;
import java.util.Scanner;

public class TodoApp {

    private static InputHelper inputHelper;

    private static ArrayList<BaseTodo> todoItems;
    private static String[] mainMenuOptions = new String[]{"New Todo", "Show Todo's", "Show completed Todo's", "Exit"};
    private static String[] todoTypes = new String[]{"Normal Todo", "List Todo", "<- Back"};
    private static String[] normalTodoOptions = new String[]{"Toggle cmpleted", "Set priority", "<- Back"};
    private static String[] listTodoOptions = new String[]{"Toggle completed", "Set priority", "Add item to checklist", "Show checklist", "<- Back"};
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

    private static BaseTodo newNormalTodo() {
        String title = inputHelper.getString("Enter a title", true);
        String description = inputHelper.getString("Enter a description", false);

        return new BaseTodo(title, description);
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
                processTodoOptionChoice(todoToAdd);
                break;
            case 2: // Show (uncompleted)
                processTodoChoice(getArrayListOfTodos(false));
                break;
            case 3: // Show completed
                processTodoChoice(getArrayListOfTodos(true));
                break;
            default:
        }
    }

    private static int getMainMenuChoice() {
        return inputHelper.buildMenu("Main Menu", mainMenuOptions);
    }

    private static int getTodoChoice(String[] todoTitles) {
        return inputHelper.buildMenu("Choose a Todo", todoTitles);
    }

    private static void processTodoChoice(ArrayList<BaseTodo> todoItems) {
        String[] listOfTodoTitles = getListOfTodoTitles(todoItems);
        int todoChoice = getTodoChoice(listOfTodoTitles) - 1;
        if (todoChoice < 0) {
            System.out.println("There are no Todo's in this list. Going back.");
            return;
        } else if (todoChoice == listOfTodoTitles.length)
            return;

        BaseTodo todo = todoItems.get(todoChoice);

        printTodoInformation(todo);
            processTodoOptionChoice(todo);
    }

    private static void processTodoOptionChoice(BaseTodo todo) {
        String[] optionsList = (todo instanceof ListTodo) ? listTodoOptions : normalTodoOptions;

        for (int todoOptionChoice = getTodoOption(optionsList); todoOptionChoice != optionsList.length; todoOptionChoice = getTodoOption(optionsList)) {
            switch (todoOptionChoice) {
                case 1: // Toggle completed
                    System.out.println(((todo.isCompleted()) ? "Marking uncompleted" : "Marking completed"));
                    todo.toggleCompleted();
                    break;
                case 2: // Set priority
                    System.out.println("Current Priority: " + todo.getPriority().getPriority());
                    int priorityChoice = inputHelper.buildMenu("Choose a priority", priorityOptions);
                    if (priorityChoice == priorityOptions.length) // If back is chosen
                        continue;
                    else
                        todo.setPriority(Priority.values()[priorityChoice - 1]);
                    break;
                case 3: // Add new (if it is a ListTodo)
                    if (todo instanceof ListTodo)
                        ((ListTodo) todo).addToCheckList(newNormalTodo());
                case 4: // Show checklist (if it is a ListTodo)
                    if (todo instanceof  ListTodo)
                        processTodoChoice(((ListTodo) todo).getChecklist());
            }
            printTodoInformation(todo);
        }
    }

    private static void printTodoInformation(BaseTodo todo) {
        System.out.println("Selected Todo: " + todo.getTitle());
        System.out.println("Description: " + todo.getDescription());
        System.out.println("Completed: " + ((todo.isCompleted()) ? "Yes" : "No"));
        System.out.println("Priority: " + todo.getPriority().getPriority());
    }

    private static int getTodoOption(String[] optionsList) {
        return inputHelper.buildMenu("Select an option for this Todo", optionsList);
    }

    private static String[] getListOfTodoTitles(ArrayList<BaseTodo> todoItems) {
        ArrayList<String> temp = new ArrayList<>();
        for (BaseTodo todo : todoItems)
            temp.add(todo.getTitle());

        temp.add("<- Back");

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
        System.out.println("Saving Todo's");
        XMLHelper.writeTodoArrayToFile(todoItems);
        System.out.println("Todo's saved.");
        System.exit(0);
    }
}
