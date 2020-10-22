package team.serenity.commons.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public Set<Student> readStudentsFromXLSX() {

        Set<Student> students = new HashSet<>();

        Iterator<Row> rowIterator = sheet.iterator();

        // Skip lines until the line containing the headers is reached
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if (formatter.formatCellValue(row.getCell(0)).equals("Photo")
                && formatter.formatCellValue(row.getCell(1)).equals("Name")
                && formatter.formatCellValue(row.getCell(2)).equals("Student Number")) {
                break;
            }
        }

        // Read details of each student
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

        return students;
    }

    /**
     * Reads a set of the Lessons
     * @param studentsInfo Set of StudentInfo
     * @return Set of Lessons
     */
    public Set<Lesson> readLessonsFromXlsx(Set<StudentInfo> studentsInfo) {
        Set<Lesson> lessons = new HashSet<>();

        /*
        try (BufferedReader br = Files.newBufferedReader(this.filePath,
            StandardCharsets.US_ASCII)) {

            String categories = "";

            // skip the first 3 lines
            for (int i = 1; i <= 3; i++) {
                categories = br.readLine();
            }

            //create classes
            String[] row = categories.split(","); //photos, name, userid, email, ....
            int len = row.length;
            for (int i = 4; i < len; i++) {
                String lessonName = computeLessonName(i - 3); //start from 1
                UniqueList<StudentInfo> newStudentsInfo = new UniqueStudentInfoList();
                newStudentsInfo.setElementsWithList(new ArrayList<>(studentsInfo));
                lessons.add(new Lesson(lessonName, newStudentsInfo));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
         */

        return lessons;
    }

    /**
     * Given a number, find out the tutorial lesson name.
     * E.g. 1 will return "1-1", 3 will return "2-1", 4 will return "2-2".
     * @param number The week of the tutorial lesson, e.g. Tutorial lesson 1, 2, 3.
     * @return The number of the tutorial Lesson.
     */
    public static String computeLessonName(int number) {
        int weekNumber = number / 2;
        double remainder = number % 2;
        if (remainder != 0) {
            //e.g. if number = 3, expected weekNumber is 2.
            // 3/2 gives 1, which we add 1 to get us our desired result
            weekNumber++;
        }
        int lessonNumber =
            remainder == 0 ? 2 : 1; //e.g. if number = 3, expected output is 2-1 i.e. lessonNumber = 1
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

    public static void main(String[] args) {
        XlsxUtil util = new XlsxUtil("CS2101_G04.xlsx");
        util.readStudentsFromXLSX();
    }
}
