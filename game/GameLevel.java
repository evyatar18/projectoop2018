package project.game;
import java.awt.Color;
import java.util.List;
import java.util.Random;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import project.game.animation.Animation;
import project.game.animation.AnimationRunner;
import project.game.animation.CountdownAnimation;
import project.game.animation.KeyPressStoppableAnimation;
import project.game.animation.PauseScreen;
import project.game.events.BallRemover;
import project.game.events.BlockRemover;
import project.game.events.HitListener;
import project.game.events.ScoreTrackingListener;
import project.game.levels.LevelInformation;
import project.game.objects.Sprite;
import project.game.objects.TopBar;
import project.game.objects.collidables.Ball;
import project.game.objects.collidables.Collidable;
import project.game.objects.collidables.Paddle;
import project.game.objects.collidables.block.Block;
import project.game.objects.collidables.block.BoundingBlock;
import project.game.objects.indicators.LevelNameIndicator;
import project.game.objects.indicators.LivesIndicator;
import project.game.objects.indicators.ScoreIndicator;
import project.geometry.EqualSegmentDivision;
import project.geometry.Point;
import project.geometry.Segment;
import project.misc.Counter;

/**
 * A game instance.
 */
public class GameLevel implements Animation {

    public static final int TOPBAR_HEIGHT = 30;
    public static final int BOUNDING_BLOCK_WIDTH = 25;

    private final SpriteCollection sprites;
    private final GameEnvironment environment;

    private final KeyboardSensor keyboard;
    private final AnimationRunner animationRunner;

    private final LevelInformation information;
    private final DrawScreen drawScreen;

    // level-specific counters
    private final Counter remainingBlocksForRemoval = new Counter();
    private final Counter remainingBalls = new Counter();

    // global counters
    private final Counter remainingLives;
    private final Counter scoreCounter;

    /**
     * Construct a new game level.
     * @param information : the level information
     * @param keyboard : a keyboard sensor
     * @param animationRunner : an animation runner
     * @param livesCounter : a counter of the lives
     * @param scoreCounter : a counter of the score
     */
    public GameLevel(LevelInformation information, KeyboardSensor keyboard, AnimationRunner animationRunner,
            Counter livesCounter, Counter scoreCounter) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();

        // set bounds for this level
        this.drawScreen = new GameLevelScreen();

        this.information = information;
        // add screen boundaries for this level
        this.information.setLevelScreen(this.drawScreen);

        // init keyboard and animation runner
        this.keyboard = keyboard;
        this.animationRunner = animationRunner;

