package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlySerenity;
import seedu.address.model.Serenity;
import seedu.address.model.group.Group;
import seedu.address.model.group.Lesson;
import seedu.address.model.group.Student;
import seedu.address.model.group.StudentInfo;
import seedu.address.model.group.UniqueLessonList;
import seedu.address.model.group.UniqueStudentInfoList;
import seedu.address.model.group.UniqueStudentList;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }

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
