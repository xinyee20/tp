package team.serenity.model.group.studentinfo;

public class Participation {

    public static final String SCORE_ERROR = "Score must be a number";
    private final int score;

    public Participation() {
        this.score = 0;
    }

    /**
     * Creates a Participation object containing the score of a student.
     * @param score Score of Student
     */
    public Participation(int score) throws IllegalArgumentException {
        if (score >= 0 && score <= 5) {
            this.score = score;
        } else {
            throw new IllegalArgumentException("Score must be within the range of 0 to 5");
        }
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
