package team.serenity.testutil;

import static team.serenity.testutil.TypicalStudentInfo.AARON_ABSENT_INFO;
import static team.serenity.testutil.TypicalStudentInfo.BENJAMIN_ABSENT_INFO;
import static team.serenity.testutil.TypicalStudentInfo.CATHERINE_ABSENT_INFO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import team.serenity.commons.core.sorter.StudentInfoSorter;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.group.studentinfo.UniqueStudentInfoList;
import team.serenity.model.util.UniqueList;

public class LessonBuilder {
    public static final String DEFAULT_NAME = "1-1";
    public static final Set<StudentInfo> DEFAULT_STUDENT_INFO =
            new HashSet<>(Arrays.asList(AARON_ABSENT_INFO, BENJAMIN_ABSENT_INFO, CATHERINE_ABSENT_INFO));

    private String name;
    private UniqueList<StudentInfo> studentsInfo = new UniqueStudentInfoList();

    /**
     * Creates a {@code LessonBuilder} with the default details.
     */

    public LessonBuilder() {
        this.name = DEFAULT_NAME;
        this.studentsInfo.setElementsWithList(new ArrayList<>(DEFAULT_STUDENT_INFO));
        this.studentsInfo.sort(new StudentInfoSorter());
    }

    /**
     * Initializes the group builder with the dat aof {@code LessonTocopy}.
     */

    public LessonBuilder(Lesson lessonToCopy) {
        this.name = lessonToCopy.getLessonName().toString();
        this.studentsInfo = lessonToCopy.getStudentsInfo();
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
        this.studentsInfo.setElementsWithList(Arrays.asList(studentInfos));
        this.studentsInfo.sort(new StudentInfoSorter());
        return this;
    }

    /**
     * Parses the {@code students} into a {@code Set<StudentInfo>} and set it
     * to the {@code Lesson} that we are building
     */
    public LessonBuilder withStudents(Student... student) {
        this.studentsInfo.setElementsWithList(
                Arrays.stream(student).map(StudentInfo::new).collect(Collectors.toList())
        );
        this.studentsInfo.sort(new StudentInfoSorter());
        return this;
    }

    /**
     * Builds the lesson.
     */
    public Lesson build() {
        this.studentsInfo.sort(Comparator.comparing(x -> x.getStudent().getStudentName().toString()));
        return new Lesson(this.name, this.studentsInfo);
    }
}
