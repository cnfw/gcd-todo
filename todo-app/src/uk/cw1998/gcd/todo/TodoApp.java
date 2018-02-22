package uk.cw1998.gcd.todo;

import uk.cw1998.gcd.todo.items.BaseTodo;
import uk.cw1998.gcd.todo.items.ListTodo;
import uk.cw1998.gcd.todo.items.Priority;
import uk.cw1998.gcd.todo.util.InputHelper;
import uk.cw1998.gcd.todo.util.ListHelper;
import uk.cw1998.gcd.todo.util.XMLHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import static uk.cw1998.gcd.todo.util.PrintHelper.printStartup;
import static uk.cw1998.gcd.todo.util.PrintHelper.printTodoInformation;

public class TodoApp {

    private static InputHelper inputHelper;
    private static XMLHelper xmlHelper;

    public static final String BACK = "<- Back";

    private static ArrayList<BaseTodo> todoItems;
    private static String[] mainMenuOptions = new String[]{"New Todo", "Show due Todo's", "Show Todo's", "Show completed Todo's", "Exit"};
    private static String[] todoTypes = new String[]{"Normal Todo", "List Todo", BACK};
    private static String[] normalTodoOptions = new String[]{"Toggle completed", "Set priority", "Set due date", BACK};
    private static String[] listTodoOptions = new String[]{"Toggle completed", "Set priority", "Set due date", "Add item to checklist", "Show checklist", BACK};
    private static String[] priorityOptions = new String[]{Priority.HIGH.getPriority(), Priority.MEDIUM.getPriority(), Priority.LOW.getPriority(), Priority.NONE.getPriority(), BACK};

    public static void main(String[] args) {
        /*
         * CHANGE FILE PATH TO EXAMPLE XML FILE PROVIDED IN THE REPOSITORY
         */
        xmlHelper = new XMLHelper("C:\\Users\\hp\\IdeaProjects\\gcd-todo\\gcd-todo.xml");
        inputHelper = new InputHelper(new Scanner(System.in));
        todoItems = xmlHelper.getTodoArray();

        printStartup();

        // Start a loop that will get a main menu choice to start off
        for (int mainMenuChoice = getMainMenuChoice(); mainMenuChoice != mainMenuOptions.length; mainMenuChoice = getMainMenuChoice())
            processMainMenuChoice(mainMenuChoice);

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
                if (todoToAdd != null) {
                    todoItems.add(todoToAdd);
                    showTodo(todoToAdd);
                }
                break;
            case 2: // Show due and uncompleted
                processTodoChoice(ListHelper.getArrayListOfDueTodoItems(todoItems));
                break;
            case 3: // Show uncompleted
                processTodoChoice(ListHelper.getArrayListOfTodoItems(todoItems,false));
                break;
            case 4: // Show completed
                processTodoChoice(ListHelper.getArrayListOfTodoItems(todoItems,true));
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
        String[] listOfTodoTitles = ListHelper.getListOfTodoTitles(todoItems);
        int todoChoice = getTodoChoice(listOfTodoTitles);
        if (todoChoice < 0) {
            System.out.println("There are no Todo's in this list. Going back.");
            return;
        } else if (todoChoice == listOfTodoTitles.length)
            return;

        BaseTodo todo = todoItems.get(todoChoice - 1); // inputHelper doesn't start from 0 (to be user friendly)

        showTodo(todo);
    }

    private static void showTodo(BaseTodo todo) {
        printTodoInformation(todo);
        String[] optionsList = (todo instanceof ListTodo) ? listTodoOptions : normalTodoOptions;

        for (int todoOptionChoice = getTodoOption(optionsList); todoOptionChoice != optionsList.length; todoOptionChoice = getTodoOption(optionsList)) {
            processTodoMenuChoice(todoOptionChoice, todo);
            printTodoInformation(todo);
        }
    }

    private static void processTodoMenuChoice(int todoOptionChoice, BaseTodo todo) {
        switch (todoOptionChoice) {
            case 1: // Toggle completed
                System.out.println(((todo.isCompleted()) ? "Marking uncompleted" : "Marking completed"));
                todo.toggleCompleted();
                break;
            case 2: // Set priority
                System.out.println("Current Priority: " + todo.getPriority().getPriority());
                int priorityChoice = inputHelper.buildMenu("Choose a priority", priorityOptions);
                if (priorityChoice == priorityOptions.length) // If back is chosen
                    return;
                else
                    todo.setPriority(Priority.values()[priorityChoice - 1]);
                break;
            case 3: // Set due date
                LocalDate date;
                do {
                    String dateInput = inputHelper.getString("Enter a date in the format DD-MM-YYYY", true);
                    date = InputHelper.inputDate(dateInput);
                } while (date == null);
                todo.setDueDate(date);
                System.out.println("New due date: " + todo.getDueDate().toString());
                break;
            case 4: // Add new (if it is a ListTodo)
                if (todo instanceof ListTodo)
                    ((ListTodo) todo).addToCheckList(newNormalTodo());
                break;
            case 5: // Show checklist (if it is a ListTodo)
                if (todo instanceof  ListTodo)
                    processTodoChoice(((ListTodo) todo).getChecklist());
                break;
            default:
        }
    }

    private static int getTodoOption(String[] optionsList) {
        return inputHelper.buildMenu("Select an option for this Todo", optionsList);
    }

    private static void cleanupAndExit() {
        System.out.println("Saving Todo's");
        xmlHelper.writeTodoArrayToFile(todoItems);
        System.out.println("Todo's saved.");
        System.exit(0);
    }
}
