package team.serenity.model.group.studentinfo;

import static team.serenity.commons.util.AppUtil.checkArgument;

public class Participation {

    public static final String MESSAGE_CONSTRAINTS = "Score must be a number between 0 to 5 inclusive.";
    private final int score;

    public Participation() {
        this.score = 0;
    }

    /**
     * Creates a Participation object containing the score of a student.
     * @param score Score of Student
     */
    public Participation(int score) throws IllegalArgumentException {
        checkArgument(isValidParticipation(score), MESSAGE_CONSTRAINTS);
        this.score = score;
    }

    public static boolean isValidParticipation(int test) {
        return test >= 0 && test <= 5;
    }

    @Override
    public String toString() {
        return Integer.toString(this.score);
    }

    public int getScore() {
        return this.score;
    }

    public Participation setNewScore(int score) {
        Participation updatedScore = new Participation(score);
        return updatedScore;
    }

    @Override
    public boolean equals(Object obj) {
        Participation other = (Participation) obj;
        return other.getScore() == getScore();
    }
}
