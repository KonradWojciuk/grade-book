package gradeBooks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import enums.EnrollmentType;
import enums.GradeBookType;
import enums.StudentType;
import persons.Student;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseGradeBook {
    public String name;
    public GradeBookType gradeBookType;
    public List<Student> students;
    public boolean isWeighted;

    public GradeBookType type;

    public BaseGradeBook(String name, boolean isWeighted) {
        this.name = name;
        students = new ArrayList<Student>();
        this.isWeighted = isWeighted;
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

    public GradeBookType getType() {
        return type;
    }

    public void setType(GradeBookType type) {
        this.type = type;
    }

    public void addStudent(Student student) {
        if (student.name.isEmpty())
            throw new IllegalArgumentException("A Name is required to add a student to a grade book.");

        students.add(student);
    }

    public void removeStudent(String name) {
        if (name.isEmpty())
            throw new IllegalArgumentException("A Name is required to remove a student from a grade book.");

        var student = students.stream().filter(s -> s.getName().equals(name)).findFirst();

        if (student.isEmpty()) {
            System.out.printf("persons.Student %s was not found, try again.%n", name);
            return;
        }

        student.ifPresent(s -> students.remove(s));
    }

    public void addGrade(String name, double score) {
        if (name.isEmpty())
            throw new IllegalArgumentException("A Name is required to add a grade to a student.");

        var student = students.stream().filter(s -> s.getName().equals(name)).findFirst();
        if (student.isEmpty()) {
            System.out.printf("persons.Student %s was not found, try again.%n", name);
            return;
        }

        student.ifPresent(s -> s.addGrade(score));
    }

    public void removeGrade(String name, double score) {
        if (name.isEmpty())
            throw new IllegalArgumentException("A Name is required to add a grade to a student.");

        var student = students.stream().filter(s -> s.getName().equals(name)).findFirst();
        if (student.isEmpty()) {
            System.out.printf("persons.Student %s was not found, try again.%n", name);
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

            return convertToGradeBook(String.valueOf(stringBuilder));
        }
    }

    public void save() {
        try (FileWriter writer = new FileWriter(this.name + ".gdbk")) {
            Gson gson = new Gson();
            String json = gson.toJson(this);
            writer.write(json);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public double getGpa(char letterGrade, StudentType studentType) {
        double grade = 0;

        if ((studentType == StudentType.HONORS) || (studentType == StudentType.DUAL_ENROLLED) && isWeighted == true)
            grade += 1;

        switch (letterGrade) {
            case 'A' -> { return grade + 4; }
            case 'B' -> { return grade + 3; }
            case 'C' -> { return grade + 2; }
            case 'D' -> { return grade + 1; }
            case 'F' -> { return grade + 0; }
        }

        return 0;
    }

    public void calculateStatistics() {
        double allStudentsPoints = 0d;
        double campusPoints = 0d;
        double nationalPoints = 0d;
        double statePoints = 0d;
        double internationalPoints = 0d;
        double standardPoints = 0d;
        double honorPoints = 0d;
        double dualEnrolledPoints = 0d;

        for (var student : students) {
            student.letterGrade = student.getLetterGrade(student.averageGrade);
            student.gpa = student.getGpa(student.letterGrade, student.studentType);

            System.out.printf("%s (%c:%f) GPA: %f%n", student.name, student.letterGrade, student.averageGrade, student.gpa);
            allStudentsPoints += student.averageGrade;

            switch (student.enrollmentType) {
                case CAMPUS -> campusPoints += student.averageGrade;
                case STATE -> statePoints += student.averageGrade;
                case NATIONAL -> nationalPoints += student.averageGrade;
                case INTERNATIONAL -> internationalPoints += student.averageGrade;
            }

            switch (student.studentType) {
                case STANDARD -> standardPoints += student.averageGrade;
                case HONORS -> honorPoints += student.averageGrade;
                case DUAL_ENROLLED -> dualEnrolledPoints += student.averageGrade;
            }
        }

        System.out.println("Average Grade of all students is " + (allStudentsPoints / students.size()));
        if (campusPoints != 0)
            System.out.println("Average for only local students is " +
                    campusPoints / students.stream().filter(s -> s.getEnrollmentType() == EnrollmentType.CAMPUS).count());
        if (statePoints != 0)
            System.out.println("Average for only state students (excluding local) is " +
                    statePoints / students.stream().filter(s -> s.getEnrollmentType() == EnrollmentType.STATE).count());
        if (nationalPoints != 0)
            System.out.println("Average for only national students (excluding state and local) is " +
                    nationalPoints / students.stream().filter(s -> s.getEnrollmentType() == EnrollmentType.NATIONAL).count());
        if (internationalPoints != 0)
            System.out.println("Average for only international students is " +
                    internationalPoints / students.stream().filter(s -> s.getEnrollmentType() == EnrollmentType.INTERNATIONAL).count());
        if (standardPoints != 0)
            System.out.println("Average for students excluding honors and dual enrollment is " +
                    standardPoints / students.stream().filter(s -> s.getStudentType() == StudentType.STANDARD).count());
        if (honorPoints != 0)
            System.out.println("Average for only honors students is " +
                    standardPoints / students.stream().filter(s -> s.getStudentType() == StudentType.HONORS).count());
        if (dualEnrolledPoints != 0)
            System.out.println("Average for only dual enrolled students is " +
                    standardPoints / students.stream().filter(s -> s.getStudentType() == StudentType.DUAL_ENROLLED).count());
    }

    public void calculateStudentStatistics(String name) {
        Student student = students.stream().filter(s -> s.getName().equals(name)).findFirst().orElse(null);
        if (student != null) {
            student.setLetterGrade(student.getLetterGrade(student.getAverageGrade()));
            student.setGpa(student.getGpa(student.letterGrade, student.studentType));
            System.out.printf("%s (%s:%s) GPA: %s.%n", student.name, student.letterGrade, student.averageGrade, student.gpa);
            System.out.println("Grades: ");
            for (double grade : student.getGrades())
                System.out.println(grade);
        }

    }

    public char getLetterGrade(double averageGrade) {
        if (averageGrade >= 90)
            return 'A';
        else if (averageGrade >= 80)
            return 'B';
        else if (averageGrade >= 70)
            return 'C';
        else if (averageGrade >= 60)
            return 'D';
        else
            return 'F';
    }

    public static BaseGradeBook convertToGradeBook(String json) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String gradeBookType = jsonObject.get("Type").getAsString();

        BaseGradeBook gradeBook;

        switch (gradeBookType) {
            default -> {
                gradeBook = gson.fromJson(json, BaseGradeBook.class);
                break;
            }
        }

        return gradeBook;
    }
}
