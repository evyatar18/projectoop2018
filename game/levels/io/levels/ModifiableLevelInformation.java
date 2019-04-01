package project.game.levels.io.levels;

import java.util.List;

import project.game.DrawScreen;
import project.game.levels.LevelBlockGenerator;
import project.game.levels.LevelInformation;
import project.game.objects.Sprite;
import project.game.objects.collidables.block.Block;
import project.geometry.Velocity;

/**
 * {@link ModifiableLevelInformation} is a {@link LevelInformation} object that can be modified.
 */
public class ModifiableLevelInformation implements LevelInformation {

    private final LevelBlockGenerator blockGenerator = new LevelBlockGenerator();

    private int numberOfBalls;
    private List<Velocity> initialBallVelocities;
    private int paddleSpeed;
    private int paddleWidth;
    private String levelName;
    private Sprite background;
    private int blocksToRemove;

    @Override
    public int numberOfBalls() {
        return this.numberOfBalls;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return this.initialBallVelocities;
    }

    /**
     * Set initial block velocities.
     * @param list : the list of ball velocities
     */
    public void setInitialBallVelocities(List<Velocity> list) {
        this.initialBallVelocities = list;
        this.numberOfBalls = list.size();
    }

    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * Set the speed of the paddle.
     * @param speed : the new speed
     */
    public void setPaddleSpeed(int speed) {
        this.paddleSpeed = speed;
    }

    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * Set the width of the paddle.
     * @param width : the new width
     */
    public void setPaddleWidth(int width) {
        this.paddleWidth = width;
    }

    @Override
    public String levelName() {
        return this.levelName;
    }

    /**
     * Set the name of this level.
     * @param name : the name
     */
    public void setLevelName(String name) {
        this.levelName = name;
    }

    @Override
    public Sprite getBackground() {
        return this.background;
    }

    /**
     * Set the background.
     * @param bg : the background
     */
    public void setBackground(Sprite bg) {
        this.background = bg;
    }

    @Override
    public List<Block> blocks() {
        return this.blockGenerator.generate();
    }

    /**
     * Get the current block generator for this level.
     * @return the block generator
     */
    public LevelBlockGenerator getBlockGenerator() {
        return this.blockGenerator;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocksToRemove;
    }

    /**
     * Set the amount of the blocks to remove.
     * @param amount : the amount
     */
    public void setNumberOfBlocksToRemove(int amount) {
        this.blocksToRemove = amount;
    }

    @Override
    public void setLevelScreen(DrawScreen screen) {

    }

}
