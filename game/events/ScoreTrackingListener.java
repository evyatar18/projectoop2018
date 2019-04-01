package project.game.events;

import project.game.objects.collidables.Ball;
import project.game.objects.collidables.block.Block;
import project.misc.Counter;

/**
 * {@link ScoreTrackingListener} is a listener that counts score.
 */
public class ScoreTrackingListener implements HitListener {

    private Counter scoreCounter;

    /**
     * Construct a new {@link ScoreTrackingListener}.
     * @param scoreCounter : a score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.scoreCounter = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.scoreCounter.increase(5);

        // if the block was destroyed
        if (beingHit.getHitPoints() == 0) {
            this.scoreCounter.increase(10);
        }
    }

}
