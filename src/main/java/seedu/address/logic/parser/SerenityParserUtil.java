package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Student;
import seedu.address.model.person.Name;

public class SerenityParserUtil {

    /**
     * Parses a {@code String student} into a {@code Student}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code student} is invalid.
     */
    public static String parseStudent(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (! Student.isValidString(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return trimmedName;
    }
}
