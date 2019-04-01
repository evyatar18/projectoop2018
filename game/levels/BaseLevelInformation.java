package project.game.levels;

import java.util.ArrayList;
import java.util.List;

import project.game.DrawScreen;
import project.game.objects.Sprite;
import project.game.objects.collidables.block.Block;
import project.geometry.EqualSegmentDivision;
import project.geometry.Segment;
import project.geometry.Velocity;

/**
 * {@link BaseLevelInformation} is a class that assists level information objects manage their information.
 */
public abstract class BaseLevelInformation implements LevelInformation {

    private final BlocksGeneratorFactory blocksGeneratorFactory;
    private final LevelBackgroundFactory backgroundFactory;

    /**
     * Construct a new {@link BaseLevelInformation}.
     * @param blocksGeneratorFactory : a factory that creates block generators
     * @param backgroundFactory : a factory that creates background
     */
    public BaseLevelInformation(BlocksGeneratorFactory blocksGeneratorFactory,
            LevelBackgroundFactory backgroundFactory) {
        this.blocksGeneratorFactory = blocksGeneratorFactory;
        this.backgroundFactory = backgroundFactory;
    }

    /**
     * Get the segment all the velocities' angles are picked from.
     * @return a segment of degrees
     */
    protected abstract Segment getVelocitiesDirectionsSegment();

    @Override
    public List<Velocity> initialBallVelocities() {
        int numberOfBalls = numberOfBalls();

        // make equal angle differences between the balls
        EqualSegmentDivision division =
                new EqualSegmentDivision(getVelocitiesDirectionsSegment(), numberOfBalls);

        // initialize velocities list
        List<Velocity> velocities = new ArrayList<>(numberOfBalls);

        /*
         * calculate the index of the middle ball - if exists
         * if doesn't exist pseudo-middle will be calculated
         */
        double middle = ((double) (numberOfBalls - 1) / 2);

        for (int i = 0; i < numberOfBalls; i++) {
            double angle;

            // if the current ball is to the left of the middle
            if (i < middle) {
                angle = division.leftmostPosition(i);
            // if the current ball is the middle
            } else if (i == middle) {
                angle = division.middlePosition(i);
            // if the current ball is to the right of the middle
            } else {
                angle = division.rightmostPosition(i);
            }

            // get the speed of each ball
            double speed = DEFAULT_BALL_SPEED;

            // add the velocity
            velocities.add(Velocity.fromAngleAndSpeed(angle, speed));
        }

        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return DEFAULT_PADDLE_SPEED;
    }

    @Override
    public int paddleWidth() {
        return DEFAULT_PADDLE_WIDTH;
    }

    @Override
    public Sprite getBackground() {
        return this.backgroundFactory.make(this.drawScreen);
    }

    @Override
    public List<Block> blocks() {
        return this.blocksGenerator.generate();
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocksGenerator.amount();
    }

    private DrawScreen drawScreen;
    private BlocksGenerator blocksGenerator;

    @Override
    public void setLevelScreen(DrawScreen screen) {
        this.drawScreen = screen;
        this.blocksGenerator = this.blocksGeneratorFactory.make(screen);
    }

    /**
     * Get the draw screen for this level.
     * @return the {@link DrawScreen} this level will be drawn upon
     */
    protected DrawScreen getDrawScreen() {
        return this.drawScreen;
    }
}
