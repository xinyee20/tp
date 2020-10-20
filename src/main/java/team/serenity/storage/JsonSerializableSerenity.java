package team.serenity.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import team.serenity.commons.exceptions.IllegalValueException;
import team.serenity.model.ReadOnlySerenity;
import team.serenity.model.Serenity;
import team.serenity.model.group.Group;

/**
 * An Immutable Serenity that is serializable to JSON format.
 */
@JsonRootName(value = "serenity")
class JsonSerializableSerenity {

    public static final String MESSAGE_DUPLICATE_PERSON =
        "Group list contains duplicate person(s).";

    private final List<JsonAdaptedGroup> groups = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableSerenity} with the given groups.
     */
    @JsonCreator
    public JsonSerializableSerenity(@JsonProperty("groups") List<JsonAdaptedGroup> groups) {
        this.groups.addAll(groups);
    }

    /**
     * Converts a given {@code ReadOnlySerenity} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableSerenity}.
     */
    public JsonSerializableSerenity(ReadOnlySerenity source) {
        this.groups.addAll(
            source.getGroupList().stream().map(JsonAdaptedGroup::new).collect(Collectors.toList()));
    }

    /**
     * Converts this serenity object into the model's {@code Serenity} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Serenity toModelType() throws IllegalValueException {
        Serenity serenity = new Serenity();
        for (JsonAdaptedGroup jsonAdaptedGroup : this.groups) {
            Group group = jsonAdaptedGroup.toModelType();
            if (serenity.hasGroup(group)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            serenity.addGroup(group);
        }
        return serenity;
    }
}
