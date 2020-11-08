package team.serenity.model.group;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.UniqueLessonList;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.student.UniqueStudentList;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.group.studentinfo.UniqueStudentInfoList;
import team.serenity.model.util.UniqueList;

class GroupTest {

    @Test
    public void constructor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Group((GroupName) null, null));
        assertThrows(NullPointerException.class, () -> new Group((String) null, (String) null));
        assertThrows(NullPointerException.class, () -> new Group((String) null, (UniqueList<Student>) null));
        assertThrows(NullPointerException.class, () -> new Group((String) null, (UniqueList<Student>) null, null));
        assertThrows(NullPointerException.class, () -> new Group((GroupName) null, (UniqueList<Student>) null, null));
    }

    @Test
    void getName() {
        GroupName expectedGroup = new GroupName("G01");
        assertTrue(new Group("G01", new UniqueStudentList(),
            new UniqueLessonList()).getGroupName().equals(expectedGroup)); //same
    }

    @Test
    void getStudents() {
        UniqueList<Student> students = new UniqueStudentList();
        List<Student> expectedList = students.asUnmodifiableObservableList();
        assertTrue(new Group("G01", students, new UniqueLessonList()).getStudents().equals(students));
        assertTrue(new Group("G01", students, new UniqueLessonList())
            .getStudentsAsUnmodifiableObservableList().equals(expectedList));
    }

    @Test
    void getLessons() {
        UniqueList<Lesson> lessons = new UniqueLessonList();
        List<Lesson> expectedList = lessons.asUnmodifiableObservableList();
        assertTrue(new Group("G01", new UniqueStudentList(), lessons).getLessons().equals(lessons));
        assertTrue(new Group("G01", new UniqueStudentList(), lessons)
            .getLessonsAsUnmodifiableObservableList().equals(expectedList));
    }

    @Test
    void testEquals() {
        UniqueList<Student> students = new UniqueStudentList();
        Student aaron = new Student("Aaron Tan", "A0123456A");
        students.add(aaron);

        UniqueList<Lesson> lessons = new UniqueLessonList();

        UniqueList<StudentInfo> studentInfos = new UniqueStudentInfoList();
        studentInfos.add(new StudentInfo(aaron));
        lessons.add(new Lesson("1-1", studentInfos));

        Group group = new Group("G01", students,
            lessons);
        Group sameGroup = new Group("G01", students, lessons);
        Group differentStudentList = new Group("G01", new UniqueStudentList(),
            lessons);
        Group differentLessonList = new Group("G01", students,
            new UniqueLessonList());
        Group differentName = new Group("G02", students,
            lessons);

        assertTrue(group.equals(group)); //same object
        assertTrue(group.equals(sameGroup)); //different object, same contents

        //assertFalse(group.equals(differentLessonList)); //same group name, different lesson list
        assertFalse(group.equals(differentStudentList)); //same group name, different student list
        assertFalse(group.equals(differentName)); //different group name
    }

    @Test
    void testHashCode() {
        GroupName expectedGroup = new GroupName("G01");
        Group group = new Group("G01", new UniqueStudentList(),
            new UniqueLessonList());
        assertTrue(group.hashCode() == (expectedGroup.hashCode()));
    }

    @Test
    void testToString() {
        GroupName expectedGroup = new GroupName("G01");
        Group group = new Group("G01",
            new UniqueStudentList(),
            new UniqueLessonList());
        String expectedValue = String.format("Group %s", expectedGroup);
        assertTrue(group.toString().equals(expectedValue));
    }
}
