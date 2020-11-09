package seedu.taskmaster.model.record;

import java.util.function.Predicate;

public class StudentRecordEqualsPredicate implements Predicate<StudentRecord> {

    private final StudentRecord desiredStudentRecord;

    public StudentRecordEqualsPredicate(StudentRecord desiredStudentRecord) {
        this.desiredStudentRecord = desiredStudentRecord;
    }

    @Override
    public boolean test(StudentRecord studentRecord) {
        return this.desiredStudentRecord.isSameStudentAs(studentRecord);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StudentRecordEqualsPredicate) // instanceof handles nulls
                && desiredStudentRecord.equals(((StudentRecordEqualsPredicate) other).desiredStudentRecord);
    }
}
