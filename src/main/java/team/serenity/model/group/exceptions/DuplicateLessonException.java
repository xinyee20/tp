package team.serenity.model.group.exceptions;

/**
 * Signals that the operation will result in duplicate Lesson (Lessons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateLessonException extends RuntimeException {
    public DuplicateLessonException() {
        super("Operation would result in duplicate Lesson!");
    }
}
