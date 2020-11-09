package team.serenity.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_FILE_PATH = "Invalid file path!\nIf you are unsure on how to get"
        + " the file path, type the 'help' command to refer to our user guide.";
    public static final String MESSAGE_FILE_PATH_EMPTY = "File path is empty.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

    public static final String MESSAGE_INVALID_FILE = "An error has occurred while reading the file.";
    public static final String MESSAGE_INVALID_FILE_NON_XLSX = "The file used is not of .xlsx file type.";
    public static final String MESSAGE_FILE_EMPTY = "The .xlsx file is empty.";
    public static final String MESSAGE_INVALID_HEADER_COLUMNS = "The .xlsx file is either missing the "
        + "Photo, Name and Student Number header columns, or these columns are placed in a wrong order.";
    public static final String MESSAGE_NO_STUDENT_LIST = "The .xlsx file is missing a list of students.";

    public static final String MESSAGE_GROUP_LISTED_OVERVIEW = "You are in tutorial group %1$s.\n"
        + "You can view the summary of every"
        + " lesson in this group here, or view individual lessons with the viewlsn command!";
    public static final String MESSAGE_GROUP_EMPTY = "No such group!";

    public static final String MESSAGE_ATTENDANCE_LISTED_OVERVIEW = "These are "
        + "the attendance records for tutorial group %1$s.";

    public static final String MESSAGE_SCORE_LISTED_OVERVIEW = "These are the "
        + "participation scores for tutorial group %1$s.";

    public static final String MESSAGE_LESSON_LISTED_OVERVIEW = "You are in "
        + "tutorial group %1$s, lesson %2$s.\n"
        + "You can view the lesson, "
        + "mark attendance, assign participation scores and add questions asked in this lesson.";
    public static final String MESSAGE_LESSON_EMPTY = "no such lesson!";

    public static final String MESSAGE_STUDENT_EMPTY = "No such student!";
    public static final String MESSAGE_STUDENT_NOT_FOUND =
            "%s is not found. \nPlease ensure the name & student id is correct!";
    public static final String MESSAGE_DUPLICATE_STUDENT = "Student already exists!";

    public static final String MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX = "Index %d is invalid.";
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    public static final String MESSAGE_NOT_VIEWING_A_GROUP = "Group not specified.";
    public static final String MESSAGE_NOT_VIEWING_A_LESSON = "You are trying to use a lesson specific command "
            + "but you are not viewing a lesson. please use the viewlsn command first.";

    public static final String MESSAGE_QUESTIONS_LISTED_OVERVIEW = "%1$d %2$s listed!";
    public static final String MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX = "The question index provided is invalid.";

    public static final String MESSAGE_NO_FLAGGED_ATTENDANCE = "There is no flagged attendance to follow up with.";
    public static final String MESSAGE_NO_PENDING_QUESTIONS = "There are no pending questions to answer.";
    public static final String MESSAGE_NO_QUESTIONS =
        "There are no pending questions to answer in this lesson. You may use addqn command to add a question.";
    public static final String MESSAGE_NO_STUDENTS =
        "There are no students in this group. You may use addstudent command to add a student.";
    public static final String MESSAGE_NO_LESSONS =
        "There are no lessons in this group. Yoo may use addlsn command to add a lesson.";

    public static final String MESSAGE_SCORE_NOT_WITHIN_RANGE = "Updated score should be within range of 0 to 5";
    public static final String MESSAGE_ADDED_SCORE_NOT_WITHIN_RANGE = "Adding %1$d to the score would result in %2$d,"
            + " which is out of range (0 - 5), please enter another number";
    public static final String MESSAGE_SUBTRACTED_SCORE_NOT_WITHIN_RANGE = "Subtracting %1$d to the score would "
            + "result %2$d, which is out of range (0 - 5), please enter another number";
    public static final String MESSAGE_SCORE_TO_ADD = "Score to add should be a positive number greater than 0";
    public static final String MESSAGE_SCORE_TO_SUB = "Score to subtract should be a positive number greater than 0";

    public static final String MESSAGE_ASSERTION_ERROR_METHOD = "Execution of method should not fail.";
}
