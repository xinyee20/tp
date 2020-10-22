package team.serenity.commons.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import team.serenity.model.group.Lesson;
import team.serenity.model.group.Student;
import team.serenity.model.group.StudentInfo;
import team.serenity.model.group.UniqueStudentInfoList;
import team.serenity.model.util.UniqueList;

public class XlsxUtil {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private DataFormatter formatter = new DataFormatter();

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
        return students;
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

    private void readDetailsOfStudents(Iterator<Row> rowIterator, Set<Student> students) {
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
        return lessons;
    }

    private void readDetailsOfLessons(Row headerRow, Set<Lesson> lessons, Set<StudentInfo> studentsInfo) {
        Iterator<Cell> cellIterator = headerRow.iterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            System.out.println("LALA: " + formatter.formatCellValue(cell));

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
        return studentsInfo;
    }

}
