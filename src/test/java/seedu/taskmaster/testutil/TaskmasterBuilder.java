package seedu.taskmaster.testutil;

import seedu.taskmaster.model.Taskmaster;
import seedu.taskmaster.model.session.Session;
import seedu.taskmaster.model.student.Student;

/**
 * A utility class to help with building Taskmaster objects.
 * Example usage: <br>
 *     {@code Taskmaster ab = new TaskmasterBuilder().withStudent("John", "Doe").build();}
 */
public class TaskmasterBuilder {

    private Taskmaster taskmaster;

    public TaskmasterBuilder() {
        taskmaster = new Taskmaster();
    }

    public TaskmasterBuilder(Taskmaster taskmaster) {
        this.taskmaster = taskmaster;
    }

    /**
     * Adds a new {@code Student} to the {@code Taskmaster} that we are building.
     */
    public TaskmasterBuilder withStudent(Student student) {
        taskmaster.addStudent(student);
        return this;
    }

    /**
     * Adds a new {@code Session} to the {@code Taskmaster} that we are building.
     */
    public TaskmasterBuilder withSession(Session session) {
        taskmaster.addSession(session);
        return this;
    }

    public Taskmaster build() {
        return taskmaster;
    }
}
