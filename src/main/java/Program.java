import userInterface.StartingUserInterface;

public class Program {
    public static void main(String[] args) {
        System.out.println("#=======================#");
        System.out.println("# Welcome to GradeBook! #");
        System.out.println("#=======================#");

        StartingUserInterface.commandLoop();

        System.out.println("Thank you for using GradeBook!");
        System.out.println("Have a nice day!");

    }
}
