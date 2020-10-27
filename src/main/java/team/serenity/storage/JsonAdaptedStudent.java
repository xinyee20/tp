package team.serenity.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import team.serenity.model.group.student.Student;

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
    public JsonAdaptedStudent(@JsonProperty("name") String name, @JsonProperty("studentNo") String number) {
        this.name = name;
        this.studentNo = number;
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        this.name = source.getStudentName().toString();
        this.studentNo = source.getStudentNo().toString();
    }


    /**
     * Converts this Jackson-friendly adapted student object into the model's {@code Student} object.
     *
     * @throws IllegalArgumentException if there were any data constraints violated in the adapted student.
     */
    public Student toModelType() throws IllegalArgumentException {
        // add some validation
        return new Student(this.name, this.studentNo);
    }

}
