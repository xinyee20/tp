package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Class;

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
    public JsonAdaptedClass(Class source) {
        name = source.getName();
    }

    @JsonValue
    public String getName() {
        return name;
    }

    /**
     * Converts this Jackson-friendly adapted class object into the model's {@code Class} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted class.
     */
    public Class toModelType() throws IllegalValueException {
        // add some validation
        return new Class(name);
    }

}
