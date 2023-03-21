package gradeBooks;

import enums.GradeBookType;
import persons.Student;

import javax.management.OperationsException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RankedGradeBook extends BaseGradeBook {

    public RankedGradeBook(String name, boolean isWeighted) {
        super(name, isWeighted);
        type = GradeBookType.RANKED;
    }

    @Override
    public char getLetterGrade(double averageGrade) {
        if (students.size() < 5)
            throw new IllegalArgumentException();

        int top20Students = (int)Math.ceil(students.size() * 0.2);
        List<Double> grades = students.stream()
                .mapToDouble(Student::getAverageGrade)
                .boxed()
                .sorted(Collections.reverseOrder())
                .toList();

        if (grades.get(top20Students - 1) <= averageGrade)
            return 'A';
        else if (grades.get(top20Students * 2 - 1) <= averageGrade)
            return 'B';
        else if (grades.get(top20Students * 3 - 1) <= averageGrade)
            return 'C';
        else if (grades.get(top20Students * 4 - 1) <= averageGrade)
            return 'D';
        else
            return 'F';
    }

    @Override
    public void calculateStatistics() {
        if (students.size() < 5) {
            System.out.println("Ranked grading requires at least 5 students.");
            return;
        }

        super.calculateStatistics();
    }

    @Override
    public void calculateStudentStatistics(String name) {
        if (students.size() < 5) {
            System.out.println("Ranked grading requires at least 5 students.");
            return;
        }

        super.calculateStudentStatistics(name);
    }
}
