package team.serenity.model.group.student;


import static java.util.Objects.requireNonNull;
import static team.serenity.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class StudentNumber {

    public static final String MESSAGE_CONSTRAINTS =
        "Student number must be alphanumeric, "
            + "starts with A and is 9 characters long, ending with an uppercase character.";

    /*
     * Alphanumeric, no white space
     */
    public static final String VALIDATION_REGEX = "[A]{1}[0-9]{7}[A-Z]{1}";

    public final String studentNumber;

    /**
     * Constructs a {@code StudentName}.
     *
     * @param name A valid name.
     */
    public StudentNumber(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.studentNumber = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX) && test.length() == 9 && test.startsWith("A");
    }


    @Override
    public String toString() {
        return studentNumber;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof StudentNumber // instanceof handles nulls
            && studentNumber.equals(((StudentNumber) other).studentNumber)); // state check
    }

    @Override
    public int hashCode() {
        return studentNumber.hashCode();
    }

}
