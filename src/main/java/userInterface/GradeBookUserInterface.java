package userInterface;

import enums.EnrollmentType;
import enums.StudentType;
import gradeBooks.BaseGradeBook;
import persons.Student;

import java.util.Scanner;

public class GradeBookUserInterface {
    public static final String EMPTY_STRING = "";
    public static BaseGradeBook baseGradeBook;
    public static boolean quit = false;

    public static void commandLoop(BaseGradeBook gradeBook) {
        baseGradeBook = gradeBook;
        quit = false;

        System.out.println("#=======================#");
        System.out.println(baseGradeBook.getName() + " : " + baseGradeBook.getType().name());
        System.out.println("#=======================#");

        while (!quit) {
            System.out.println(EMPTY_STRING);
            System.out.println(">> What would you like to do?");
            String command = new Scanner(System.in).nextLine().toLowerCase();
            commandRoute(command);
        }

        System.out.println(baseGradeBook.getName() + " has been closed.");
    }

    public static void commandRoute(String command) {
        if (command.equals("save"))
            saveCommand();
        else if (command.startsWith("addgrade"))
            addGradeCommand(command);
        else if (command.startsWith("removegrade"))
            removeGradeCommand(command);
        else if (command.startsWith("add"))
            addStudentCommand(command);
        else if (command.startsWith("remove"))
            removeStudentCommand(command);
        else if (command.equals("list"))
            listCommand();
        else if (command.equals("statistic all"))
            statisticCommand();
        else if (command.startsWith("statistic"))
            studentStatisticCommand(command);
        else if (command.equals("help"))
            helpCommand();
        else if (command.equals("exit"))
            quit = true;
        else
            System.out.printf("%s was not recognized, please try again.%n", command);
    }

    public static void saveCommand() {
        baseGradeBook.save();
        System.out.printf("%s has been saved.%n", baseGradeBook.getName());
    }

    public static void addGradeCommand(String command) {
        String[] parts = command.split(" ");
        if (parts.length != 3) {
            System.out.println("Command not valid, AddGrade requires a name and score.");
            return;
        }

        String name = parts[1];
        double score = Double.parseDouble(parts[2]);
        baseGradeBook.addGrade(name, score);
        System.out.printf("Added a score of %f to %s's grades%n", score, name);
    }

    public static void removeGradeCommand(String command) {
        String[] parts = command.split(" ");
        if (parts.length != 3) {
            System.out.println("Command not valid, RemoveGrade requires a name and score.");
            return;
        }

        String name = parts[1];
        double score = Double.parseDouble(parts[2]);
        baseGradeBook.removeGrade(name, score);
        System.out.printf("Removed a score of %f from %s's grades%n", score, name);
    }

    public static void addStudentCommand(String command) {
        String[] parts = command.split(" ");
        if (parts.length != 4) {
            System.out.println("Command not valid, Add requires a name, student type, and enrollment type.");
            return;
        }

        String name = parts[1];

        StudentType studentType;
        try {
            studentType = StudentType.valueOf(parts[2].toUpperCase());
        }
        catch (IllegalArgumentException exception) {
            System.out.printf("%s is not a valid student type, try again.%n", parts[2]);
            return;
        }

        EnrollmentType enrollmentType;
        try {
            enrollmentType = EnrollmentType.valueOf(parts[2].toUpperCase());
        }
        catch (IllegalArgumentException exception) {
            System.out.printf("%s is not a valid enrollment type, try again.%n", parts[3]);
            return;
        }

        Student student = new Student(name, studentType, enrollmentType);
        baseGradeBook.addStudent(student);
        System.out.printf("Added %s to the grade book.%n", name);
    }

    public static void removeStudentCommand(String command) {
        String[] parts = command.split(" ");
        if (parts.length != 2) {
            System.out.println("Command not valid, Remove requires a name.");
            return;
        }

        String name = parts[1];
        baseGradeBook.removeStudent(name);
        System.out.printf("Removed %s from the grade book.%n", name);
    }

    public static void listCommand() {
        baseGradeBook.listStudents();
    }

    public static void statisticCommand() {
        baseGradeBook.calculateStatistics();
    }

    public static void studentStatisticCommand(String command) {
        String[] parts = command.split(" ");
        if (parts.length != 2) {
            System.out.println("Command not valid, Requires Name or All.");
            return;
        }

        String name = parts[1];
        baseGradeBook.calculateStudentStatistics(name);
    }

    public static void helpCommand() {
        System.out.println();
        System.out.println("While a grade book is open you can use the following commands:");
        System.out.println();
        System.out.println("Add 'Name' 'Student Type' 'Enrollment Type' - Adds a new student to the grade book with the" +
                " provided name, type of student, and type of enrollment.");
        System.out.println();
        System.out.println("Accepted Student Types:");
        System.out.println("Standard - Student not enrolled in Honors classes or Dual Enrolled.");
        System.out.println("Honors - Students enrolled in Honors classes and not Dual Enrolled.");
        System.out.println("DualEnrolled - Students who are Dual Enrolled.");
        System.out.println();
        System.out.println("Accepted Enrollment Types:");
        System.out.println("Campus - Students who are in the same district as the school.");
        System.out.println("State - Students whose legal residence is outside the school's district, but is in the same" +
                " state as the school.");
        System.out.println("National - Students whose legal residence is not in the same state as the school," +
                " but is in the same country as the school.");
        System.out.println("International - Students whose legal residence is not in the same country as the school.");
        System.out.println();
        System.out.println("List - Lists all students.");
        System.out.println();
        System.out.println("AddGrade 'Name' 'Score' - Adds a new grade to a student with the matching name of the provided score.");
        System.out.println();
        System.out.println("RemoveGrade 'Name' 'Score' - Removes a grade from a student with the matching name and score.");
        System.out.println();
        System.out.println("Remove 'Name' - Removes the student with the provided name.");
        System.out.println();
        System.out.println("Statistics 'Name' - Gets statistics for the specified student.");
        System.out.println();
        System.out.println("Statistics All - Gets general statistics for the entire grade book.");
        System.out.println();
        System.out.println("Close - Closes the grade book and takes you back to the starting command options.");
        System.out.println();
        System.out.println("Save - Saves the grade book to the hard drive for later use.");
    }
}
