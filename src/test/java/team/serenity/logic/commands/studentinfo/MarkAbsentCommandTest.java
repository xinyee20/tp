package team.serenity.logic.commands.studentinfo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import team.serenity.model.group.Group;
import team.serenity.model.group.UniqueGroupList;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.UniqueLessonList;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.util.UniqueList;
import team.serenity.testutil.GroupBuilder;
import team.serenity.testutil.LessonBuilder;
import team.serenity.testutil.ModelStub;

class MarkAbsentCommandTest {

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {

    }

    @Test
    public void execute_unmarkStudent_success() {
    }

    @Test
    public void execute_wrongName_throwsCommandException() {

    }

    @Test
    public void execute_wrongStudentId_throwsCommandException() {

    }

    @Test
    public void execute_missingStudentName_throwsCommandException() {

    }

    @Test
    public void execute_missingStudentId_throwsCommandException() {

    }

}

/**
 * A Model stub containing present students
 */
class ModelStubWithStudentsPresent extends ModelStub {

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        List<Group> grpList = new ArrayList<>();
        grpList.add(new GroupBuilder().build());
        UniqueList<Group> groupUniqueList = new UniqueGroupList();
        groupUniqueList.setElementsWithList(grpList);
        return groupUniqueList.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        List<Lesson> lsnList = new ArrayList<>();
        lsnList.add(new LessonBuilder().build());
        UniqueList<Lesson> lessonUniqueList = new UniqueLessonList();
        lessonUniqueList.setElementsWithList(lsnList);
        return lessonUniqueList.asUnmodifiableObservableList();
    }

    @Override
    public void updateStudentsInfoList() {
        return;
    }

    @Override
    public void updateLessonList() {
        return;
    }

}

/**
 * A Model stub containing a present student
 */
class ModelStubWithIndexPresent extends ModelStub {

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        List<Group> grpList = new ArrayList<>();
        grpList.add(new GroupBuilder().withName("G01")
                .withStudents(new Student("Aaron Tan", "A0123456U"))
                .withClasses("1-1").build());
        UniqueList<Group> groupUniqueList = new UniqueGroupList();
        groupUniqueList.setElementsWithList(grpList);
        return groupUniqueList.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        List<Lesson> lsnList = new ArrayList<>();
        lsnList.add(new LessonBuilder()
                .withName("1-1")
                .withStudentInfos(new StudentInfo(new Student("Aaron Tan", "A0123456U")))
                .build());
        UniqueList<Lesson> lessonUniqueList = new UniqueLessonList();
        lessonUniqueList.setElementsWithList(lsnList);
        return lessonUniqueList.asUnmodifiableObservableList();
    }

    @Override
    public void updateLessonList() {
        return;
    }

    @Override
    public void updateStudentsInfoList() {
        return;
    }

}
