package team.serenity.model.group;

import static team.serenity.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

import javafx.collections.ObservableList;
import team.serenity.commons.util.XlsxUtil;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.UniqueLessonList;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.student.UniqueStudentList;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.util.UniqueList;

/**
 * Represents a tutorial Group in Serenity.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Group {

    // Identity field
    private final GroupName groupName;

    // Data fields
    private final UniqueList<Student> students;
    private final UniqueList<Lesson> lessons;

    /**
     * Constructs a {@code Group}.
     *
     * @param groupName A valid name.
     * @param filePath A valid filePath.
     */
    public Group(String groupName, String filePath) {
        requireAllNonNull(groupName, filePath);
        this.groupName = new GroupName(groupName);
        XlsxUtil util = new XlsxUtil(filePath);
        this.students = new UniqueStudentList();
        this.students.setElementsWithList(new ArrayList<>(util.readStudentsFromXlsx()));
        // TODO: implement scores data
        Set<StudentInfo> studentsInfo = util.readStudentsInfoFromXlsx(util.readStudentsFromXlsx());
        this.lessons = new UniqueLessonList();
        this.lessons.setElementsWithList(new ArrayList<>(util.readLessonsFromXlsx(studentsInfo)));
    }

    /**
     * Constructs a {@code Group}.
     *
     * @param groupName A valid group name.
     * @param grpExcelData A valid group excel data.
     */
    public Group(GroupName groupName, XlsxUtil grpExcelData) {
        requireAllNonNull(groupName, grpExcelData);
        this.groupName = groupName;
        this.students = new UniqueStudentList();
        this.students.setElementsWithList(new ArrayList<>(grpExcelData.readStudentsFromXlsx()));
        // TODO: implement scores data
        Set<StudentInfo> studentsInfo = grpExcelData.readStudentsInfoFromXlsx(grpExcelData.readStudentsFromXlsx());
        this.lessons = new UniqueLessonList();
        this.lessons.setElementsWithList(new ArrayList<>(grpExcelData.readLessonsFromXlsx(studentsInfo)));
    }

    /**
     * Constructs a {@code Group}.
     *
     * @param groupName A valid name.
     * @param students A list of students.
     */
    public Group(String groupName, UniqueList<Student> students) {
        requireAllNonNull(groupName, students);
        this.groupName = new GroupName(groupName);
        this.students = students;
        this.lessons = new UniqueLessonList();
    }

    /**
     * Constructs a {@code Group}.
     *
     * @param groupName A valid name.
     * @param students A list of students.
     * @param lessons A list of tutorial lessons.
     */
    public Group(String groupName, UniqueList<Student> students, UniqueList<Lesson> lessons) {
        requireAllNonNull(groupName, students, lessons);
        this.groupName = new GroupName(groupName);
        this.students = students;
        this.lessons = lessons;
    }

    public GroupName getGroupName() {
        return this.groupName;
    }

    public UniqueList<Student> getStudents() {
        return this.students;
    }

    public ObservableList<Student> getStudentsAsUnmodifiableObservableList() {
        return this.students.asUnmodifiableObservableList();
    }

    public ObservableList<Lesson> getLessonsAsUnmodifiableObservableList() {
        return this.lessons.asUnmodifiableObservableList();
    }

    public UniqueList<Lesson> getLessons() {
        return this.lessons;
    }

    public UniqueList<Student> getSortedStudents() {
        this.students.sort(Comparator.comparing(x -> x.getStudentName().toString()));
        return this.students;
    }

    public UniqueList<Lesson> getSortedLessons() {
        this.lessons.sort(Comparator.comparing(x -> x.getLessonName().toString()));
        return this.lessons;
    }

    /**
     * Adds a Student to a Group.
     *
     * @param student Student to be added
     */
    public void addStudentToGroup(Student student) {
        addToStudentList(student);
        this.students.sort(Comparator.comparing(x -> x.getStudentName().toString()));
        addToStudentListInLessons(student);
    }

    /**
     * Removes a Student from the Group.
     *
     * @param student Student to be added
     */
    public void deleteStudentFromGroup(Student student) {
        deleteFromStudentList(student);
        deleteStudentFromStudentListInLessons(student);
    }

    private void deleteFromStudentList(Student student) {
        this.students.remove(student);
    }

    private void addToStudentList(Student student) {
        this.students.add(student);
    }


    private void addToStudentListInLessons(Student student) {
        for (Lesson lesson : this.lessons) {
            StudentInfo newStudent = new StudentInfo(student);
            UniqueList<StudentInfo> studentInfos = lesson.getStudentsInfo();
            studentInfos.add(newStudent);
            studentInfos.sort(Comparator.comparing(x -> x.getStudent().getStudentName().toString()));
            Lesson updatedLesson = new Lesson(lesson.getLessonName(), studentInfos);
            this.lessons.setElement(lesson, updatedLesson);
        }
    }

    private void deleteStudentFromStudentListInLessons(Student student) {
        for (Lesson lesson : this.lessons) {
            StudentInfo newStudent = new StudentInfo(student);
            UniqueList<StudentInfo> studentInfos = lesson.getStudentsInfo();
            studentInfos.remove(newStudent);
            Lesson updatedLesson = new Lesson(lesson.getLessonName(), studentInfos);
            this.lessons.setElement(lesson, updatedLesson);
        }
    }

    /**
     * Returns true if both groups of the same name have at least one other identity field that is the same. This
     * defines a weaker notion of equality between two groups.
     */
    public boolean isSameGroup(Group otherGroup) {
        if (otherGroup == this) {
            return true;
        }

        return otherGroup != null
            && otherGroup.getGroupName().equals(getGroupName())
            && otherGroup.getStudents().equals(getStudents())
            && otherGroup.getLessons().equals(getLessons());
    }

    /**
     * Returns true if both groups have the same identity and data fields. This defines a stronger notion of equality
     * between two groups.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return otherGroup.getGroupName().equals(getGroupName())
            && otherGroup.getStudents().equals(getStudents())
            && otherGroup.getLessons().equals(getLessons());
    }

    @Override
    public int hashCode() {
        return this.groupName.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Group %s", this.groupName);
    }


}
