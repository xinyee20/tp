package team.serenity.testutil;

import static team.serenity.testutil.TypicalGroups.GROUP_C;
import static team.serenity.testutil.TypicalGroups.GROUP_D;

import java.util.ArrayList;
import java.util.Arrays;

import team.serenity.model.group.student.Student;
import team.serenity.model.group.student.UniqueStudentList;
import team.serenity.model.managers.StudentManager;
import team.serenity.model.util.UniqueList;

public class TypicalStudent {

    public static final Student AARON = new StudentBuilder().withName("Aaron Tan").withId("A0123456U").build();
    public static final Student JOHN = new StudentBuilder().withName("John").withId("A0000001U").build();
    public static final Student JAMES = new StudentBuilder().withName("James").withId("A0000002U").build();
    public static final Student JEFFERY = new StudentBuilder().withName("Jeffery").withId("A0000003U").build();
    public static final Student LUNA = new StudentBuilder().withName("Luna").withId("A0000004U").build();
    public static final Student QUEENIE = new StudentBuilder().withName("Queenie").withId("A0000005U").build();
    public static final Student FREDDIE = new StudentBuilder().withName("Freddie").withId("A0000006U").build();
    public static final Student JUNE = new StudentBuilder().withName("June").withId("A0000007U").build();
    public static final Student JANE = new Student("JANE", "A0000008U");

    private TypicalStudent() {
    } // prevent instantiation

    public static UniqueList<Student> getTypicalStudents() {
        UniqueList<Student> list = new UniqueStudentList();
        list.setElementsWithList(new ArrayList<>(Arrays.asList(JEFFERY, LUNA, QUEENIE)));
        return list;
    }

    public static StudentManager getTypicalStudentManager() {
        StudentManager studentManager = new StudentManager();
        studentManager.setListOfStudentsToGroup(GROUP_C.getGroupName(), GROUP_C.getStudents());
        studentManager.setListOfStudentsToGroup(GROUP_D.getGroupName(), GROUP_D.getStudents());
        return studentManager;
    }
}
