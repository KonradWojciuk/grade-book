package gradeBooks;

import enums.GradeBookType;
import org.junit.jupiter.params.aggregator.ArgumentAccessException;
import persons.Student;
import persons.Student.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BaseGradeBook {
    public String name;
    public GradeBookType gradeBookType;
    public List<Student> students;
    public boolean isWeighted;

    public BaseGradeBook(String name) {
        this.name = name;
        students = new ArrayList<Student>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GradeBookType getGradeBookType() {
        return gradeBookType;
    }

    public void setGradeBookType(GradeBookType gradeBookType) {
        this.gradeBookType = gradeBookType;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public boolean isWeighted() {
        return isWeighted;
    }

    public void setWeighted(boolean weighted) {
        isWeighted = weighted;
    }


    public void addStudent(Student student) {
        if (student.name.isEmpty())
            throw new IllegalArgumentException("A Name is required to add a student to a grade book.");

        students.add(student);
    }

    public void removeStudent(String name) {
        if (name.isEmpty())
            throw new ArgumentAccessException("A Name is required to remove a student from a grade book.");

        var student = students.stream().filter(s -> s.getName().equals(name)).findFirst();

        if (student.isEmpty()) {
            System.out.printf("Student %s was not found, try again.%n", name);
            return;
        }

        student.ifPresent(s -> students.remove(s));
    }

    public void addGrade(String name, double score) {
        if (name.isEmpty())
            throw new IllegalArgumentException("A Name is required to add a grade to a student.");

        var student = students.stream().filter(s -> s.getName().equals(name)).findFirst();
        if (student.isEmpty()) {
            System.out.printf("Student %s was not found, try again.%n", name);
            return;
        }

        student.ifPresent(s -> s.addGrade(score));
    }

    public void removeGrade(String name, double score) {
        if (name.isEmpty())
            throw new IllegalArgumentException("A Name is required to add a grade to a student.");

        var student = students.stream().filter(s -> s.getName().equals(name)).findFirst();
        if (student.isEmpty()) {
            System.out.printf("Student %s was not found, try again.%n", name);
            return;
        }

        student.ifPresent(s -> s.removeGrade(score));
    }

    public void listStudents() {
        for (var student : students)
            System.out.printf("%s : %s : %s%n", student.name, student.studentType.toString().toLowerCase(),
                    student.enrollmentType.toString().toLowerCase());
    }

    public static BaseGradeBook load(String name) throws IOException {
        if (!Files.exists(Paths.get(name + ".gdbk"))) {
            System.out.println("Grade book could not be found.");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(name + ".gdbk"))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
                stringBuilder.append(line);

            return
        }
    }

    public static BaseGradeBook convertToGradeBook(String json) {
        Gson gson = Gson
    }
}
