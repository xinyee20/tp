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

/**
 * A utility class to help with building Group objects.
 */
public class GroupBuilder {

    public static final String DEFAULT_NAME = "G04";
    public static final Set<Student> DEFAULT_STUDENTS = new HashSet<>(Arrays.asList(
        new Student("Aaron Tan", "e0123456"),
        new Student("Baron Wong", "e0654321"),
        new Student("Cherry Lee", "e0135791"),
        new Student("Dickson Low", "e0246810"),
        new Student("Eng Wee Kiat", "e0101010")

    ));
    public static final Set<Lesson> DEFAULT_CLASSES = new HashSet<>(Arrays.asList(
    ));

    private String name;
    private UniqueStudentList students = new UniqueStudentList();
    private UniqueLessonList classes = new UniqueLessonList();

    /**
     * Creates a {@code GroupBuilder} with the default details.
     */
    public GroupBuilder() {
        name = DEFAULT_NAME;
        students.setStudents(new ArrayList<>(DEFAULT_STUDENTS));
        classes.setLessons(new ArrayList<>(DEFAULT_CLASSES));
    }

    /**
     * Initializes the GroupBuilder with the data of {@code groupToCopy}.
     */
    public GroupBuilder(Group groupToCopy) {
        name = groupToCopy.getName();
        students = groupToCopy.getStudents();
        classes = groupToCopy.getLessons();
    }

    /**
     * Initializes the GroupBuilder from the data inside the CSV file.
     */
    public GroupBuilder(String name, Path filePath) {
        this.name = name;
        students.setStudents(new ArrayList<>(new CsvUtil(filePath).readStudentsFromCsv()));
        classes.setLessons(new ArrayList<>());
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
        this.students.setStudents(Arrays.asList(students));
        return this;
    }

    /**
     * Parses the {@code filePath} into a {@code Set<Student>} and set it to the {@code Group} that we are building.
     */
    public GroupBuilder withFilePath(Path filePath) {
        students.setStudents(new ArrayList<>(new CsvUtil(filePath).readStudentsFromCsv()));
        return this;
    }

    /**
     * Creates and parses the {@code classes} into a {@code Set<Class>} and set it to the {@code Group} that we are
     * building.
     */
    public GroupBuilder withClasses(String... classes) {
        UniqueStudentInfoList studentsInfo = new UniqueStudentInfoList();
        for (Student student : students) {
            studentsInfo.add(new StudentInfo(student));
        }
        for (String className : classes) {
            this.classes.add(new Lesson(className, studentsInfo));
        }
        return this;
    }

    public Group build() {
        return new Group(name, students, classes);
    }

}
