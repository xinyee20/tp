package team.serenity.model.group.student;


import static java.util.Objects.requireNonNull;
import static team.serenity.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class StudentName {

    public static final String MESSAGE_CONSTRAINTS =
        "Student name should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code StudentName}.
     *
     * @param name A valid name.
     */
    public StudentName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.fullName = name.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof StudentName // instanceof handles nulls
            && fullName.toUpperCase().equals(((StudentName) other).fullName.toUpperCase())); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
