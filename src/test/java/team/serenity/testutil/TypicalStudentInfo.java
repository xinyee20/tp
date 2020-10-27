package team.serenity.testutil;

import static team.serenity.testutil.TypicalStudent.JAMES;
import static team.serenity.testutil.TypicalStudent.JANE;
import static team.serenity.testutil.TypicalStudent.JOHN;
import static team.serenity.testutil.TypicalStudent.JUNE;

import java.util.ArrayList;
import java.util.Arrays;

import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.group.studentinfo.UniqueStudentInfoList;
import team.serenity.model.managers.StudentInfoManager;
import team.serenity.model.util.UniqueList;

public class TypicalStudentInfo {

    public static final StudentInfo JAMES_INFO = new StudentInfoBuilder().withStudent(JAMES)
            .withAttendance(false)
            .withParticipation(0)
            .build();

    public static final StudentInfo JOHN_INFO = new StudentInfoBuilder().withStudent(JOHN)
            .withAttendance(false)
            .withParticipation(0)
            .build();

    public static final StudentInfo JUNE_INFO = new StudentInfoBuilder().withStudent(JUNE)
            .withAttendance(false)
            .withParticipation(0)
            .build();

    public static final StudentInfo JANE_INFO = new StudentInfoBuilder().withStudent(JANE)
            .withAttendance(false)
            .withParticipation(0)
            .build();

    public TypicalStudentInfo() {
    } //prevents instantiation

    public static UniqueList<StudentInfo> getTypicalStudentInfo() {
        UniqueList<StudentInfo> list = new UniqueStudentInfoList();
        list.setElementsWithList(new ArrayList<>(Arrays.asList(JAMES_INFO, JOHN_INFO)));
        return list;
    }

    public static StudentInfoManager getTypicalStudentInfoManager() {
        StudentInfoManager studentInfoManager = new StudentInfoManager();
        studentInfoManager.setListOfStudentsInfoToGroupLessonKey(new GroupLessonKeyBuilder().build(),
                getTypicalStudentInfo());
        return studentInfoManager;
    }
}
