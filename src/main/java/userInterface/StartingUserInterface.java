package userInterface;

import gradeBooks.BaseGradeBook;
import gradeBooks.StandardGradeBook;

import java.io.IOException;
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
            createCommand(command);
        else if (command.startsWith("load"))
            loadCommand(command);
        else if (command.equals("help"))
            helpCommand();
        else if (command.equals("quit"))
            quit = true;
        else
            System.out.printf("%s was not recognized, please try again.%n", command);
    }

    public static void createCommand(String command) {
        String[] parts = command.split(" ");
        if (parts.length != 4) {
            System.out.println("Command not valid, Create requires a name, type of grade book, " +
                    "if it's weighted (true / false).");
            return;
        }
        String name = parts[1];
        if (parts[2].equals("standard") && parts[3].equals("true")) {
            StandardGradeBook standardGradeBook = new StandardGradeBook(name, true);
            GradeBookUserInterface.commandLoop(standardGradeBook);
        }
        else if (parts[2].equals("standard") && parts[3].equals("false")) {
            StandardGradeBook standardGradeBook = new StandardGradeBook(name, false);
            GradeBookUserInterface.commandLoop(standardGradeBook);
        }
        else {
            System.out.printf("%s is not a supported type of grade book, please try again", parts[2]);
            return;
        }

        System.out.printf("Created grade book %s", name);
    }

    public static void loadCommand(String command) {
        String[] parts = command.split(" ");
        if (parts.length != 2) {
            System.out.println("Command not valid, Load requires a name.");
            return;
        }
        String name = parts[1];

        try {
            var gradeBook = BaseGradeBook.load(name);
            GradeBookUserInterface.commandLoop(gradeBook);
        }
        catch (IOException exception) {
            return;
        }
    }

    public static void helpCommand() {
        System.out.println();
        System.out.println("GradeBook accepts the following commands:");
        System.out.println();
        System.out.println("Create 'Name' 'Type' 'Weighted' - Creates a new   where 'Name' is the name of the grade book," +
                " 'Type' is what type of grading it should use," +
                " and 'Weighted' is whether or not grades should be weighted (true or false).");
        System.out.println();
        System.out.println("Load 'Name' - Loads the grade book with the provided 'Name'.");
        System.out.println();
        System.out.println("Help - Displays all accepted commands.");
        System.out.println();
        System.out.println("Quit - Exits the application");
    }
}
