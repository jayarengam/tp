package seedu.taskmaster.model.session;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SessionDateTime {

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
    public static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm (EEE) \nd LLL yyyy");
    public static final String MESSAGE_CONSTRAINTS =
            "Date and time should be in the format 'dd-MM-yyyy HHmm'.";

    private final LocalDateTime localDateTime;

    /**
     * Constructs a {@code SessionDateTime}.
     *
     * @param localDateTime The date and time of a session.
     */
    public SessionDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    private LocalDateTime getLocalDateTime() {
        return this.localDateTime;
    }

    public String getDateTimeString() {
        return localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    public String getDisplayDateTimeString() {
        return localDateTime.format(DISPLAY_FORMATTER);
    }

    @Override
    public String toString() {
        return localDateTime.format(DATE_TIME_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SessionDateTime // instanceof handles nulls
                && localDateTime.equals(((SessionDateTime) other).getLocalDateTime())); // state check
    }

}
