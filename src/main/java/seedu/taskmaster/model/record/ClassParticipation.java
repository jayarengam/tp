package seedu.taskmaster.model.record;


public class ClassParticipation {
    public static final String MESSAGE_CONSTRAINTS = "The score of a student needs to be a positive integer.";

    private double score = 0;

    /**
     * Constructor method for Class Participation
     * Uses the default maximum score, which is 10
     * The initial score is set to 0
     */
    public ClassParticipation() {

    }

    /**
     * Constructor method for Class Participation using score
     * Uses the default maximum score, which is 10
     * The score is set to the wanted score
     */
    public ClassParticipation(double score) {
        assert score >= 0;
        this.score = score;
    }

    /**
     * Returns the class participation score as an double.
     */
    public double getRawScore() {
        return this.score;
    }

    @Override
    public String toString() {
        String formattedScore = String.format("%.2f", score);
        return "Class Participation Score: " + formattedScore;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClassParticipation)) {
            return false;
        }

        // state check
        ClassParticipation classPart = (ClassParticipation) other;
        return (this.score == classPart.score);
    }
}
