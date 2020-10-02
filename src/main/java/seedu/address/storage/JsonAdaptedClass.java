package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.model.group.Lesson;

/**
 * Jackson-friendly version of {@link Class}.
 */
class JsonAdaptedClass {

    private final String name;

    /**
     * Constructs a {@code JsonAdaptedClass} with the given {@code name}.
     */
    @JsonCreator
    public JsonAdaptedClass(String name) {
        this.name = name;
    }

    /**
     * Converts a given {@code Class} into this class for Jackson use.
     */
    public JsonAdaptedClass(Lesson source) {
        name = source.getName();
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
