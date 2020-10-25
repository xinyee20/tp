package team.serenity.testutil;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import team.serenity.commons.util.CsvUtil;
import team.serenity.model.group.Group;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.Student;
import team.serenity.model.group.StudentInfo;
import team.serenity.model.group.UniqueLessonList;
import team.serenity.model.group.UniqueStudentInfoList;
import team.serenity.model.group.UniqueStudentList;
import team.serenity.model.util.UniqueList;

/**
 * A utility class to help with building Group objects.
 */
public class GroupBuilder {

    public static final String DEFAULT_NAME = "G04";
    public static final Set<Student> DEFAULT_STUDENTS = new HashSet<>(Arrays.asList(
        new Student("Aaron Tan", "A0123456U"),
        new Student("Baron Wong", "A0654321C"),
        new Student("Cherry Lee", "A0135791B"),
        new Student("Dickson Low", "A0246810D"),
        new Student("Eng Wee Kiat", "A0101010E")

    ));
    public static final Set<Lesson> DEFAULT_CLASSES = new HashSet<>(Arrays.asList(
    ));

    private String name;
    private UniqueList<Student> students = new UniqueStudentList();
    private UniqueList<Lesson> lessons = new UniqueLessonList();

    /**
     * Creates a {@code GroupBuilder} with the default details.
     */
    public GroupBuilder() {
        name = DEFAULT_NAME;
        students.setElementsWithList(new ArrayList<>(DEFAULT_STUDENTS));
        lessons.setElementsWithList(new ArrayList<>(DEFAULT_CLASSES));
    }

    /**
     * Initializes the GroupBuilder with the data of {@code groupToCopy}.
     */
    public GroupBuilder(Group groupToCopy) {
        name = groupToCopy.getName();
        students = groupToCopy.getStudents();
        lessons = groupToCopy.getLessons();
    }

    /**
     * Initializes the GroupBuilder from the data inside the CSV file.
     */
    public GroupBuilder(String name, Path filePath) {
        this.name = name;
        students.setElementsWithList(new ArrayList<>(new CsvUtil(filePath).readStudentsFromCsv()));
        lessons.setElementsWithList(new ArrayList<>());
    }

    /**
     * Sets the {@code Name} of the {@code Group} that we are building.
     */
    public GroupBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Parses the {@code students} into a {@code Set<Student>} and set it to the {@code Group} that we are building.
     */
    public GroupBuilder withStudents(Student... students) {
        this.students.setElementsWithList(Arrays.asList(students));
        return this;
    }

    /**
     * Parses the {@code filePath} into a {@code Set<Student>} and set it to the {@code Group} that we are building.
     */
    public GroupBuilder withFilePath(Path filePath) {
        students.setElementsWithList(new ArrayList<>(new CsvUtil(filePath).readStudentsFromCsv()));
        return this;
    }

    /**
     * Creates and parses the {@code classes} into a {@code Set<Class>} and set it to the {@code Group} that we are
     * building.
     */
    public GroupBuilder withClasses(String... classes) {
        UniqueList<StudentInfo> studentsInfo = new UniqueStudentInfoList();
        for (Student student : students) {
            studentsInfo.add(new StudentInfo(student));
        }
        for (String className : classes) {
            this.lessons.add(new Lesson(className, studentsInfo));
        }
        return this;
    }

    public Group build() {
        return new Group(this.name, this.students, this.lessons);
    }

}
