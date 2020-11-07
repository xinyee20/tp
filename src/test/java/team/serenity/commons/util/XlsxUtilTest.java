package team.serenity.commons.util;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.serenity.commons.core.Messages.MESSAGE_ASSERTION_ERROR_METHOD;
import static team.serenity.commons.core.Messages.MESSAGE_FILE_EMPTY;
import static team.serenity.commons.core.Messages.MESSAGE_INVALID_HEADER_COLUMNS;
import static team.serenity.commons.core.Messages.MESSAGE_NO_STUDENT_LIST;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_XLSX_EMPTY;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_XLSX_NO_HEADER_COLUMNS;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_XLSX_NO_STUDENTS;
import static team.serenity.logic.commands.CommandTestUtil.INVALID_XLSX_WRONG_HEADER_COLUMNS;
import static team.serenity.logic.commands.CommandTestUtil.VALID_PATH_A;
import static team.serenity.logic.commands.CommandTestUtil.VALID_XLSX_NO_LESSONS;
import static team.serenity.logic.commands.CommandTestUtil.VALID_XLSX_NO_TITLE;
import static team.serenity.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.group.studentinfo.UniqueStudentInfoList;
import team.serenity.model.util.UniqueList;
import team.serenity.testutil.StudentBuilder;

public class XlsxUtilTest {

    private Set<Student> expectedStudents = ExpectedGroup.getExpectedStudents();

    private Set<StudentInfo> expectedStudentsInfo = ExpectedGroup.getExpectedStudentsInfo();

    private Set<Lesson> expectedLessons = ExpectedGroup.getExpectedLessons();

    @Test
    public void readStudentsFromXlsx_validXlsx_success() {
        XlsxUtil util = new XlsxUtil(VALID_PATH_A);
        Set<Student> students = util.readStudentsFromXlsx();
        assertTrue(students.size() == expectedStudents.size() && students.retainAll(expectedStudents));
    }

    @Test
    public void readStudentsFromXlsx_invalidXlsxNoStudents() {
        XlsxUtil util = new XlsxUtil(INVALID_XLSX_NO_STUDENTS);
        Set<Student> students = util.readStudentsFromXlsx();
        assertTrue(students.isEmpty());
    }

    @Test
    public void readStudentsFromXlsx_invalidXlsxNoHeaderColumns() {
        XlsxUtil util = new XlsxUtil(INVALID_XLSX_NO_HEADER_COLUMNS);
        Set<Student> students = util.readStudentsFromXlsx();
        assertTrue(students.isEmpty());
    }

    @Test
    public void checkValidityOfXlsx_validXlsx_success() {
        try {
            XlsxUtil util = new XlsxUtil(VALID_PATH_A);
            util.checkValidityOfXlsx();
        } catch (ParseException e) {
            throw new AssertionError(MESSAGE_ASSERTION_ERROR_METHOD, e);
        }
    }

    @Test
    public void checkValidityOfXlsx_validXlsxNoTitle_success() {
        try {
            XlsxUtil util = new XlsxUtil(VALID_XLSX_NO_TITLE);
            util.checkValidityOfXlsx();
        } catch (ParseException e) {
            throw new AssertionError("Execution of method should not fail", e);
        }
    }

    @Test
    public void checkValidityOfXlsx_validXlsxNoLessons_success() {
        try {
            XlsxUtil util = new XlsxUtil(VALID_XLSX_NO_LESSONS);
            util.checkValidityOfXlsx();
        } catch (ParseException e) {
            throw new AssertionError("Execution of method should not fail", e);
        }
    }

    @Test
    public void checkValidityOfXlsx_invalidXlsxEmpty_throwsParseException() {
        XlsxUtil util = new XlsxUtil(INVALID_XLSX_EMPTY);
        assertThrows(ParseException.class, MESSAGE_FILE_EMPTY, util::checkValidityOfXlsx);
    }

    @Test
    public void checkValidityOfXlsx_invalidXlsxNoHeaderColumns_throwsParseException() {
        XlsxUtil util = new XlsxUtil(INVALID_XLSX_NO_HEADER_COLUMNS);
        assertThrows(ParseException.class, MESSAGE_INVALID_HEADER_COLUMNS, util::checkValidityOfXlsx);
    }

