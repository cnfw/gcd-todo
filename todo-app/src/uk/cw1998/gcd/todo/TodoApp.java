package uk.cw1998.gcd.todo;

import uk.cw1998.gcd.todo.items.BaseTodo;
import uk.cw1998.gcd.todo.items.ListTodo;
import uk.cw1998.gcd.todo.items.NormalTodo;
import uk.cw1998.gcd.todo.util.InputHelper;
import uk.cw1998.gcd.todo.util.XMLHelper;

import java.util.ArrayList;
import java.util.Scanner;

public class TodoApp {

    private static InputHelper inputHelper;

    private static ArrayList<BaseTodo> todoItems;
    private static String[] mainMenuOptions = new String[]{"New Todo", "Show Todo's", "Show Completed Todo's", "Exit"};
    private static String[] todoTypes = new String[]{"Normal Todo", "List Todo", "<- Back"};

    public static void main(String[] args) {
        inputHelper = new InputHelper(new Scanner(System.in));
        todoItems = new ArrayList<>();

        printStartup();

        XMLHelper.getTodoArray();

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
                break;
            case 3: // Show completed
                break;
            default:
        }
    }

    private static int getMainMenuChoice() {
        return inputHelper.buildMenu("Main Menu", mainMenuOptions);
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

        System.exit(0);
    }
}
