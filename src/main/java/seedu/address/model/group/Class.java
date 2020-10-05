package seedu.address.model.group;

/**
 * Represents a tutorial class in serenity.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Class {

    private String name;

    /**
     * Constructs a {@code Class}.
     *
     * @param name A valid name.
     */
    public Class(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
