package team.serenity.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import team.serenity.model.group.Group;
import team.serenity.model.group.UniqueGroupList;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.UniqueLessonList;
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
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Group[] getSampleGroups() {
        Set<StudentInfo> studentsInfo = getStudentInfoSet(new Student("John", "A6543210C"),
            new Student("James", "A0123456C"));
        UniqueList<StudentInfo> studentsInfoList = new UniqueStudentInfoList();
        studentsInfoList.setElementsWithList(new ArrayList<>(studentsInfo));

        Set<Student> students = getStudentSet(new Student("John", "A6543210C"),
            new Student("James", "A0123456C"));
        UniqueList<Student> studentsList = new UniqueStudentList();
        studentsList.setElementsWithList(new ArrayList<>(students));

        UniqueList<Lesson> lessonsList = new UniqueLessonList();
        lessonsList.add(new Lesson("1-1", studentsInfoList));
        return new Group[] {new Group("G04", studentsList, lessonsList)};
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

    /**
     * Returns a lesson set containing the list of strings given.
     */
    public static Set<Lesson> getLessonSet(Lesson... lessons) {
        return Arrays.stream(lessons)
            .collect(Collectors.toSet());
    }

    public static Question[] getSampleQuestion() {
        return new Question[]{
            new Question("G04", "2-2", "What is the deadline for the report?"),
            new Question("G05", "3-1", "When is the consultation held?")
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
