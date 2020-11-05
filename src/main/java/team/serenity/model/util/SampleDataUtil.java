package team.serenity.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import team.serenity.model.group.Group;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.UniqueGroupList;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.LessonName;
import team.serenity.model.group.lesson.UniqueLessonList;
import team.serenity.model.group.question.Description;
import team.serenity.model.group.question.Question;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.student.UniqueStudentList;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.group.studentinfo.UniqueStudentInfoList;
import team.serenity.model.managers.QuestionManager;
import team.serenity.model.managers.ReadOnlyQuestionManager;
import team.serenity.model.managers.ReadOnlySerenity;
import team.serenity.model.managers.Serenity;

/**
 * Contains utility methods for populating {@code Serenity} with sample data.
 */
public class SampleDataUtil {

    private static final Student STUDENT_AARON = new Student("Aaron Tan", "A0123456A");
    private static final Student STUDENT_BENJAMIN = new Student("Benjamin Barker", "A0123456B");
    private static final Student STUDENT_CATHERINE = new Student("Catherine Teo", "A0123456C");

    private static final LessonName LESSON_1_1 = new LessonName("1-1");
    private static final LessonName LESSON_1_2 = new LessonName("1-2");

    private static final GroupName GROUP_G01 = new GroupName("G01");

    public static Group[] getSampleGroups() {
        Set<StudentInfo> studentsInfo = getStudentInfoSet(STUDENT_AARON, STUDENT_BENJAMIN, STUDENT_CATHERINE);
        UniqueList<StudentInfo> studentsInfoList = new UniqueStudentInfoList();
        studentsInfoList.setElementsWithList(new ArrayList<>(studentsInfo));
        studentsInfoList.sort((s1, s2) ->
                s1.getStudent().getStudentName().fullName.compareTo(s2.getStudent().getStudentName().fullName));

        Set<Student> students = getStudentSet(STUDENT_AARON, STUDENT_BENJAMIN, STUDENT_CATHERINE);
        UniqueList<Student> studentsList = new UniqueStudentList();
        studentsList.setElementsWithList(new ArrayList<>(students));
        studentsList.sort((s1, s2) -> s1.getStudentName().fullName.compareTo(s2.getStudentName().fullName));

        UniqueList<Lesson> lessonsList = new UniqueLessonList();
        lessonsList.add(new Lesson(LESSON_1_1, studentsInfoList));
        lessonsList.add(new Lesson(LESSON_1_2, studentsInfoList));
        return new Group[] {new Group(GROUP_G01, studentsList, lessonsList)};
    }

    public static ReadOnlySerenity getSampleSerenity() {
        UniqueList<Group> groups = new UniqueGroupList();
        for (Group sampleGroup : getSampleGroups()) {
            groups.add(sampleGroup);
        }
        return new Serenity(groups.asUnmodifiableObservableList());
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

    public static Question[] getSampleQuestion() {
        return new Question[]{
            new Question(GROUP_G01, LESSON_1_1,
                new Description("What is the deadline for the report?")),
            new Question(GROUP_G01, LESSON_1_2,
                new Description("When is the consultation held?"))
        };
    }

    public static ReadOnlyQuestionManager getSampleQuestionManager() {
        QuestionManager sampleQuestionManager = new QuestionManager();
        for (Question sampleQuestion : getSampleQuestion()) {
            sampleQuestionManager.addQuestion(sampleQuestion);
        }
        return sampleQuestionManager;
    }

}
