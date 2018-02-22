package uk.cw1998.gcd.todo.util;

import java.time.LocalDate;
import java.util.Scanner;

public class InputHelper {

    private Scanner scanner;

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Will build up a menu, accept input and return the number of the option chosen
     * Returns the first option automatically if there is only one option provided
     * @param title prompt that gets shown to the user before they choose an option
     * @param options the list of available options
     * @return the option chosen
     */
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

    /**
     * @see #getString(String, boolean)
     * @param promptToUser prompt to show to user (usually an instruction)
     * @return string that user entered
     */
    public String getString(String promptToUser) {
        return getString(promptToUser, false);
    }

    /**
     * Gets text from the console that is entered. If required, blank input won't be accepted
     * If not required, there will be a prompt to ensure that an empty string is desired
     * @param promptToUser prompt to show to user (usually an instruction)
     * @param required flag to determine if the string returned should be non-empty
     * @return string that the user entered
     */
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

    /**
     * Parse a UK date string to a LocalDate object
     * It can split dates on / or -
     * EGs of valid dates "21/02/2018", "21-02-2018", "21-2-2018"
     * @param dateEntered date to parse
     * @return object matching the date entered or null if the input was invalid
     */
    public static LocalDate inputDate(String dateEntered) {
        if (dateEntered == null)
            return null;

        String[] splitDate = dateEntered.split("[/\\-]");
        try {
            int day = Integer.parseInt(splitDate[0]);
            int month = Integer.parseInt(splitDate[1]);
            int year = Integer.parseInt(splitDate[2]);

            return LocalDate.of(year, month, day);
        } catch (Exception e) { // Will be caught if the date is invalid in any way
            return null;
        }
    }
}
