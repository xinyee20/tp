package team.serenity.testutil;

import static team.serenity.testutil.TypicalStudent.AARON;
import static team.serenity.testutil.TypicalStudent.BENJAMIN;
import static team.serenity.testutil.TypicalStudent.CATHERINE;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import team.serenity.commons.core.sorter.LessonSorter;
import team.serenity.commons.core.sorter.StudentSorter;
import team.serenity.commons.util.CsvUtil;
import team.serenity.commons.util.XlsxUtil;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.UniqueLessonList;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.student.UniqueStudentList;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.group.studentinfo.UniqueStudentInfoList;
import team.serenity.model.util.UniqueList;

/**
 * A utility class to help with building Group objects.
 */
public class GroupBuilder {

    public static final String DEFAULT_NAME = "G01";
    public static final Set<Student> DEFAULT_STUDENTS =
            new HashSet<>(Arrays.asList(AARON, BENJAMIN, CATHERINE));
    public static final Set<Lesson> DEFAULT_CLASSES = new HashSet<>(Arrays.asList());

    private GroupName name;
    private UniqueList<Student> students = new UniqueStudentList();
    private UniqueList<Lesson> lessons = new UniqueLessonList();

    /**
     * Creates a {@code GroupBuilder} with the default details.
     */
    public GroupBuilder() {
        name = new GroupName(DEFAULT_NAME);
        students.setElementsWithList(new ArrayList<>(DEFAULT_STUDENTS));
        students.sort(new StudentSorter());
        lessons.setElementsWithList(new ArrayList<>(DEFAULT_CLASSES));
        lessons.sort(new LessonSorter());
    }

    /**
     * Initializes the GroupBuilder with the data of {@code groupToCopy}.
     */
    public GroupBuilder(Group groupToCopy) {
        name = groupToCopy.getGroupName();
        students = groupToCopy.getStudents();
        lessons = groupToCopy.getLessons();
    }

    /**
     * Initializes the GroupBuilder from the data inside the CSV file.
     */
    public GroupBuilder(String name, Path filePath) {
        this.name = new GroupName(name);
        students.setElementsWithList(new ArrayList<>(new CsvUtil(filePath).readStudentsFromCsv()));
        students.sort(new StudentSorter());
        lessons.setElementsWithList(new ArrayList<>());
    }

    /**
     * Sets the {@code Name} of the {@code Group} that we are building.
     */
    public GroupBuilder withName(String name) {
        this.name = new GroupName(name);
        return this;
    }

    /**
     * Parses the {@code students} into a {@code Set<Student>} and set it to the {@code Group} that we are building.
     */
    public GroupBuilder withStudents(Student... students) {
        this.students.setElementsWithList(Arrays.asList(students));
        this.students.sort(new StudentSorter());
        return this;
    }

    /**
     * Parses the {@code filePath} into a {@code Set<Student>} and set it to the {@code Group} that we are building.
     */
    public GroupBuilder withFilePath(String filePath) {
        try {
            students.setElementsWithList(new ArrayList<>(new XlsxUtil(filePath,
                new XSSFWorkbook(filePath)).readStudentsFromXlsx()));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
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
        return new Group(this.name.toString(), this.students, this.lessons);
    }

}
