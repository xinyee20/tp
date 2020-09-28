package seedu.address.testutil;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.parser.CsvUtil;
import seedu.address.model.group.Class;
import seedu.address.model.group.Group;
import seedu.address.model.group.Student;
import seedu.address.model.util.SampleDataUtil;

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
    public static final Set<Class> DEFAULT_CLASSES = new HashSet<>(Arrays.asList(
    ));

    private String name;
    private Set<Student> students;
    private Set<Class> classes;

    /**
     * Creates a {@code GroupBuilder} with the default details.
     */
    public GroupBuilder() {
        name = DEFAULT_NAME;
        students = DEFAULT_STUDENTS;
        classes = DEFAULT_CLASSES;
    }

    /**
     * Initializes the GroupBuilder with the data of {@code groupToCopy}.
     */
    public GroupBuilder(Group groupToCopy) {
        name = groupToCopy.getName();
        students = new HashSet<>(groupToCopy.getStudents());
        classes = new HashSet<>(groupToCopy.getClasses());
    }

    /**
     * Initializes the GroupBuilder from the data inside the CSV file.
     */
    public GroupBuilder(String name, Path filePath) {
        this.name = name;
        students = new CsvUtil(filePath).readStudentsFromCsv();
        classes = new HashSet<>();
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
    public GroupBuilder withStudents(Student ... students) {
        this.students = SampleDataUtil.getStudentSet(students);
        return this;
    }

    /**
     * Parses the {@code filePath} into a {@code Set<Student>} and set it to the {@code Group} that we are building.
     */
    public GroupBuilder withFilePath(Path filePath) {
        students = new CsvUtil(filePath).readStudentsFromCsv();
        return this;
    }

    /**
     * Parses the {@code classes} into a {@code Set<Class>} and set it to the {@code Group} that we are building.
     */
    public GroupBuilder withClasses(Class ... classes) {
        this.classes = SampleDataUtil.getClassSet(classes);
        return this;
    }

    public Group build() {
        return new Group(name, students, classes);
    }

}
