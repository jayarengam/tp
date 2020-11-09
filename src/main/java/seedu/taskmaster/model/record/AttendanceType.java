package seedu.taskmaster.model.record;

/**
 * Represents the possible attendance states that a {@code StudentRecord} can have.
 */
public enum AttendanceType {
    PRESENT("PRESENT", "#228B22"),
    ABSENT("ABSENT", "#B22222"),
    NO_RECORD("NO RECORD", "#696969");

    public static final String MESSAGE_CONSTRAINTS = "Attendance type can only be 'present', 'absent' or 'no_record.";

    /**
     * Each type has a (1) description (2) color in RGB format of the icon displayed on the GUI.
     */
    private final String description;
    private final String color;

    AttendanceType(String description, String color) {
        this.description = description;
        this.color = color;
    }

    /**
     * Returns true if a given string is a valid attendance type.
     */
    public static boolean isValidAttendanceType(String test) {
        try {
            AttendanceType.valueOf(test);
            return true;
        } catch (IllegalArgumentException iae) {
            return false;
        }
    }

    /**
     * Returns the {@code description} of the attendance type for display purposes.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the {@code color} of the attendance type in RGB format for display purposes.
     */
    public String getColor() {
        return color;
    }
}
