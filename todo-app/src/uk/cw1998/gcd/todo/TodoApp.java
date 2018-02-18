package uk.cw1998.gcd.todo;

import uk.cw1998.gcd.todo.items.BaseTodo;
import uk.cw1998.gcd.todo.util.InputHelper;

import java.util.ArrayList;
import java.util.Scanner;

public class TodoApp {

    private static InputHelper inputHelper;

    private static ArrayList<BaseTodo> todoItems;
    private static String[] mainMenuOptions = new String[]{"New Todo", "Show a Todo List", "Manage Lists", "Exit"};

    public static void main(String[] args) {
        inputHelper = new InputHelper(new Scanner(System.in));
        todoItems = new ArrayList<>();

        printStartup();

        // Start a loop that will get a main menu choice to start off
        for (int mainMenuChoice = getMainMenuChoice(); mainMenuChoice != mainMenuOptions.length; mainMenuChoice = getMainMenuChoice()) {
            switch (mainMenuChoice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    cleanupAndExit();
                    break;
                default:
            }
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
