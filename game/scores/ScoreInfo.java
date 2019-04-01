package project.game.scores;

import java.io.Serializable;

/**
 * {@link ScoreInfo} is a class that holds score info.
 */
public class ScoreInfo implements Serializable, Comparable<ScoreInfo> {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final int score;

    private final long timeOfAddition;

    /**
     * Construct a new score info.
     * @param name : the name of the player
     * @param score : the score of the player
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;

        // the date in which this score was added
        this.timeOfAddition = System.currentTimeMillis();
    }

    /**
     * Construct a new {@link ScoreInfo} instance using only score.
     * @param score : the score
     */
    public ScoreInfo(int score) {
        this("", score);
    }

    /**
     * Get the name of the scorer.
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the score of the scorer.
     * @return the score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Get the time this score was registered.
     * @return the timestamp
     */
    private long timeOfAddition() {
        return this.timeOfAddition;
    }

    @Override
    public int compareTo(ScoreInfo o) {
        int cmp = Integer.compare(getScore(), o.getScore());

        // if both scores are equal
        if (cmp == 0) {
            // do time comparison
            cmp = -Long.compareUnsigned(timeOfAddition(), o.timeOfAddition());
        }

        return cmp;
    }

    private static final String FORMAT = "ScoreInfo = { name: %s, score: %s }";

    @Override
    public String toString() {
        return String.format(FORMAT, getName(), getScore());
    }
}
