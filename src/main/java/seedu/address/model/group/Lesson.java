package seedu.address.model.group;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Set;

/**
 * Represents a tutorial class in serenity. Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Lesson {

    public static final String NAME_CONSTRAINT = "Class name cannot be empty";
    public static final String SCORES_CONSTRAINT = "Scores cannot be empty";
    private final String name;
    private final Set<Score> scores;

    /**
     * Constructs a {@code Class}.
     *
     * @param name A valid name.
     */
    public Lesson(String name, Set<Score> scores) {
        requireAllNonNull(name, scores);
        checkArgument(isValidName(name), NAME_CONSTRAINT);
        checkArgument(isValidScore(scores), SCORES_CONSTRAINT);
        this.name = name;
        this.scores = scores;
    }

    boolean isValidName(String name) {
        return name.length() > 0;
    }

    public Set<Score> getScores() {
        return Collections.unmodifiableSet(scores);
    }

    boolean isValidScore(Set<Score> scores) {
        return scores.size() > 0;
    }


    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Lesson)) {
            return false;
        }

        Lesson otherClass = (Lesson) obj;
        return otherClass.getName().equals(getName()) && otherClass.getScores()
            .containsAll(getScores());
    }
}

