package team.serenity.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.LessonName;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
class JsonAdaptedLesson {

    private final LessonName name;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given {@code name}.
     */
    @JsonCreator
    public JsonAdaptedLesson(String name) {
        this.name = new LessonName(name);
    }

    /**
     * Converts a given {@code Lesson} into this Lesson for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        this.name = source.getLessonName();
    }

    @JsonValue
    public String getName() {
        return this.name.toString();
    }
}
