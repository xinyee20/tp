package seedu.address.model.group;

public class Participation {

    private final int score;

    public Participation() {
        this.score = 0;
    }

    @Override
    public String toString() {
        return Integer.toString(score);
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object obj) {
        Participation other = (Participation) obj;
        return other.getScore() == getScore();
    }
}
