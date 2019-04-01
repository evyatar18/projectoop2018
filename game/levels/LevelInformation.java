package project.game.levels;

import java.util.List;

import project.game.DrawScreen;
import project.game.objects.Sprite;
import project.game.objects.collidables.block.Block;
import project.geometry.Velocity;

/**
 * {@link LevelInformation} specifies the information required to describe a level.
 */
public interface LevelInformation {

    int DEFAULT_BALL_SPEED = 6 * 60;

    int DEFAULT_PADDLE_WIDTH = 120;
    int DEFAULT_PADDLE_SPEED = 10;

    /**
     * The number of balls in this level.
     * @return the number
     */
    int numberOfBalls();

    /**
     * The initial velocities of all the balls.
     * The list should start at the velocity of the leftmost ball all
     *  the way to the rightmost ball.
     * @return a list containing the initial velocities
     */
    List<Velocity> initialBallVelocities();

    /**
     * The speed of the paddle.
     * @return the speed
     */
    int paddleSpeed();

    /**
     * The width of the paddle.
     * @return the width
     */
    int paddleWidth();

    /**
     * The name of this level.
     * @return a string
     */
    String levelName();

    /**
     * Get the background of this level.
     * @return the background
     */
    Sprite getBackground();

    /**
     * A list containing the blocks in this level.
     * @return all the blocks
     */
    List<Block> blocks();

    /**
     * The amount of blocks that have to be destroyed in order to have this level cleared.
     * @return an integer
     */
    int numberOfBlocksToRemove();

    /**
     * Set the boundaries for this level.
     * @param screen : the boundaries
     */
    void setLevelScreen(DrawScreen screen);
}
