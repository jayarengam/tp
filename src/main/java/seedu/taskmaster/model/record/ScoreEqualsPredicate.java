package seedu.taskmaster.model.record;

import java.util.function.Predicate;

import seedu.taskmaster.model.student.NameContainsKeywordsPredicate;


public class ScoreEqualsPredicate implements Predicate<StudentRecord> {

    private final double desiredScore;

    public ScoreEqualsPredicate(double desiredScore) {
        this.desiredScore = desiredScore;
    }

    @Override
    public boolean test(StudentRecord studentRecord) {
        return studentRecord.getAttendanceType().equals(AttendanceType.PRESENT)
                && studentRecord.getClassParticipation().getRawScore() == desiredScore;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && desiredScore == ((ScoreEqualsPredicate) other).desiredScore); // state check
    }
}
