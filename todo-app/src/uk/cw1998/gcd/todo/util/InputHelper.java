package uk.cw1998.gcd.todo.util;

import java.util.Scanner;

public class InputHelper {

    private Scanner scanner;

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public int buildMenu(String title, String[] options) {
        if (options.length == 0)
            return -1;

        if(!title.equals(""))
            System.out.println("===== " + title.toUpperCase() + " =====");

        System.out.println("Choose from one of these options:");

        for (int i = 0; i < options.length; i++)
            System.out.println("[" + (i + 1) + "] " + options[i]);

        if (options.length == 1)
            return 1;

        int choice;
        boolean validInput = false;

        System.out.print("# ");

        String errorMessage = "Something wasn't right with that last input.\nPlease enter a number between 1 and " + options.length + ".\n# ";

        do {
            while (!scanner.hasNextInt()) {
                System.err.print(errorMessage);
                scanner.next();
            }

            choice = scanner.nextInt();

            if (choice >= 1 && choice <= options.length)
                validInput = true;
            else
                System.err.print(errorMessage);
        } while (!validInput);

        // Clear the line break for the next input
        scanner.nextLine();

        return choice;

    }

    public String getString(String promptToUser) {
        return getString(promptToUser, false);
    }

    public String getString(String promptToUser, boolean required) {
        System.out.println(promptToUser);
        System.out.print((required) ? ">* " : "> ");

        String output;

        // Keep looping until a valid input is entered
        while(true) {
            output = scanner.nextLine();

            if (output.equals("") && required) {
                System.out.print("This is required, please enter a value.\n> ");
                continue;
            } else if (output.equals("")) {
                System.out.print("Are you sure? Press enter again to leave input blank.\n> ");
                return scanner.nextLine();
            } else
                return output;
        }
    }

}
