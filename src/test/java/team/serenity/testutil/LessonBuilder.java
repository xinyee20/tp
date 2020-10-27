package team.serenity.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.group.studentinfo.UniqueStudentInfoList;
import team.serenity.model.util.UniqueList;

public class LessonBuilder {
    public static final String DEFAULT_NAME = "1-1";
    public static final Set<StudentInfo> DEFAULT_STUDENT_INFO = new HashSet<>(Arrays.asList(
            new StudentInfo(new Student("Aaron Tan", "A0123456U")),
            new StudentInfo(new Student("Baron Wong", "A0654321C")),
            new StudentInfo(new Student("Cherry Lee", "A0135791B")),
            new StudentInfo(new Student("Dickson Low", "A0246810D")),
            new StudentInfo(new Student("Eng Wee Kiat", "A0101010E"))
    ));

    private String name;
    private UniqueList<StudentInfo> studentInfos = new UniqueStudentInfoList();

    /**
     * Creates a {@code LessonBuilder} with the default details.
     */

    public LessonBuilder() {
        this.name = DEFAULT_NAME;
        this.studentInfos.setElementsWithList(new ArrayList<>(DEFAULT_STUDENT_INFO));
    }

    /**
     * Initializes the group builder with the dat aof {@code LessonTocopy}.
     */

    public LessonBuilder(Lesson lessonToCopy) {
        this.name = lessonToCopy.getLessonName().toString();
        this.studentInfos = lessonToCopy.getStudentsInfo();
    }

    /**
     * Sets the {@code Name} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Parses the {@code studentInfos} into a {@code Set<StudentInfo>} and set it
     * to the {@code Lesson} that we are building
     */
    public LessonBuilder withStudentInfos(StudentInfo... studentInfos) {
        this.studentInfos.setElementsWithList(Arrays.asList(studentInfos));
        return this;
    }

    /**
     * Parses the {@code students} into a {@code Set<StudentInfo>} and set it
     * to the {@code Lesson} that we are building
     */
    public LessonBuilder withStudents(Student... student) {
        this.studentInfos.setElementsWithList(
                Arrays.stream(student).map(StudentInfo::new).collect(Collectors.toList())
        );
        return this;
    }

    public Lesson build() {
        return new Lesson(this.name, this.studentInfos);
    }
}
