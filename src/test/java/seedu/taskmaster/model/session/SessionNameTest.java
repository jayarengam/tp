package seedu.taskmaster.model.session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskmaster.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SessionNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SessionName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new SessionName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> SessionName.isValidName(null));

        // invalid name
        assertFalse(SessionName.isValidName("")); // empty string
        assertFalse(SessionName.isValidName(" ")); // spaces only
        assertFalse(SessionName.isValidName(" session")); // begins with space
        assertFalse(SessionName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(SessionName.isValidName("Session*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(SessionName.isValidName("new session")); // alphabets only
        assertTrue(SessionName.isValidName("12345")); // numbers only
        assertTrue(SessionName.isValidName("new session 1")); // alphanumeric characters
        assertTrue(SessionName.isValidName("New Session")); // with capital letters
        assertTrue(SessionName.isValidName("New Session With A Very Very Very Very Long Name")); // long names
    }
}