    @Test
    public void checkValidityOfXlsx_invalidXlsxWrongHeaderColumns_throwsParseException() {
        XlsxUtil util = new XlsxUtil(INVALID_XLSX_WRONG_HEADER_COLUMNS);
        assertThrows(ParseException.class, MESSAGE_INVALID_HEADER_COLUMNS, util::checkValidityOfXlsx);
    }

    @Test
    public void checkValidityOfXlsx_invalidXlsxNoStudents_throwsParseException() {
        XlsxUtil util = new XlsxUtil(INVALID_XLSX_NO_STUDENTS);
        assertThrows(ParseException.class, MESSAGE_NO_STUDENT_LIST, util::checkValidityOfXlsx);
    }

    @Test
    public void readLessonsFromXlsx_validXlsx_success() {
        XlsxUtil util = new XlsxUtil(VALID_PATH_A);
        Set<Lesson> lessons = util.readLessonsFromXlsx(expectedStudentsInfo);
        assertTrue(lessons.size() == expectedLessons.size() && lessons.retainAll(expectedLessons));
    }

    @Test
    public void readLessonsFromXlsx_validXlsxNoLessons_success() {
        XlsxUtil util = new XlsxUtil(VALID_XLSX_NO_LESSONS);
        Set<Lesson> lessons = util.readLessonsFromXlsx(expectedStudentsInfo);
        assertTrue(lessons.isEmpty());
    }

    @Test
    public void readStudentsInfoFromXlsx_validXlsx_success() {
        XlsxUtil util = new XlsxUtil(VALID_PATH_A);
        Set<StudentInfo> studentsInfo = util.readStudentsInfoFromXlsx(expectedStudents);
        assertTrue(studentsInfo.size() == expectedStudentsInfo.size()
            && studentsInfo.retainAll(expectedStudentsInfo));
    }

    private static class ExpectedGroup {

        private static final Student AARON_TAN =
            new StudentBuilder().withName("AARON TAN").withId("A0123456U").build();
        private static final Student BU_WEN_JIN =
            new StudentBuilder().withName("BU WEN JIN").withId("A0123456M").build();
        private static final Student LAU_XIN_YEE =
            new StudentBuilder().withName("LAU XIN YEE").withId("A0765432U").build();
        private static final Student LIM_CHUN_YONG =
            new StudentBuilder().withName("LIM CHUN YONG").withId("A0388443R").build();
        private static final Student LIM_JIA_RUI_RYAN =
            new StudentBuilder().withName("LIM JIA RUI RYAN").withId("A0587314L").build();
        private static final Student NEO_RUI_EN_MAYBELLINE =
            new StudentBuilder().withName("NEO RUI EN MAYBELLINE").withId("A0139345U").build();

        public static Set<Student> getExpectedStudents() {
            return new LinkedHashSet<>() {
                {
                    add(AARON_TAN);
                    add(BU_WEN_JIN);
                    add(LAU_XIN_YEE);
                    add(LIM_CHUN_YONG);
                    add(LIM_JIA_RUI_RYAN);
                    add(NEO_RUI_EN_MAYBELLINE);
                }
            };
        }

        public static Set<StudentInfo> getExpectedStudentsInfo() {
            return new LinkedHashSet<>() {
                {
                    add(new StudentInfo(AARON_TAN));
                    add(new StudentInfo(BU_WEN_JIN));
                    add(new StudentInfo(LAU_XIN_YEE));
                    add(new StudentInfo(LIM_CHUN_YONG));
                    add(new StudentInfo(LIM_JIA_RUI_RYAN));
                    add(new StudentInfo(NEO_RUI_EN_MAYBELLINE));
                }
            };
        }

        public static Set<Lesson> getExpectedLessons() {
            return new LinkedHashSet<>() {
                {
                    UniqueList<StudentInfo> studentInfoList = new UniqueStudentInfoList();
                    studentInfoList.setElementsWithList(new ArrayList<>(getExpectedStudentsInfo()));
                    add(new Lesson("1-1", studentInfoList));
                    add(new Lesson("1-2", studentInfoList));
                    add(new Lesson("2-1", studentInfoList));
                    add(new Lesson("2-2", studentInfoList));
                    add(new Lesson("3-1", studentInfoList));
                    add(new Lesson("3-2", studentInfoList));
                    add(new Lesson("4-1", studentInfoList));
                }
            };
        }
    }

}
