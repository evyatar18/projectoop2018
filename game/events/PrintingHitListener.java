package project.game.events;

import project.game.objects.collidables.Ball;
import project.game.objects.collidables.block.Block;

/**
 * {@link PrintingHitListener} is a listener that prints when a ball was hit.
 */
public class PrintingHitListener implements HitListener {

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A block with " + beingHit.getHitPoints() + " points was hit.");
    }

}
