package project.game.objects.collidables;

import java.awt.Color;
import java.util.function.Predicate;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import project.game.GameEnvironment;
import project.game.objects.BaseGameObject;
import project.game.objects.Sprite;
import project.geometry.EqualSegmentDivision;
import project.geometry.Line;
import project.geometry.Point;
import project.geometry.Rectangle;
import project.geometry.Segment;
import project.geometry.Vector2D;
import project.geometry.Velocity;
import project.misc.DrawUtils;
import project.misc.NotThis;

/**
 * A paddle in the game.
 */
public class Paddle extends BaseGameObject implements Collidable, Sprite {

    public static final int HEIGHT = 20;

    private final KeyboardSensor keyboard;

    private int width;
    private int height;

    private int movementSpeed;
    private Vector2D velocity;
    private Vector2D topLeft;

    private Color color;

    private double differenceTime;

    /**
     * Construct a new Paddle controlled by a given KeyboardSensor.
     * @param keySensor : the given keyboard sensor
     * @param color : the color of the paddle
     */
    public Paddle(KeyboardSensor keySensor, Color color) {
        this.keyboard = keySensor;
        this.color = color;

        this.height = HEIGHT;

        this.resetVelocity();
    }

    /**
     * Set the position of this paddle.
     * @param pos : the top left position of the paddle
     */
    public void setPosition(Point pos) {
        this.topLeft = pos.asVector();
    }

    /**
     * Reset the velocity.
     */
    private void resetVelocity() {
        this.velocity = new Vector2D(0, 0);
    }

    /**
     * Move the paddle to the right.
     */
    public void moveRight() {
        this.velocity = this.velocity.add(rightVelocity());
    }

    /**
     * Get the right velocity.
     * @return the right velocity
     */
    private Vector2D rightVelocity() {
        return new Vector2D(this.movementSpeed * this.differenceTime, 0);
    }

    /**
     * Move the paddle to the left.
     */
    public void moveLeft() {
        this.velocity = this.velocity.add(leftVelocity());
    }

    /**
     * Get the left velocity.
     * @return the left velocity
     */
    private Vector2D leftVelocity() {
        return new Vector2D(this.movementSpeed * this.differenceTime * -1, 0);
    }

    /**
     * Get the top middle point.
     * @return a Point instance
     */
    public Point topMiddle() {
        return this.topLeft.add(new Vector2D(getWidth() / 2, 0)).asPoint();
    }

    @Override
    public void drawOn(DrawSurface surface) {
        DrawUtils draw = new DrawUtils(surface);
        draw.fillRectangle(this.topLeft.asPoint(), getWidth(), HEIGHT, this.color);
        draw.drawRectangle(this.topLeft.asPoint(), getWidth(), HEIGHT, Color.BLACK);
    }

