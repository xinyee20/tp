package seedu.address.commons.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.group.Lesson;
import seedu.address.model.group.Score;
import seedu.address.model.group.Student;

/**
 * Reads CSV file that the tutor downloads from LUMINUS and writes JSON data to a new CSV file.
 */
public class CsvUtil {

    /**
     * The path of the CSV file that the tutor downloads from LUMINUS.
     */
    private Path filePath;

    /**
     * Creates a CsvUtil object that manages CSV files.
     *
     * @param filePath The path of the CSV file that the tutor downloads from LUMINUS.
     */
    public CsvUtil(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads CSV file that the tutor downloads from LUMINUS. The CSV file stores a list of {@code Student} that are in a
     * tutorial group.
     *
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

    /**
     * Reads a set of Scores and creates the Lessons
     * @param scores Set of Scores
     * @return Set of Lessons
     */
    public Set<Lesson> readLessonsFromCsv(Set<Score> scores) {
        Set<Lesson> lessons = new HashSet<>();
        try (BufferedReader br = Files.newBufferedReader(filePath,
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
                String lessonName = computeClassName(i - 3); //start from 1
                lessons.add(new Lesson(lessonName, scores));
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return lessons;
    }

    /**
     * Given a number, find out the class name E.g. 1 will return "1-1", 3 will return "2-1", 4 will return "2-2"
     * @param number Number of class, e.g. lesson 1,2,3
     * @return Class name
     */
    public static String computeClassName(int number) {
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
     * Creates a new set of Scores from CSV
     *
     * @return Set of scores
     */
    public Set<Score> readScoresFromCsv(Set<Student> students) {
        Set<Score> scores = new HashSet<>();
        for (Student student : students) {
            scores.add(new Score(student));
        }
        return scores;
    }

    private Lesson createClass(String name, Set<Score> scores) {
        return new Lesson(name, scores);
    }

    private static Student createStudent(String[] metadata) {
        String name = metadata[1];
        String studentNumber = metadata[2];

        return new Student(name, studentNumber);
    }

    //ToDo: add a method to export JSON data into CSV
}
