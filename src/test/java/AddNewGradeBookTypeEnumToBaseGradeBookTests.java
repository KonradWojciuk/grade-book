import enums.GradeBookType;
import gradeBooks.BaseGradeBook;
import org.apache.commons.lang3.EnumUtils;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// This class contains all tests related to the "Add New GradeBookType Enum to BaseGradeBook" task group.
public class AddNewGradeBookTypeEnumToBaseGradeBookTests {
    // All Tests related to the "Create a new Enum GradeBookType" Task.
    @Test
    public void testCreateNewEnumGradeBookType() {
        // Get appropriate path to file for the current operating system
        String filePath = "D:\\JetBrains\\IntelliJ IDEA 2022.2.4\\Projekty\\grade-book\\src\\main\\java\\enums\\GradeBookType.java";
        Path path = Paths.get(filePath);
        boolean fileExist = Files.exists(path);

        // Assert GradeBookType is in the Enums folder
        Assert.assertTrue("`GradeBookType.java` was not found in the `Enums` directory.", fileExist);

        // Get GradeBookType from the enums.GradeBookType
        Class<?> gradeBookEnum = null;
        try {
            gradeBookEnum = Class.forName("enums.GradeBookType");
        }
        catch (ClassNotFoundException exception) {
            Assert.fail("GradeBookType enum not found.");
        }

        // Assert GradeBookType was found in the enums.GradeBookType
        Assert.assertNotNull("`GradeBookType` wasn't found in the `GradeBook.Enums` namespace.", gradeBookEnum);

        // Test to make sure the enum `GradeBookType` is public.
        Assert.assertTrue("`GradeBook.Enums.GradeBookType` exists, but isn't `public`.", Modifier.isPublic(gradeBookEnum.getModifiers()));

        // Test to make sure that `GradeBookType` is an enum not a class
        Assert.assertTrue("`GradeBook.Enums.GradeBookType` exists, but isn't an enum.", gradeBookEnum.isEnum());

        // Test that `GradeBookType` contains the value `Standard`
        boolean isValid = EnumUtils.isValidEnum(GradeBookType.class, GradeBookType.STANDARD.name());
        Assert.assertTrue("`GradeBook.Enums.GradeBookType` doesn't contain the value `Standard`.", isValid);

        // Test that `GradeBookType` contains the value `Ranked`
        isValid = EnumUtils.isValidEnum(GradeBookType.class, GradeBookType.RANKED.name());
        Assert.assertTrue("`GradeBook.Enums.GradeBookType` doesn't contain the value `Ranked`.", isValid);

        // Test that `GradeBookType` contains the value `ESNU`
        isValid = EnumUtils.isValidEnum(GradeBookType.class, GradeBookType.ESNU.name());
        Assert.assertTrue("`GradeBook.Enums.GradeBookType` doesn't contain the value `ESNU`.", isValid);

        // Test that `GradeBookType` contains the value `OneToFour`
        isValid = EnumUtils.isValidEnum(GradeBookType.class, GradeBookType.ONE_TO_FOUR.name());
        Assert.assertTrue("`GradeBook.Enums.GradeBookType` doesn't contain the value `OneToFour`.", isValid);

        // Test that `GradeBookType` contains the value `SixPoint`
        isValid = EnumUtils.isValidEnum(GradeBookType.class, GradeBookType.SIX_POINT.name());
        Assert.assertTrue("`GradeBook.Enums.GradeBookType` doesn't contain the value `SixPoint`.", isValid);
    }

    // All tests related to the "Add property Type to BaseGradeBook" task.
    // "Add property Type to BaseGradeBook Tests @add-a-gradebooktype-property-to-basegradebook"
    @Test
    public void testAddPropertyTypeToBaseGradeBook() throws Exception {
        // Get property Type from BaseGradeBook
        Class<BaseGradeBook> baseGradeBookClass = BaseGradeBook.class;
        Field typeField = baseGradeBookClass.getDeclaredField("type");

        // Test that the property Type exists in BaseGradeBook
        Assert.assertNotNull(typeField);

        // Get GradeBookType Enum from enums.GradeBookType
        Class<?> gradeBookType = null;
        try {
            gradeBookType = Class.forName("enums.GradeBookType");
        }
        catch (ClassNotFoundException exception) {
            Assert.fail("GradeBookType enum not found.");
        }
        Assert.assertEquals(typeField.getType(), gradeBookType);

        // Test that the property Type is of type GradeBookType
    }
}