    /**
     * Do movement if there was a keypress.
     * @param dt : the difference in time since last call
     * @return true if there was a movement. false otherwise.
     */
    private boolean doMovement(double dt) {
        // set the dt
        this.differenceTime = dt;

        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }

        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }

        return !this.velocity.isZero();
    }

    private final Predicate<Collidable> notThis = new NotThis<Collidable>(this);

    /**
     * Check if there's no collision.
     * @param trajectory : the trajectory of this paddle
     */
    private void checkNoCollision(Line trajectory) {
        int sensorsPairs = 5;
        double distance = getHeight() / ((double) 2 * sensorsPairs);

        // get any collision
        CollisionInfo collision = this.gameEnvironment
                .getClosestCollision(trajectory, this.notThis, sensorsPairs, distance);

        // move only if there's no collision
        if (collision == null) {
            this.topLeft = getTrajectory().end().asVector();
        }
    }

    /**
     * Get the middle-left point of this paddle.
     * @return the middle left point
     */
    private Point middleLeft() {
        return this.topLeft.add(0, this.height / 2).asPoint();
    }

    /**
     * Get the middle-right point of this paddle.
     * @return the middle right point
     */
    private Point middleRight() {
        return this.topLeft.add(this.width, this.height / 2).asPoint();
    }

    /**
     * Query whether this paddle moves to the right.
     * @return true if it is, false otherwise.
     */
    private boolean movingRight() {
        return this.velocity.sameDirection(Vector2D.X_UNIT);
    }

    /**
     * Query whether this paddle moves to the left.
     * @return true if it is. false otherwise.
     */
    private boolean movingLeft() {
        return this.velocity.sameDirection(Vector2D.X_UNIT.opposite());
    }

    /**
     * Get the trajectory of this paddle.
     * @return the trajectory starting from the top left position.
     */
    private Line getTrajectory() {
        Point target = this.topLeft.add(this.velocity).asPoint();
        return new Line(this.topLeft.asPoint(), target);
    }

    /**
     * Get the trajectory of this line, which has a start point positioned in the middle
     *  of the its edge its moving towards.
     * @return the trajectory line
     */
    private Line getMiddleTrajectory() {
        Point startPoint;
        if (movingRight()) {
            startPoint = middleRight();
        } else if (movingLeft()) {
            startPoint = middleLeft();
        } else {
            return null;
        }

        return new Line(startPoint, this.velocity);
    }

    @Override
    public void timePassed(double dt) {
        // do movement
        if (doMovement(dt)) {
            /* if moved */

            // check collision
            Line traj = getMiddleTrajectory();
            checkNoCollision(traj);

            // reset the velocity
            resetVelocity();
        }
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return new Rectangle(this.topLeft.asPoint(), getWidth(), getHeight());
    }

    private static final int REGIONS = 5;
    private static final double ANGLE_DIFFERENCE = 30;

    /**
     * Get the region in the rectangle for a given point.
     * @param p : the point
     * @return the region of the point. a number from 0 to 5
     */
    private int getRegion(Point p) {
        // divide top segment equally
        Segment topSegment = Segment.fromStartAndWidth(this.topLeft.getX(), getWidth());
        EqualSegmentDivision partsOfPaddle = new EqualSegmentDivision(topSegment, REGIONS);

        // get index on the line division
        int index = partsOfPaddle.getIndexForPosition(p.getX());

        // do validity checks
        if (index < 0) {
            return 0;
        } else if (index >= REGIONS) {
            return REGIONS - 1;
        } else {
            return index;
        }
    }

    /**
     * Get a corresponding angle for a region index.
     * @param region : the given region index
     * @return the corresponding angle
     */
    private double angleForRegion(int region) {
        // start from angle 0, divide to the amount of regions with differences of 'angle_difference'
        EqualSegmentDivision angleDivision = EqualSegmentDivision
                .fromCenterSpacingAndAmount(0, ANGLE_DIFFERENCE, REGIONS);

        return angleDivision.middlePosition(region);
    }

    @Override
    public Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity) {
        throw new RuntimeException(ERR_NO_COLLISION_LINE);
    }

    @Override
    public Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity, Line collisionLine) {
        // if hit from the top rectangle, do the special collision
        if (collisionLine.equals(getCollisionRectangle().getLines()[Rectangle.TOP])) {
            int region = getRegion(collisionPoint);
            double angle = angleForRegion(region);

            return Velocity.fromAngleAndSpeed(angle, currentVelocity.speed());
        }

        Vector2D v = currentVelocity.asVector();

        if (getCollisionRectangle().isVertex(collisionPoint)) {
            return new Velocity(v.opposite());
        }

        // if hit from the side, do normal collision
        if (collisionLine.direction().onSameLine(Vector2D.Y_UNIT)) {
            v.multiplyX(-1);
            return new Velocity(v);

            // if hit from the bottom, send backwards
        } else {
            v.multiplyY(-1);
            return new Velocity(v);
        }
    }

    private GameEnvironment gameEnvironment;

    @Override
    public void setGameEnvironment(GameEnvironment environment) {
        this.gameEnvironment = environment;
    }

    /**
     * Set the width of this paddle.
     * @param w : the width
     */
    public void setWidth(int w) {
        this.width = w;
    }

    /**
     * Get the width of this paddle.
     * @return the width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Get the height of this paddle.
     * @return the height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Set the speed of this paddle.
     * @param speed : the new speed
     */
    public void setSpeed(int speed) {
        this.movementSpeed = speed;
    }
}
