package seedu.taskmaster.model.record;

public enum AttendanceType {
    PRESENT("PRESENT", "#228B22"),
    ABSENT("ABSENT", "#B22222"),
    NO_RECORD("NO RECORD", "#696969");

    public static final String MESSAGE_CONSTRAINTS = "StudentRecord type can only be 'present', 'absent' or 'empty.";

    private final String description;
    private final String color;

    private AttendanceType(String description, String color) {
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

    public String getDescription() {
        return description;
    }

    public String getColor() {
        return color;
    }
}
