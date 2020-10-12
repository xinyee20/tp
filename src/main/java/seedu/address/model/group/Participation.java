package seedu.address.model.group;

public class Participation {

    public static final String SCORE_ERROR = "Score must be a number";
    private final int score;

    public Participation() {
        this.score = 0;
    }

    /**
     * Creates a Participation object containing the score of a student
     * @param score Score of Student
     */
    public Participation(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return Integer.toString(score);
    }

    public int getScore() {
        return score;
    }

    public Participation setScore(int score) {
        Participation updatedScore = new Participation(score);
        return updatedScore;
    }

    @Override
    public boolean equals(Object obj) {
        Participation other = (Participation) obj;
        return other.getScore() == getScore();
    }
}
