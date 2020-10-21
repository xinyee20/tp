package team.serenity.model.group.exceptions;

public class DuplicateException extends RuntimeException {
    public DuplicateException() {
        super("Operation would result in duplicate elements");
    }
}
