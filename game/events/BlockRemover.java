package project.game.events;

import project.game.GameLevel;
import project.game.objects.collidables.Ball;
import project.game.objects.collidables.block.Block;
import project.misc.Counter;

/**
 * {@link BlockRemover} is a listener that removes blocks.
 */
public class BlockRemover implements HitListener {

    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * Construct a new BlockRemover.
     * @param game : the game this remover is attached to
     * @param remainingBlocks : the amount of remaining blocks in the game
     */
    public BlockRemover(GameLevel game, Counter remainingBlocks) {
        this.remainingBlocks = remainingBlocks;
        this.game = game;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // if this block is done
        if (beingHit.getHitPoints() <= 0) {
            // decrease the amount of remaining blocks
            this.remainingBlocks.decrease(1);

            // remove this hit listener from the block
            beingHit.removeHitListener(this);

            // remove the block from the game
            beingHit.removeFromGame(this.game);
        }
    }

}
