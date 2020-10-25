package team.serenity.testutil;

import team.serenity.model.managers.Serenity;
import team.serenity.model.group.Group;

/**
 * A utility class to help with building Serenity objects. Example usage: <br> {@code Serenity serenity = new
 * SerenityBuilder().withGroup("John", "Doe").build();}
 */
public class SerenityBuilder {

    private Serenity serenity;

    public SerenityBuilder() {
        serenity = new Serenity();
    }

    public SerenityBuilder(Serenity serenity) {
        this.serenity = serenity;
    }

    /**
     * Adds a new {@code Group} to the {@code Serenity} that we are building.
     */
    public SerenityBuilder withGroup(Group group) {
        serenity.addGroup(group);
        return this;
    }

    public Serenity build() {
        return serenity;
    }
}
