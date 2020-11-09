package team.serenity.model.group.lesson;

import static team.serenity.commons.util.AppUtil.checkArgument;

public class LessonName {

    public static final String MESSAGE_CONSTRAINTS =
        "Lesson name should not be blank and must follow the format 'X-X' or 'XX-X' where X is a digit from 0-9.";

    public static final String MESSAGE_LESSON_NAME_EMPTY =
        "Lesson name is empty.";

    /*
     * Must contain 1 or 2 digits, followed by a hypen and 1 digit
     * E.g. 1-1, 10-1 would pass but not 100-1 or 1-10
     */
    public static final String VALIDATION_REGEX = "^[0-9]{1,2}[-]{1}[1-9]{1}$";

    public final String lessonName;

    /**
     * Constructs a {@code StudentName}.
     *
     * @param name A valid name.
     */
    public LessonName(String name) {
        assert name != null;
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.lessonName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        assert test != null;
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return lessonName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof LessonName // instanceof handles nulls
            && lessonName.equals(((LessonName) other).lessonName)); // state check
    }

    @Override
    public int hashCode() {
        return lessonName.hashCode();
    }

}
