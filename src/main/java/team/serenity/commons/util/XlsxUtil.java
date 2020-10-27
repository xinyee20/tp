package team.serenity.commons.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.group.studentinfo.UniqueStudentInfoList;
import team.serenity.model.util.UniqueList;

/**
 * Reads XLSX file that the tutor downloads from LUMINUS and writes JSON data to a new XLSX file.
 */
public class XlsxUtil {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private DataFormatter formatter = new DataFormatter();

    /**
     * Creates a XlsxUtil object that manages XLSX files.
     *
     * @param filePath The path of the XLSX file that the tutor downloads from LUMINUS.
     */
    public XlsxUtil(String filePath) {
        try {
            workbook = new XSSFWorkbook(filePath);
            sheet = workbook.getSheetAt(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads XLSX file that the tutor downloads from LUMINUS.
     * The XLSX file stores a list of {@code Student} that are in a tutorial group.
     *
     * @return
     */
    public Set<Student> readStudentsFromXlsx() {
        Set<Student> students = new HashSet<>();
        Iterator<Row> rowIterator = sheet.iterator();
        skipRowsToHeaderRow(rowIterator);
        readDetailsOfStudents(rowIterator, students);
        List<Student> studentList = new ArrayList<>(students);
        Collections.sort(studentList, new StudentSorter());
        Set<Student> newStudents = new LinkedHashSet<>(studentList);
        return newStudents;
    }

    private Row skipRowsToHeaderRow(Iterator<Row> rowIterator) {
        Row row = null;
        while (rowIterator.hasNext()) {
            row = rowIterator.next();

            if (formatter.formatCellValue(row.getCell(0)).equals("Photo")
                && formatter.formatCellValue(row.getCell(1)).equals("Name")
                && formatter.formatCellValue(row.getCell(2)).equals("Student Number")) {
                break;
            }
        }
        return row;
    }

    private void readDetailsOfStudents(Iterator<Row> rowIterator,
        Set<Student> students) throws IllegalArgumentException {
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            Cell photoCell = cellIterator.next();
            // Photo

            Cell nameCell = cellIterator.next();
            String name = formatter.formatCellValue(nameCell);

            Cell studentIdCell = cellIterator.next();
            String studentId = formatter.formatCellValue(studentIdCell);

            Student student = new Student(name, studentId);
            students.add(student);
        }
    }

    /**
     * Reads a set of the Lessons
     * @param studentsInfo Set of StudentInfo
     * @return Set of Lessons
     */
    public Set<Lesson> readLessonsFromXlsx(Set<StudentInfo> studentsInfo) {
        Set<Lesson> lessons = new HashSet<>();
        Iterator<Row> rowIterator = sheet.iterator();
        Row headerRow = skipRowsToHeaderRow(rowIterator);
        readDetailsOfLessons(headerRow, lessons, studentsInfo);
        List<Lesson> lessonList = new ArrayList<>(lessons);
        Collections.sort(lessonList, new LessonSorter());
        Set<Lesson> newLessons = new LinkedHashSet<>(lessonList);
        return newLessons;
    }

    private void readDetailsOfLessons(Row headerRow, Set<Lesson> lessons, Set<StudentInfo> studentsInfo) {
        Iterator<Cell> cellIterator = headerRow.iterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();

            if (formatter.formatCellValue(cell).startsWith("T")) {
                String lessonName = formatter.formatCellValue(cell);
                String formattedLessonName = formatLessonName(lessonName);
                UniqueList<StudentInfo> newStudentsInfo = new UniqueStudentInfoList();
                newStudentsInfo.setElementsWithList(new ArrayList<>(studentsInfo));
                lessons.add(new Lesson(formattedLessonName, newStudentsInfo));
            }
        }
    }

    private String formatLessonName(String lessonName) {
        String trimmedLessonName = lessonName.substring(1); // remove the first character "T" from the lessonName
        int lessonNumbering = Integer.parseInt(trimmedLessonName);
        boolean isEvenWeek = lessonNumbering % 2 == 0;
        int weekNumber = isEvenWeek ? lessonNumbering / 2 : lessonNumbering / 2 + 1;
        int lessonNumber = isEvenWeek ? 2 : 1;
        return String.format("%d-%d", weekNumber, lessonNumber);
    }

    /**
     * Creates a new set of StudentInfo from XLSX.
     *
     * @return Set of studentInfo.
     */
    public Set<StudentInfo> readStudentsInfoFromXlsx(Set<Student> students) {
        Set<StudentInfo> studentsInfo = new HashSet<>();
        for (Student student : students) {
            studentsInfo.add(new StudentInfo(student));
        }
        List<StudentInfo> studentInfoList = new ArrayList<>(studentsInfo);
        Collections.sort(studentInfoList, new StudentInfoSorter());
        Set<StudentInfo> newStudentsInfo = new LinkedHashSet<>(studentInfoList);
        return newStudentsInfo;
    }

    private class LessonSorter implements Comparator<Lesson> {

        @Override
        public int compare(Lesson lessonOne, Lesson lessonTwo) {
            String lesOne = lessonOne.getLessonName().lessonName;
            int lesOneLen = lesOne.length();
            String lesTwo = lessonTwo.getLessonName().lessonName;
            int lesTwoLen = lesTwo.length();
            int minLength = Math.min(lesOneLen, lesTwoLen);
            for (int i = 0; i < minLength; i++) {
                int lesOneChar = (int) lesOne.charAt(i);
                int lesTwoChar = (int) lesTwo.charAt(i);

                if (lesOneChar != lesTwoChar) {
                    return lesOneChar - lesTwoChar;
                }
            }

            if (lesOneLen != lesTwoLen) {
                return lesOneLen - lesTwoLen;
            } else {
                return 0;
            }
        }
    }

    private class StudentSorter implements Comparator<Student> {

        @Override
        public int compare(Student studentOne, Student studentTwo) {
            String sOne = studentOne.getStudentName().fullName;
            int sOneLen = sOne.length();
            String sTwo = studentTwo.getStudentName().fullName;
            int sTwoLen = sTwo.length();
            int minLength = Math.min(sOneLen, sTwoLen);
            for (int i = 0; i < minLength; i++) {
                int lesOneChar = (int) sOne.charAt(i);
                int lesTwoChar = (int) sTwo.charAt(i);

                if (lesOneChar != lesTwoChar) {
                    return lesOneChar - lesTwoChar;
                }
            }

            if (sOneLen != sTwoLen) {
                return sOneLen - sTwoLen;
            } else {
                return 0;
            }
        }
    }

    private class StudentInfoSorter implements Comparator<StudentInfo> {

        @Override
        public int compare(StudentInfo studentInfoOne, StudentInfo studentInfoTwo) {
            String infoOne = studentInfoOne.getStudent().getStudentName().fullName;
            int infoOneLen = infoOne.length();
            String infoTwo = studentInfoTwo.getStudent().getStudentName().fullName;
            int infoTwoLen = infoTwo.length();
            int minLength = Math.min(infoOneLen, infoTwoLen);
            for (int i = 0; i < minLength; i++) {
                int infoOneChar = (int) infoOne.charAt(i);
                int infoTwoChar = (int) infoTwo.charAt(i);

                if (infoOneChar != infoTwoChar) {
                    return infoOneChar - infoTwoChar;
                }
            }

            if (infoOneLen != infoTwoLen) {
                return infoOneLen - infoTwoLen;
            } else {
                return 0;
            }
        }
    }
}

