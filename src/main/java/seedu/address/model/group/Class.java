package seedu.address.model.group;

public class Class {

    // it's a "box" that will contain attendance list, question list, etc. can refer to Tag.java.

    private String name;

    public Class(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
