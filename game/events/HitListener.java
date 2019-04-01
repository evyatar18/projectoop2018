package project.game.events;

import project.game.objects.collidables.Ball;
import project.game.objects.collidables.block.Block;

/**
 * {@link HitListener} is a listener for block hits.
 */
public interface HitListener {

    /**
     * Notify this object that a hit has occurred.
     * @param beingHit : the block that was hit
     * @param hitter : the ball that hit it
     */
    void hitEvent(Block beingHit, Ball hitter);
}
