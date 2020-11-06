package team.serenity.testutil;

import static team.serenity.testutil.TypicalGroups.GROUP_G01;
import static team.serenity.testutil.TypicalGroups.GROUP_G02;

import java.util.ArrayList;
import java.util.Arrays;

import team.serenity.model.group.student.Student;
import team.serenity.model.group.student.UniqueStudentList;
import team.serenity.model.managers.StudentManager;
import team.serenity.model.util.UniqueList;

public class TypicalStudent {

    public static final Student AARON = new StudentBuilder().withName("Aaron Tan").withId("A0123456A").build();
    public static final Student BENJAMIN = new Student("Benjamin Barker", "A0123456B");
    public static final Student CATHERINE = new StudentBuilder().withName("Catherine Teo").withId("A0123456C").build();
    public static final Student DAVID = new StudentBuilder().withName("David Chong").withId("A0123456D").build();
    public static final Student ELFIE = new StudentBuilder().withName("Elfie Ang").withId("A0123456E").build();
    public static final Student FREDDIE = new StudentBuilder().withName("Freddie Lim").withId("A0123456F").build();
    public static final Student GEORGE = new StudentBuilder().withName("George").withId("A0123456G").build();
    public static final Student HELENE = new StudentBuilder().withName("Helene Ong").withId("A0123456H").build();
    public static final Student IRENE = new StudentBuilder().withName("Irene").withId("A0123456I").build();
    public static final Student JEFFERY = new StudentBuilder().withName("Jeffery").withId("A0123456J").build();

    private TypicalStudent() {
    } // prevent instantiation

    public static UniqueList<Student> getTypicalStudents() {
        UniqueList<Student> list = new UniqueStudentList();
        list.setElementsWithList(new ArrayList<>(Arrays.asList(JEFFERY, IRENE, ELFIE)));
        return list;
    }

    public static StudentManager getTypicalStudentManager() {
        StudentManager studentManager = new StudentManager();
        studentManager.setListOfStudentsToGroup(GROUP_G01.getGroupName(), GROUP_G01.getStudents());
        studentManager.setListOfStudentsToGroup(GROUP_G02.getGroupName(), GROUP_G02.getStudents());
        return studentManager;
    }
}
