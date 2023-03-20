package persons;

import enums.EnrollmentType;
import enums.StudentType;

import java.util.ArrayList;
import java.util.List;

public class Student {
    public String name;
    public StudentType studentType;
    public EnrollmentType enrollmentType;
    public List<Double> grades;
    public double averageGrade;
    public char letterGrade;
    public double gpa;

    public Student(String name, StudentType studentType, EnrollmentType enrollmentType) {
        this.name = name;
        this.studentType = studentType;
        this.enrollmentType = enrollmentType;
        grades = new ArrayList<Double>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StudentType getStudentType() {
        return studentType;
    }

    public void setStudentType(StudentType studentType) {
        this.studentType = studentType;
    }

    public EnrollmentType getEnrollmentType() {
        return enrollmentType;
    }

    public void setEnrollmentType(EnrollmentType enrollmentType) {
        this.enrollmentType = enrollmentType;
    }

    public List<Double> getGrades() {
        return grades;
    }

    public void setGrades(List<Double> grades) {
        this.grades = grades;
    }

    public char getLetterGrade(double averageGrade) {
        return letterGrade;
    }

    public void setLetterGrade(char letterGrade) {
        this.letterGrade = letterGrade;
    }

    public double getGpa(char letterGrade, StudentType studentType) {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public double getAverageGrade() {
        int numberOfGrades = grades.size();

        double sum = 0;
        for (double grade : grades)
            sum += grade;

        return sum / numberOfGrades;
    }

    public void addGrade(double grade) {
        if (grade < 0 || grade > 100)
            throw new ArithmeticException("Grades must be between 0 and 100.");

        grades.add(grade);
    }

    public void removeGrade(double grade) {
        grades.remove(grade);
    }
}
