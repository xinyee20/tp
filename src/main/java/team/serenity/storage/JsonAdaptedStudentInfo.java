package team.serenity.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.Attendance;
import team.serenity.model.group.studentinfo.Participation;
import team.serenity.model.group.studentinfo.StudentInfo;

public class JsonAdaptedStudentInfo {

    private final JsonAdaptedStudent student;
    private final boolean isPresent;
    private final boolean isFlagged;
    private final int participation;

    public JsonAdaptedStudentInfo(StudentInfo source) {
        this.student = new JsonAdaptedStudent(source.getStudent());
        this.isPresent = source.getAttendance().getAttendance();
        this.isFlagged = source.getAttendance().getFlagged();
        this.participation = source.getParticipation().getScore();
    }

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

    public StudentInfo toModelType() throws IllegalArgumentException {
        Student student = this.student.toModelType();
        Attendance attendance = new Attendance(this.isPresent, this.isFlagged);
        Participation participation = new Participation(this.participation);
        return new StudentInfo(student, participation, attendance);
    }
}
