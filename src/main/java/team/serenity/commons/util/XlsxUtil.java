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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import team.serenity.model.group.Student;

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

    public static void main(String[] args) {
        XlsxUtil util = new XlsxUtil("CS2101_G04.xlsx");
        util.readStudentsFromXLSX();
    }
}
