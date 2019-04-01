package project.game.objects.indicators;

import project.game.objects.BaseLabel;
import project.misc.Counter;

/**
 * {@link ScoreIndicator} is a sprite that displays the current score on the screen.
 */
public class ScoreIndicator extends BaseLabel {

    private Counter scoreCounter;

    /**
     * Construct a new {@link ScoreIndicator}.
     * @param scoreCounter : the counter of the score
     */
    public ScoreIndicator(Counter scoreCounter) {
        this.scoreCounter = scoreCounter;
    }

    @Override
    protected String getText() {
        return "Score: " + this.scoreCounter.getValue();
    }
}