package project.game.events;

import project.game.GameLevel;
import project.game.objects.collidables.Ball;
import project.game.objects.collidables.block.Block;
import project.misc.Counter;

/**
 * {@link BallRemover} is a {@link HitListener} that removes balls.
 */
public class BallRemover implements HitListener {

    private GameLevel game;
    private Counter counter;

    /**
     * Construct a {@link BallRemover}.
     * @param game : the gamelevel
     * @param remainingBalls : the remaining balls
     */
    public BallRemover(GameLevel game, Counter remainingBalls) {
        this.game = game;
        this.counter = remainingBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.counter.decrease(1);
    }

}
