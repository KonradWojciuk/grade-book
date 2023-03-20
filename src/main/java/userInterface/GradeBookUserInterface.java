package userInterface;

import gradeBooks.BaseGradeBook;
import userInterface.StartingUserInterface;

import java.util.Scanner;

public class GradeBookUserInterface {
    public static final String EMPTY_STRING = "";
    public static BaseGradeBook baseGradeBook;
    public static boolean quit = false;

    public static void commandLoop(BaseGradeBook gradeBook) {
        baseGradeBook = gradeBook;
        quit = false;

        System.out.println("#=======================#");
        System.out.println(baseGradeBook.getName() + " : " + baseGradeBook.getGradeBookType().name());
        System.out.println("#=======================#");

        while (!quit) {
            System.out.println(EMPTY_STRING);
            System.out.println(">> What would you like to do?");
            String command = new Scanner(System.in).nextLine().toLowerCase();

        }

        System.out.println(baseGradeBook.getName() + " has been closed.");
    }

    public static void commandRoute(String command) {
        if (command.equals("save"))

    }

    public static void saveCommand() {

    }
}
