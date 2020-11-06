package seedu.taskmaster.model.record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class ClassParticipationTest {
    static final double CLASS_PART_SCORE = 5.32;
    static final double CLASS_PART_SCORE_OVERFLOW = 5.321;
    private ClassParticipation emptyClassPart = new ClassParticipation();
    private ClassParticipation dupeClassPart = new ClassParticipation(0);
    private ClassParticipation filledClassPart = new ClassParticipation(CLASS_PART_SCORE);
    private ClassParticipation overflowClassPart = new ClassParticipation(CLASS_PART_SCORE_OVERFLOW);


    @Test
    void getRawScore() {
        assertEquals(emptyClassPart.getRawScore(), 0);
        assertEquals(filledClassPart.getRawScore(), CLASS_PART_SCORE);
    }

    @Test
    void testToString() {
        assertEquals(emptyClassPart.toString(), "Class Participation Score: 0.00");
        assertEquals(filledClassPart.toString(), "Class Participation Score: " + CLASS_PART_SCORE);
        assertEquals(overflowClassPart.toString(), "Class Participation Score: " + CLASS_PART_SCORE);
    }

    @Test
    void testEquals() {
        assertEquals(emptyClassPart, dupeClassPart);
        assertNotEquals(filledClassPart, emptyClassPart);
        assertEquals(emptyClassPart, emptyClassPart);
        assertEquals(overflowClassPart, overflowClassPart);
    }
}
