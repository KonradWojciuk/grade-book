import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.io.output.TeeOutputStream;
import org.junit.Assert;
import org.junit.Test;
import userInterface.StartingUserInterface;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

public class AddMultipleGradeBookTypeSupportToStartingUserInterfaceTests {

    //All Tests related to the "Increase Parts Check To 3" Task.
    @Test
    public void testIncreasePartsCheckToThree() {
        //Bypass Test if Create Command for Weighted GPA has been started
        Class<?> rankedGradeBook = null;
        try {
            rankedGradeBook = Class.forName("gradeBooks.RankedGradeBook");
        }
        catch (ClassNotFoundException exception) {
            Assert.fail(String.valueOf(exception));
        }
        Assert.assertNotNull("`RankedGradeBook` wasn't found in the `GradeBooks.GradeBook` namespace.", rankedGradeBook);

        Constructor<?> constructor = null;
        try {
            constructor = rankedGradeBook.getConstructor();
        }
        catch (NoSuchMethodException exception) {
            Assert.fail(String.valueOf(exception));
        }

        Parameter[] parameters = constructor.getParameters();
        if (parameters.length == 2 && parameters[0].getType() == String.class && parameters[1].getType() == Boolean.class)
            return;

        // Setup Test
        String output;
        try {
            System.setIn(new ByteArrayInputStream("close".getBytes()));
            ByteArrayOutputStream consoleStream = new ByteArrayOutputStream();
            TeeOutputStream teeOutputStream = new TeeOutputStream(System.out, consoleStream);
            System.setOut(new PrintStream(teeOutputStream));
            StartingUserInterface.createCommand("create test");
            output = teeOutputStream.toString().toLowerCase();
            Assert.assertTrue("`GradeBook.UserInterfaces.StartingUserInterface` didn't write a message to the" +
                    " console when the create command didn't contain both a name and type.", output.contains("command not valid"));
        }
        finally {
            System.setIn(System.in);
            System.setOut(System.out);
        }
    }
}
