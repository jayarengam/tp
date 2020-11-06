package seedu.taskmaster.testutil;

import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_NUSNETID_AMY;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_NUSNETID_BOB;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_TELEGRAM_AMY;
import static seedu.taskmaster.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.taskmaster.model.Taskmaster;
import seedu.taskmaster.model.record.StudentRecord;
import seedu.taskmaster.model.session.Session;
import seedu.taskmaster.model.session.SessionDateTime;
import seedu.taskmaster.model.session.SessionList;
import seedu.taskmaster.model.session.SessionListManager;
import seedu.taskmaster.model.session.SessionName;
import seedu.taskmaster.model.student.Student;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Student ALICE =
            new StudentBuilder()
            .withName("Alice Pauline")
            .withNusnetId("e0123456")
            .withEmail("alice@example.com")
            .withTelegram("alicepauline")
            .withTags("friends").build();

    public static final Student BENSON =
            new StudentBuilder()
            .withName("Benson Meier")
            .withNusnetId("e0456789")
            .withEmail("johnd@example.com")
            .withTelegram("benson_m")
            .withTags("owesMoney", "friends").build();

    public static final Student CARL =
            new StudentBuilder()
                    .withName("Carl Kurz")
                    .withTelegram("kurzzz")
                    .withEmail("heinz@example.com")
                    .withNusnetId("e0312654").build();

    public static final Student DANIEL =
            new StudentBuilder()
                    .withName("Daniel Meier")
                    .withTelegram("dms_only")
                    .withEmail("cornelia@example.com")
                    .withNusnetId("e0987465")
                    .withTags("friends").build();

    public static final Student ELLE = new StudentBuilder()
            .withName("Elle Meyer")
            .withTelegram("mellie")
            .withEmail("werner@example.com")
            .withNusnetId("e0946875").build();

    public static final Student FIONA = new StudentBuilder()
            .withName("Fiona Kunz")
            .withTelegram("fionakunz")
            .withEmail("lydia@example.com")
            .withNusnetId("e0319843").build();

    public static final Student GEORGE = new StudentBuilder()
            .withName("George Best")
            .withTelegram("george_worst")
            .withEmail("anna@example.com")
            .withNusnetId("e0731894").build();

    // Manually added
    public static final Student HOON = new StudentBuilder()
            .withName("Hoon Meier")
            .withTelegram("8482424")
            .withEmail("stefan@example.com")
            .withNusnetId("e0134679").build();

    public static final Student IDA = new StudentBuilder()
            .withName("Ida Mueller")
            .withTelegram("8482131")
            .withEmail("hans@example.com")
            .withNusnetId("e0235689").build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder()
            .withName(VALID_NAME_AMY)
            .withTelegram(VALID_TELEGRAM_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withNusnetId(VALID_NUSNETID_AMY)
            .withTags(VALID_TAG_FRIEND).build();

    public static final Student BOB = new StudentBuilder()
            .withName(VALID_NAME_BOB)
            .withTelegram(VALID_TELEGRAM_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withNusnetId(VALID_NUSNETID_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalStudents() {} // prevents instantiation

    /**
     * Returns a {@code Session} with all the typical students.
     */
    public static Session getTypicalSession() {
        return new Session(
                new SessionName("Typical session"),
                new SessionDateTime(LocalDateTime.of(2020, 1, 1, 12, 0)),
                getTypicalStudents());
    }

    /**
     * Returns a {@code Session} with all the typical students and modified {@code ClassParticipation} scores.
     */
    public static Session getTypicalScoredSession() {
        Session modifiedSession = new Session(
                new SessionName("Typical session 2"),
                new SessionDateTime(LocalDateTime.of(2020, 1, 1, 12, 0)),
                getTypicalStudents());
        modifiedSession.scoreAllParticipation(
                getTypicalStudents().stream().map(Student::getNusnetId).collect(Collectors.toList()), 5);
        modifiedSession.scoreStudentParticipation(ALICE.getNusnetId(), 4);
        modifiedSession.scoreStudentParticipation(CARL.getNusnetId(), 4);

        return modifiedSession;
    }

    /**
     * Returns a {@code Taskmaster} with all the typical students and 1 session.
     */
    public static Taskmaster getTypicalTaskmaster() {
        Taskmaster typicalTaskmaster = new Taskmaster();
        for (Student student : getTypicalStudents()) {
            typicalTaskmaster.addStudent(student);
        }
        typicalTaskmaster.addSession(getTypicalSession());
        return typicalTaskmaster;
    }

    /**
     * Returns a {@code Taskmaster} with all the typical students and 2 sessions, one of which is scored.
     */
    public static Taskmaster getScoredTaskmaster() {
        Taskmaster taskmaster = getTypicalTaskmaster();
        taskmaster.setSessions(getTypicalScoredSessionList().asUnmodifiableObservableList());
        return taskmaster;
    }


    /**
     * Returns an {@code SessionList} with all the typical students and two unmodified {@code Sessions}.
     */
    public static SessionList getTypicalSessionList() {
        SessionList sessionList = new SessionListManager();
        Session typicalSession = getTypicalSession();
        sessionList.add(typicalSession);
        Session typicalSession2 = new Session(
                new SessionName("Typical session 2"),
                new SessionDateTime(LocalDateTime.of(2020, 1, 1, 13, 0)),
                getTypicalStudents());
        sessionList.add(typicalSession2);
        return sessionList;
    }

    /**
     * Returns an {@code SessionList} with all the typical students and two unmodified {@code Sessions}.
     */
    public static SessionList getTypicalScoredSessionList() {
        SessionList sessionList = new SessionListManager();
        sessionList.add(getTypicalSession());
        sessionList.add(getTypicalScoredSession());
        return sessionList;
    }

    /**
     * Returns a List containing all the typical students .
     */
    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    /**
     * Returns a list containing the StudentRecords of all the typical students,
     * without modified ClassParticipation and Attendance fields.
     */
    public static List<StudentRecord> getTypicalStudentRecords() {
        return getTypicalSession().getStudentRecords();
    }
}
