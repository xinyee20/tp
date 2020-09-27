package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlySerenity;
import seedu.address.model.group.Group;
import seedu.address.model.Serenity;

/**
 * An Immutable Serenity that is serializable to JSON format.
 */
@JsonRootName(value = "serenity")
class JsonSerializableSerenity {

    public static final String MESSAGE_DUPLICATE_PERSON = "Group list contains duplicate person(s).";

    private final List<JsonAdaptedGroup> groups = new ArrayList<>();

    @JsonCreator
    public JsonSerializableSerenity(@JsonProperty("groups") List<JsonAdaptedGroup> groups) {
        this.groups.addAll(groups);
    }

    public JsonSerializableSerenity(ReadOnlySerenity source) {
        groups.addAll(source.getGroupList().stream().map(JsonAdaptedGroup::new).collect(Collectors.toList()));
    }

    public Serenity toModelType() throws IllegalValueException {
        Serenity serenity = new Serenity();
        for (JsonAdaptedGroup jsonAdaptedGroup : groups) {
            Group group = jsonAdaptedGroup.toModelType();
            if (serenity.hasGroup(group)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            serenity.addGroup(group);
        }
        return serenity;
    }

}
