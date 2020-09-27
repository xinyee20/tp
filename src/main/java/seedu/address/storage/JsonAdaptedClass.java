package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Class;

/**
 * Jackson-friendly version of {@link Class}.
 */
class JsonAdaptedClass {

    private final String name;
    // add some more fields

    @JsonCreator
    public JsonAdaptedClass(String name) {
        this.name = name;
    }

    public JsonAdaptedClass(Class source) {
        name = source.getName();
    }

    public Class toModelType() throws IllegalValueException {
        // add some validation
        return new Class(name);
    }

}
