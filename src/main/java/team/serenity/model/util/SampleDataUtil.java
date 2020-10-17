package team.serenity.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import team.serenity.model.ReadOnlySerenity;
import team.serenity.model.Serenity;
import team.serenity.model.group.Group;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.Student;
import team.serenity.model.group.StudentInfo;
import team.serenity.model.group.UniqueLessonList;
import team.serenity.model.group.UniqueStudentInfoList;
import team.serenity.model.group.UniqueStudentList;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Group[] getSampleGroups() {
        Set<StudentInfo> studentsInfo = getStudentInfoSet(new Student("John", "E0123456"),
            new Student("James", "E02030303"));
        UniqueStudentInfoList studentsInfoList = new UniqueStudentInfoList();
        studentsInfoList.setStudentInfo(new ArrayList<>(studentsInfo));

        Set<Student> students = getStudentSet(new Student("John", "E0123456"),
            new Student("James", "E02030303"));
        UniqueStudentList studentsList = new UniqueStudentList();
        studentsList.setStudents(new ArrayList<>(students));

        UniqueLessonList lessonsList = new UniqueLessonList();
        Set<Lesson> lessons = new HashSet<>();
        lessons.add(new Lesson("1-1", studentsInfoList));
        return new Group[] {new Group("G04", studentsList, lessonsList)};
    }

    public static ReadOnlySerenity getSampleSerenity() {
        Serenity sampleS = new Serenity();
        for (Group sampleGroup : getSampleGroups()) {
            sampleS.addGroup(sampleGroup);
        }
        return sampleS;
    }

    public static Set<StudentInfo> getStudentInfoSet(Student... students) {
        Set<StudentInfo> studentsInfo = new HashSet<>();
        for (Student student : students) {
            studentsInfo.add(new StudentInfo(student));
        }
        return studentsInfo;
    }

    /**
     * Returns a student set containing the list of strings given.
     */
    public static Set<Student> getStudentSet(Student... students) {
        return Arrays.stream(students)
            .collect(Collectors.toSet());
    }

    /**
     * Returns a class set containing the list of strings given.
     */
    public static Set<Class> getClassSet(Class... classes) {
        return Arrays.stream(classes)
            .collect(Collectors.toSet());
    }
}
