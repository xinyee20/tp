package team.serenity.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_FILE_PATH = "Invalid file path!\nIf you are unsure on how to get"
        + " the file path, type the 'help' command to refer to our user guide.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

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

    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "Index %d is invalid.";
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    public static final String MESSAGE_NOT_VIEWING_A_GROUP = "Group not specified.";
    public static final String MESSAGE_NOT_VIEWING_A_LESSON = "You are trying to use a lesson specific command "
            + "but you are not viewing a lesson. please use the viewlsn command first.";

    public static final String MESSAGE_QUESTIONS_LISTED_OVERVIEW = "%1$d %2$s listed!";
    public static final String MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX = "The question index provided is invalid.";

    public static final String MESSAGE_NO_FLAGGED_ATTENDANCE = "There is no flagged attendance to follow up with.";
    public static final String MESSAGE_NO_PENDING_QUESTIONS = "There are no pending questions to answer.";
    public static final String MESSAGE_NO_STUDENTS =
        "There are no students in this group. You may use addstudent command to add a student.";
    public static final String MESSAGE_NO_LESSONS =
        "There are no lessons in this group. Yoo may use addlsn command to add a lesson.";
}
