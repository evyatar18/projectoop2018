package project.game.objects.indicators;

import project.game.objects.BaseLabel;
import project.misc.Counter;

/**
 * {@link LivesIndicator} is an indicator of lives.
 */
public class LivesIndicator extends BaseLabel {

    private Counter lives;

    /**
     * Construct a new {@link LivesIndicator}.
     * @param lives : the amount of lives
     */
    public LivesIndicator(Counter lives) {
        this.lives = lives;
    }

    @Override
    protected String getText() {
        return "Lives: " + this.lives.getValue();
    }

}
