package seedu.address.logic.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.serenity.Student;

public class CSVUtil {

    // excel file from LUMINUS
    private Path filePath;

    public CSVUtil(Path filePath) {
        this.filePath = filePath;
    }

    public Set<Student> readStudentsFromCsv() {

        Set<Student> students = new HashSet<>();

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(filePath,
            StandardCharsets.US_ASCII)) {

            String line = "";

            // skip the first 4 lines
            for (int i = 1; i <= 4; i++) {
                line = br.readLine();
            }

            // loop until all lines are read
            while (line != null) {
                // use string.split to load a string array with the values from each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(",");

                Student student = createStudent(attributes);

                // adding order into ArrayList
                students.add(student);

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return students;
    }

    private static Student createStudent(String[] metadata) {
        String name = metadata[0];
        String studentNumber = metadata[1];

        return new Student(name, studentNumber);
    }

    //ToDo: add a method to export JSON data into CSV
}
