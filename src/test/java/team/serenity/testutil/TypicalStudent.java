package team.serenity.testutil;

import static team.serenity.testutil.TypicalGroups.GROUP_C;
import static team.serenity.testutil.TypicalGroups.GROUP_D;

import java.util.ArrayList;
import java.util.Arrays;

import team.serenity.model.group.Student;
import team.serenity.model.group.UniqueStudentList;
import team.serenity.model.managers.StudentManager;
import team.serenity.model.util.UniqueList;

public class TypicalStudent {

    public static final Student JOHN = new StudentBuilder().withName("John").withId("A1234567U").build();
    public static final Student JAMES = new StudentBuilder().withName("James").withId("A7654321U").build();
    public static final Student JEFFERY = new StudentBuilder().withName("Jeffery").withId("A0000001U").build();
    public static final Student LUNA = new StudentBuilder().withName("Luna").withId("A0111111U").build();
    public static final Student QUEENIE = new StudentBuilder().withName("Queenie").withId("A0222222U").build();
    public static final Student FREDDIE = new StudentBuilder().withName("Freddie").withId("A0000001U").build();
    public static final Student JUNE = new StudentBuilder().withName("June").withId("A0101011U").build();
    public static final Student JANE = new Student("JANE", "A7654320U");

    private TypicalStudent() {
    } // prevent instantiation

    public static UniqueList<Student> getTypicalStudents() {
        UniqueList<Student> list = new UniqueStudentList();
        list.setElementsWithList(new ArrayList<>(Arrays.asList(JEFFERY, LUNA, QUEENIE)));
        return list;
    }

    public static StudentManager getTypicalStudentManager() {
        StudentManager studentManager = new StudentManager();
        studentManager.setListOfStudentsToGroup(GROUP_C, GROUP_C.getStudents());
        studentManager.setListOfStudentsToGroup(GROUP_D, GROUP_D.getStudents());
        return studentManager;
    }
}
