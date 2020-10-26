package team.serenity.testutil;

import team.serenity.model.managers.Serenity;


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

    public Serenity build() {
        return serenity;
    }
}
