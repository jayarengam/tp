package seedu.taskmaster.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.taskmaster.model.ReadOnlyTaskmaster;
import seedu.taskmaster.model.Taskmaster;
import seedu.taskmaster.model.session.Session;
import seedu.taskmaster.model.session.SessionDateTime;
import seedu.taskmaster.model.session.SessionName;
import seedu.taskmaster.model.student.Email;
import seedu.taskmaster.model.student.Name;
import seedu.taskmaster.model.student.NusnetId;
import seedu.taskmaster.model.student.Student;
import seedu.taskmaster.model.student.Telegram;
import seedu.taskmaster.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Taskmaster} with sample data.
 */
public class SampleDataUtil {

    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(
                    new Name("Alex Yeoh"),
                    new Telegram("alexyeoh"),
                    new Email("alexyeoh@example.com"),
                    new NusnetId("e0456456"),
                    getTagSet("smartalec")),
            new Student(
                    new Name("Bernice Yu"),
                    new Telegram("notverynice"),
                    new Email("berniceyu@example.com"),
                    new NusnetId("e0789789"),
                    getTagSet("alwayslate", "weak")),
            new Student(
                    new Name("Charlotte Oliveiro"),
                    new Telegram("charlotte_o"),
                    new Email("charlotte@example.com"),
                    new NusnetId("e0987897"),
                    getTagSet("good")),
            new Student(
                    new Name("David Li"),
                    new Telegram("david_light"),
                    new Email("lidavid@example.com"),
                    new NusnetId("e0321321"),
                    getTagSet("slacker")),
            new Student(
                    new Name("Irfan Ibrahim"),
                    new Telegram("irfan"),
                    new Email("irfan@example.com"),
                    new NusnetId("e0984984"),
                    getTagSet("smart")),
            new Student(
                    new Name("Roy Balakrishnan"),
                    new Telegram("roy_bk"),
                    new Email("royb@example.com"),
                    new NusnetId("e0984983"),
                    getTagSet("smart"))
        };
    }

    public static ReadOnlyTaskmaster getSampleTaskmaster() {
        Taskmaster sampleTaskmaster = new Taskmaster();
        for (Student sampleStudent : getSampleStudents()) {
            sampleTaskmaster.addStudent(sampleStudent);
        }
        Session sampleSession = new Session(
                new SessionName("Sample session"),
                new SessionDateTime(LocalDateTime.of(2020, 1, 1, 12, 0)),
                Arrays.asList(getSampleStudents()));
        sampleTaskmaster.addSession(sampleSession);
        return sampleTaskmaster;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
