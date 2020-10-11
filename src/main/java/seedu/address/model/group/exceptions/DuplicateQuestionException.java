package seedu.address.model.group.exceptions;

/**
 * Signals that the operation will result in duplicate Questions (Questions are considered duplicates
 * if they have the same values).
 */
public class DuplicateQuestionException extends RuntimeException {

    public DuplicateQuestionException() {
        super("Operation would result in duplicate Question!");
    }

}