        this.remainingLives = livesCounter;
        this.scoreCounter = scoreCounter;
    }

    /**
     * Add a collidable to this game.
     * @param collidable : the collidable
     */
    public void addCollidable(Collidable collidable) {
        this.environment.addCollidable(collidable);
    }

    /**
     * Remove a collidable from this game.
     * @param collidable : the collidable to remove
     */
    public void removeCollidable(Collidable collidable) {
        this.environment.removeCollidable(collidable);
    }

    /**
     * Add a sprite to this game.
     * @param sprite : the sprite to add
     */
    public void addSprite(Sprite sprite) {
        this.sprites.addSprite(sprite);
    }

    /**
     * Remove a sprite from this game.
     * @param sprite : the sprite to be removed
     */
    public void removeSprite(Sprite sprite) {
        this.sprites.removeSprite(sprite);
    }

    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int TOP = 2;
    private static final int BOTTOM = 3;

    /**
     * Initialize the bounding blocks of this game.
     * @param color : the color of the blocks
     */
    private void initBoundingBlocks(Color color) {
        BoundingBlock[] blocks = new BoundingBlock[4];

        int blockWidth = BOUNDING_BLOCK_WIDTH;

        // init bounding blocks
        blocks[LEFT] = new BoundingBlock(new Point(0, 30), blockWidth, height(), color);
        blocks[RIGHT] = new BoundingBlock(new Point(width() - blockWidth, 30), blockWidth, height(), color);
        blocks[TOP] = new BoundingBlock(new Point(0, 30), width(), blockWidth, color);
        blocks[BOTTOM] = new BoundingBlock(new Point(0, height()), width(), 0, color);

        // add all the bounding blocks to this game
        for (BoundingBlock block : blocks) {
            block.addToGame(this);
        }

        // make the bottom boundary remove balls
        HitListener ballRemover = new BallRemover(this, this.remainingBalls);
        blocks[BOTTOM].addHitListener(ballRemover);
    }

    /**
     * Get a point within the game regions that is not surrounded by collidables.
     * @return a free point
     */
    public Point getFreePoint() {
        Point p;

        // initialize random
        Random random = new Random();

        do {
            p = new Point(random.nextDouble() * width(), random.nextDouble() * height());
        } while (this.environment.isSurrounded(p));

        return p;
    }


    /**
     * Initialize blocks for this game level.
     */
    private void initLevelBlocks() {
        // init a new block remover
        HitListener blockRemover = new BlockRemover(this, this.remainingBlocksForRemoval);

        // init a new score tracker
        HitListener scoreTracker = new ScoreTrackingListener(this.scoreCounter);

        // init remaining blocks for removal
        this.remainingBlocksForRemoval.reset();
        this.remainingBlocksForRemoval.increase(this.information.numberOfBlocksToRemove());

        // get blocks from information
        List<Block> blocks = this.information.blocks();

        // iterate over the blocks to add them all
        for (Block block : blocks) {
            block.addToGame(this);

            // add block remover and score tracker
            block.addHitListener(blockRemover);
            block.addHitListener(scoreTracker);
        }
    }

    private TopBar topBar;

    /**
     * Initialize the top bar of this game.
     * @param color : the background color of the top
     */
    private void initTop(Color color) {
        // init top bar
        TopBar top = new TopBar(color, TOPBAR_HEIGHT);
        top.addToGame(this);
        this.topBar = top;

        // init score indicator
        ScoreIndicator score = new ScoreIndicator(this.scoreCounter);
        score.addToGame(this);

        // init lives indicator
        LivesIndicator lives = new LivesIndicator(this.remainingLives);
        lives.addToGame(this);

        // init level name indicator
        LevelNameIndicator levelName = new LevelNameIndicator(this.information.levelName());
        levelName.addToGame(this);
    }

    /**
     * Initialize this game level.
     */
    public void initialize() {
        this.addSprite(this.information.getBackground());

        // init the level blocks
        initLevelBlocks();

        // init the bounding blocks
        initBoundingBlocks(Color.DARK_GRAY);

        // init the top bar
        initTop(Color.GRAY);
    }

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    /**
     * Get the width of this game screen.
     * @return the width
     */
    public int width() {
        return WIDTH;
    }

    /**
     * Get the height of this game screen.
     * @return the height
     */
    public int height() {
        return HEIGHT;
    }

    /**
     * Initialize the paddle for this level.
     * @param color : the color of the paddle
     * @return the paddle
     */
    private Paddle initPaddle(Color color) {
        Paddle paddle = new Paddle(this.keyboard, color);

        // set speed and width
        paddle.setWidth(this.information.paddleWidth());
        paddle.setSpeed(this.information.paddleSpeed());

        /* decide what the position of the paddle will be */
        int spaceFromBottom = 20;

        Segment xseg = Segment.fromMiddleAndWidth(width() / 2, paddle.getWidth());
        double x = xseg.getLeft();
        double y = this.height() - paddle.getHeight() - spaceFromBottom;

        paddle.setPosition(new Point(x, y));

        // add to this game
        paddle.addToGame(this);

        return paddle;
    }

    /**
     * Initialize the balls.
     * @param middle : the middle point of all the balls
     * @param color : the color of the balls
     * @return a balls array
     */
    private Ball[] initBalls(Point middle, Color color) {
        int ballsAmount = this.information.numberOfBalls();

        Ball[] balls = new Ball[ballsAmount];

        final int ballRadius = 5;
        final double spaceBetweenBalls = 20;

        EqualSegmentDivision xPositionDivisor = EqualSegmentDivision
                .fromCenterSpacingAndAmount(middle.getX(), spaceBetweenBalls * 0, ballsAmount);

        for (int i = 0; i < balls.length; i++) {
            // get x and y for this ball
            double x = xPositionDivisor.middlePosition(i);
            double y = middle.getY();

            // construct center
            Point ballCenter = new Point(x, y);

            // construct ball
            Ball ball = new Ball(ballCenter, ballRadius, color);

            // add to this level
            ball.addToGame(this);

            // set the initial velocity
            ball.setVelocity(this.information.initialBallVelocities().get(i));
        }

        return balls;
    }

    /**
     * Init the objects for this turn.
     * @return the paddle of this turn
     */
    private Paddle initTurnObjects() {
        // create a paddle
        Paddle paddle = initPaddle(Color.YELLOW);

        int height = 20;
        Point middleOfBalls = paddle.topMiddle().asVector().add(0, -height).asPoint();

        // init positions for balls
        Ball[] balls = initBalls(middleOfBalls, Color.WHITE);

        // init counter for remaining balls
        this.remainingBalls.reset();
        this.remainingBalls.increase(balls.length);

        return paddle;
    }

    /**
     * Play one turn(one life).
     */
    public void playOneTurn() {
        Paddle paddle = initTurnObjects();

        // do countdown for 2 seconds starting from 3
        this.animationRunner.run(new CountdownAnimation(2, 3, this.sprites));

        // flag this as running
        this.running = true;

        // run this animation
        this.animationRunner.run(this);

        // remove the current paddle from the screen after this turn is done
        paddle.removeFromGame(this);
    }

    /**
     * Get the top bar of this level.
     * @return the top bar
     */
    public TopBar getTopBar() {
        return this.topBar;
    }

    private boolean running;

    private static final String PAUSE_BUTTON = "p";

    @Override
    public void doOneFrame(DrawSurface surface, double dt) {
        // check for pause screen request
        if (this.keyboard.isPressed(PAUSE_BUTTON)) {
            this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboard,
                    KeyboardSensor.SPACE_KEY, new PauseScreen()));
        }

        // draw and notify time pass
        this.sprites.setTimePassed(dt);
        this.sprites.drawAllOn(surface);
        this.sprites.notifyAllTimePassed();

        // if this turn has been finished
        if (!moreBlocksLeft() || this.remainingBalls.getValue() == 0) {
            // flag this as not running
            this.running = false;

            // if all balls failed
            if (this.remainingBalls.getValue() == 0) {
                // take off one live
                this.remainingLives.decrease(1);
            }
        }
    }

    /**
     * Query whether there are more blocks for removal left.
     * @return true if there are. false otherwise.
     */
    public boolean moreBlocksLeft() {
        return this.remainingBlocksForRemoval.getValue() > 0;
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * Query whether a point is within the bounds of this level.
     * @param point : the point to check
     * @return true if it is. false otherwise.
     */
    public boolean insideBounds(Point point) {
        return this.drawScreen.inside(point);
    }

}
