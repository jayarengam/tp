package seedu.taskmaster.model.session;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class SessionDateTimeTest {

    private final LocalDateTime testLocalDateTime =
            LocalDateTime.of(2020, 11, 1, 10, 30);

    private final SessionDateTime testSessionDateTime =
            new SessionDateTime(testLocalDateTime);

    @Test
    public void getDateTimeString() {
        String expected = "01-11-2020 10:30";
        assertEquals(expected, testSessionDateTime.getDateTimeString());
    }

    @Test
    public void getDisplayDateTimeString() {
        String expected = "10:30 (Sun) \n1 Nov 2020";
        assertEquals(expected, testSessionDateTime.getDisplayDateTimeString());
    }
}
