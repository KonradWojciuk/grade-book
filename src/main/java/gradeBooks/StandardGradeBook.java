package gradeBooks;

import enums.GradeBookType;

public class StandardGradeBook extends BaseGradeBook{
    public StandardGradeBook(String name, boolean isWeighted) {
        super(name, isWeighted);
        type = GradeBookType.STANDARD;
    }
}
