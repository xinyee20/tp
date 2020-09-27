package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Student;

/**
 * Jackson-friendly version of {@link Student}.
 */
class JsonAdaptedStudent {

    private final String name;
    private final String studentNumber;

    @JsonCreator
    public JsonAdaptedStudent(String name, String studentNumber) {
        this.name = name;
        this.studentNumber = studentNumber;
    }

    public JsonAdaptedStudent(Student source) {
        name = source.getName();
        studentNumber = source.getStudentNumber();
    }

    public Student toModelType() throws IllegalValueException {
        // add some validation
        return new Student(name, studentNumber);
    }

}
