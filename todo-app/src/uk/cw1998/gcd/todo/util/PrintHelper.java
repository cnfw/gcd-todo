package uk.cw1998.gcd.todo.util;

import uk.cw1998.gcd.todo.items.BaseTodo;
import uk.cw1998.gcd.todo.items.ListTodo;

public class PrintHelper {
    public static void printStartup() {
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

    public static void printTodoInformation(BaseTodo todo) {
        System.out.println("Selected Todo: " + todo.getTitle());
        System.out.println("Description: " + todo.getDescription());
        System.out.println("Completed: " + ((todo.isCompleted()) ? "Yes" : "No"));
        System.out.println("Priority: " + todo.getPriority().getName());
        System.out.println("Due date: " + todo.getUKDate());

        if (todo instanceof ListTodo)
            System.out.println("Number of checklist items: " + ((ListTodo) todo).getChecklist().size());
    }
}
