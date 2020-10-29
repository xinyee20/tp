package team.serenity.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import team.serenity.commons.exceptions.IllegalValueException;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.Attendance;
import team.serenity.model.group.studentinfo.Participation;
import team.serenity.model.group.studentinfo.StudentInfo;

public class JsonAdaptedStudentInfo {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "StudentInfo's %s field is missing!";

    private final JsonAdaptedStudent student;
    private final boolean isPresent;
    private final boolean isFlagged;
    private final int participation;

    /**
     * Jackson-friendly version of {@link StudentInfo}.
     * @param source
     */
    public JsonAdaptedStudentInfo(StudentInfo source) {
        this.student = new JsonAdaptedStudent(source.getStudent());
        this.isPresent = source.getAttendance().isPresent();
        this.isFlagged = source.getAttendance().isFlagged();
        this.participation = source.getParticipation().getScore();
    }


    /**
     * Creates a Jackson-friendly version of {@link StudentInfo} from JSON.
     * @param student
     * @param isPresent
     * @param isFlagged
     * @param participation
     */
    @JsonCreator
    public JsonAdaptedStudentInfo(@JsonProperty("student") JsonAdaptedStudent student,
        @JsonProperty("isPresent") boolean isPresent,
        @JsonProperty("isFlagged") boolean isFlagged,
        @JsonProperty("participation") int participation
    ) {
        this.student = student;
        this.isPresent = isPresent;
        this.isFlagged = isFlagged;
        this.participation = participation;
    }

    /**
     * Converts this Jackson-friendly adapted StudentInfo object into the model's {@code StudentInfo} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted group.
     */
    public StudentInfo toModelType() throws IllegalValueException {

        if (this.student == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Student.class.getSimpleName()));
        }

        if (!Participation.isValidParticipation(this.participation)) {
            throw new IllegalValueException(Participation.MESSAGE_CONSTRAINTS);
        }

        if (!Attendance.isValidAttendance(isPresent, isFlagged)) {
            throw new IllegalValueException(Attendance.MESSAGE_CONSTRAINTS);
        }

        Student student = this.student.toModelType();
        Attendance attendance = new Attendance(this.isPresent, this.isFlagged);
        Participation participation = new Participation(this.participation);
        return new StudentInfo(student, participation, attendance);
    }
}
