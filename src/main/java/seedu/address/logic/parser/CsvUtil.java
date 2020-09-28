package seedu.address.logic.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.group.Student;

/**
 * Reads CSV file that the tutor downloads from LUMINUS and writes JSON data to a new CSV file.
 */
public class CsvUtil {

    /** The path of the CSV file that the tutor downloads from LUMINUS. */
    private Path filePath;

    /**
     * Creates a CsvUtil object that manages CSV files.
     * @param filePath The path of the CSV file that the tutor downloads from LUMINUS.
     */
    public CsvUtil(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads CSV file that the tutor downloads from LUMINUS.
     * The CSV file stores a list of {@code Student} that are in a tutorial group.
     * @return
     */
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
