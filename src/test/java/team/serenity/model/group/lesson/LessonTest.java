package team.serenity.model.group.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.TypicalStudent.JAMES;
import static team.serenity.testutil.TypicalStudent.JOHN;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.Attendance;
import team.serenity.model.group.studentinfo.Participation;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.group.studentinfo.UniqueStudentInfoList;
import team.serenity.model.util.UniqueList;

public class LessonTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson((String) null, (UniqueStudentInfoList) null));
        assertThrows(NullPointerException.class, () -> new Lesson((LessonName) null, (UniqueStudentInfoList) null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        UniqueList<StudentInfo> studentsInfo = new UniqueStudentInfoList();
        StudentInfo studentInfo = new StudentInfo(JOHN, new Participation(), new Attendance());
        studentsInfo.add(studentInfo);
        assertThrows(IllegalArgumentException.class, () -> new Lesson(invalidName, studentsInfo));
    }

    @Test
    public void constructor_emptyTutorial_throwsIllegalArgumentException() {
        String name = "1-1";
        Set<Student> students = new HashSet<>();
        assertThrows(IllegalArgumentException.class, () -> new Lesson(name, new UniqueStudentInfoList()));
    }

    @Test
    public void equals() {
        UniqueList<StudentInfo> studentsInfo = new UniqueStudentInfoList();
        StudentInfo studentInfo = new StudentInfo(JOHN);
        studentsInfo.add(studentInfo);
        Lesson oneOne = new Lesson("1-1", studentsInfo);
        Lesson oneOneClone = new Lesson("1-1", studentsInfo);
        Lesson classTwoOne = new Lesson("2-1", studentsInfo); //same students, different name
        UniqueList<StudentInfo> newStudentsInfo = new UniqueStudentInfoList();
        newStudentsInfo.add(new StudentInfo(JOHN));
        newStudentsInfo.add(new StudentInfo(JAMES));
        Lesson oneOneWithTwoStudents = new Lesson("1-1", newStudentsInfo);
        Lesson twoOneWithTwoStudents = new Lesson("2-1", newStudentsInfo);
        assertTrue(oneOneClone.equals(oneOne)); //same
        assertFalse(oneOneWithTwoStudents.equals(oneOne)); //same class name, different students
        assertFalse(classTwoOne.equals(oneOne)); //different class name, same students
        assertFalse(twoOneWithTwoStudents.equals(oneOne)); //different class name, different students
    }
}
