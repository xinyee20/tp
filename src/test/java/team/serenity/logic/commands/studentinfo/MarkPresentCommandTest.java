package team.serenity.logic.commands.studentinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.serenity.commons.core.Messages.MESSAGE_STUDENT_NOT_FOUND;
import static team.serenity.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import team.serenity.commons.core.index.Index;
import team.serenity.logic.commands.CommandResult;
import team.serenity.logic.commands.exceptions.CommandException;
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

class MarkPresentCommandTest {

    @Test
    public void execute_markStudent_success() throws CommandException {
        ModelStubWithStudentsAbsent modelStub = new ModelStubWithStudentsAbsent();
        Student toMarkPresent = new Student("Aaron Tan", "A0123456U");

        CommandResult commandResult = new MarkPresentCommand(toMarkPresent).execute(modelStub);
        assertEquals(String.format(MarkPresentCommand.MESSAGE_SUCCESS, toMarkPresent), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_wrongName_throwsCommandException() throws CommandException {
        ModelStubWithStudentsAbsent modelStub = new ModelStubWithStudentsAbsent();
        Student wrongName = new Student("Aaron", "A0123456U");
        MarkPresentCommand markPresentCommand = new MarkPresentCommand(wrongName);

        assertThrows(CommandException.class, String.format(MESSAGE_STUDENT_NOT_FOUND, wrongName), () -> markPresentCommand.execute(modelStub));
    }

    @Test
    public void execute_wrongStudentNumber_throwsCommandException() {
        ModelStubWithStudentsAbsent modelStub = new ModelStubWithStudentsAbsent();
        Student wrongNumber = new Student("Aaron Tan", "A0000000U");
        MarkPresentCommand markPresentCommand = new MarkPresentCommand(wrongNumber);

        assertThrows(CommandException.class, String.format(MESSAGE_STUDENT_NOT_FOUND, wrongNumber), () -> markPresentCommand.execute(modelStub));
    }

    @Test
    public void execute_markIndex_success() throws CommandException {
        ModelStubWithIndexAbsent modelStub = new ModelStubWithIndexAbsent();
        Index validIndex = Index.fromOneBased(Integer.parseInt("1"));
        Student toMarkPresent = new Student("Aaron Tan", "A0123456U");

        CommandResult commandResult = new MarkPresentCommand(validIndex).execute(modelStub);
        assertEquals(String.format(MarkPresentCommand.MESSAGE_SUCCESS, toMarkPresent), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_wrongIndex_throwsCommandException() {
        ModelStubWithIndexAbsent modelStub = new ModelStubWithIndexAbsent();
        Index wrongIndex = Index.fromOneBased(Integer.parseInt("2"));
        MarkPresentCommand markPresentCommand = new MarkPresentCommand(wrongIndex);

        assertThrows(IndexOutOfBoundsException.class, () -> markPresentCommand.execute(modelStub));
    }

    @Test
    public void execute_markAllStudent_success() throws CommandException {
        ModelStubWithStudentsAbsent modelStub = new ModelStubWithStudentsAbsent();

        CommandResult commandResult = new MarkPresentCommand().execute(modelStub);
        assertEquals(String.format(MarkPresentCommand.MESSAGE_ALL_SUCCESS), commandResult.getFeedbackToUser());
    }

}

/**
 * A Model stub containing an absent student
 */
class ModelStubWithStudentsAbsent extends ModelStub {

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

class ModelStubWithIndexAbsent extends ModelStub {

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