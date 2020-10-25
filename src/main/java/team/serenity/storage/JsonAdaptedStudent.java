package team.serenity.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import team.serenity.commons.exceptions.IllegalValueException;
import team.serenity.model.group.Student;

/**
 * Jackson-friendly version of {@link Student}.
 */
class JsonAdaptedStudent {

    private final String name;
    private final String studentNo;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given {@code name} and {@ocde studentNumber}.
     */
    @JsonCreator
    public JsonAdaptedStudent(String name, String studentNo) {
        this.name = name;
        this.studentNo = studentNo;
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        this.name = source.getName();
        this.studentNo = source.getStudentNo();
    }

    @JsonValue
    public String getName() {
        return this.name;
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's {@code Student} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student.
     */
    public Student toModelType() throws IllegalValueException {
        // add some validation
        return new Student(this.name, this.studentNo);
    }

}
