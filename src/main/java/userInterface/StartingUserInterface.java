package userInterface;

import gradeBooks.BaseGradeBook;

import java.util.Scanner;

public class StartingUserInterface {
    public static final String EMPTY_STRING = "";
    public static boolean quit = false;

    public static void commandLoop() {
        while (!quit) {
            System.out.println(EMPTY_STRING);
            System.out.println(">> What would you like to do?");
            String command = new Scanner(System.in).nextLine().toLowerCase();
            CommandRoute(command);
        }
    }

    public static void CommandRoute(String command) {
        if (command.startsWith("create"))
            System.out.println("create");
        else if (command.startsWith("load"))
            System.out.println("load");
        else if (command.equals("help"))
            helpCommand();
        else if (command.equals("quit"))
            quit = true;
        else
            System.out.printf("%s was not recognized, please try again.%n", command);
    }

    public static void createCommand(String command) {
        String[] parts = command.split(" ");
        if (parts.length != 2) {
            System.out.println("Command not valid, Create requires a name.");
            return;
        }
        String name = parts[1];
        BaseGradeBook baseGradeBook = new BaseGradeBook(name);
        System.out.printf("Created grade book %s.%n", name);

    }

    public static void loadCommand(String command) {
        String[] parts = command.split(" ");
        if (parts.length != 2) {
            System.out.println("Command not valid, Load requires a name.");
            return;
        }
        String name = parts[1];

    }

    public static void helpCommand() {
        System.out.println();
        System.out.println("GradeBook accepts the following commands:");
        System.out.println();
        System.out.println("Create 'Name'");
        System.out.println();
        System.out.println("Load 'Name' - Loads the grade book with the provided 'Name'.");
        System.out.println();
        System.out.println("Help - Displays all accepted commands.");
        System.out.println();
        System.out.println("Quit - Exits the application");
    }
}
