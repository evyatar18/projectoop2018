package project.game.objects.collidables;
import java.awt.Color;
import java.util.function.Predicate;

import biuoop.DrawSurface;
import project.game.GameEnvironment;
import project.game.objects.BaseGameObject;
import project.game.objects.Sprite;
import project.game.objects.VelocityControl;
import project.geometry.Line;
import project.geometry.Point;
import project.geometry.Rectangle;
import project.geometry.Vector2D;
import project.geometry.Velocity;
import project.misc.DrawUtils;

/**
 * A class representing a circle.
 */
public class Ball extends BaseGameObject implements Collidable, Sprite {

    private static final Predicate<Collidable> NOT_BALL = new Predicate<Collidable>() {

        @Override
        public boolean test(Collidable t) {
            return !(t instanceof Ball);
        }
    };

    private Point center;
    private final int radius;
    private final Color color;

    private final VelocityControl velocityControl;

    private double differenceTime;

    /**
     * Construct a new circle.
     * @param center : the center point of the circle
     * @param r : the radius of the circle
     * @param color : the color of the circle
     */
    public Ball(Point center, int r, Color color) {
        // initialize given parameters
        this.center = center;
        this.radius = r;
        this.color = color;

        // initialize velocity
        this.velocityControl = new VelocityControl();
    }

    /**
     * Get the x of the center.
     * @return the x value
     */
    public int getX() {
        return this.center.xFloored();
    }

    /**
     * Get the y of the center.
     * @return the y value
     */
    public int getY() {
        return this.center.yFloored();
    }

    /**
     * Get the size of the ball.
     * @return the radius of the ball
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * Get the color of the ball.
     * @return the color of the ball
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Draws this ball on given surface.
     * @param surface : the surface to draw on
     */
    @Override
    public void drawOn(DrawSurface surface) {
        DrawUtils utils = new DrawUtils(surface);

        // draw the ball circle and outline
        utils.fillCircle(this.center, this.radius, this.color);
        utils.drawCircle(this.center, this.radius, Color.BLACK);
    }

    /**
     * Sets the velocity of this ball.
     * @param v : the new velocity
     */
    public void setVelocity(Velocity v) {
        if (v == null) {
            throw new NullPointerException("The velocity cannot be null.");
        }

        this.velocityControl.setVelocity(v);
    }

    /**
     * Sets the velocity of this ball.
     * @param dx : the new velocity in x axis
     * @param dy : the new velocity in y axis
     */
    public void setVelocity(double dx, double dy) {
        setVelocity(new Velocity(dx, dy));
    }

    /**
     * Get the current velocity of this ball.
     * @return the current velocity
     */
    public Velocity getVelocity() {
        return this.velocityControl.getVelocity();
    }

    private GameEnvironment environment;

    @Override
    public void setGameEnvironment(GameEnvironment ge) {
        this.environment = ge;
    }

    /**
     * Get the trajectory for this ball.
     * @return a line instance
     */
    public Line trajectory() {
        return this.velocityControl.calculateTrajectory(this.center, this.differenceTime);
    }

    /**
     * Query whether this ball is inside another collidable.
     * @param c : the other collidable
     * @return true if it is, otherwise false.
     */
    private boolean isInside(Collidable c) {
        return c.getCollisionRectangle().contains(this.center);
    }

    /**
     * Moves this ball 1 step according to its velocity and other collidable objects.
     */
    public void moveOneStep() {
        Line traj = trajectory();

        if (this.environment == null) {
            this.center = traj.end();
        } else {
            // move this ball through the trajectory and check if it collides
            CollisionInfo collision = this.environment.getClosestCollision(traj, NOT_BALL);

            // if there was a collision
            if (collision != null) {
                if (isInside(collision.collisionObject())) {
                    this.center = super.getGame().getFreePoint();
                    return;
                }

                // update velocity
                setVelocity(collision.collisionObject()
                        .hit(this, collision.collisionPoint(), getVelocity(),
                                collision.collisionLine()));

                Line toCollisionPoint = new Line(this.center, collision.collisionPoint());

                // if there is a movement after the intersection
                if (!toCollisionPoint.direction().isZero()) {
                    // move to a slightly bit before the collision point
                    final double fraction = 0.75D - Math.min(0.5D, getVelocity().speed() * this.differenceTime * 0.2D);
                    Line newTrajectory = toCollisionPoint.fractionOfLine(fraction);

                    if (newTrajectory.length() < GameEnvironment.MINIMUM_MOVEMENT) {
                        return;
                    }

                    this.center = newTrajectory.end();
                }

                // if no collision
            } else {
                this.center = traj.end();
            }
        }

        this.initRectangle();
    }

    @Override
    public void timePassed(double dt) {
        // set the current ball velocity
        this.differenceTime = dt;
        moveOneStep();

        // fixing errors
        checkInsideBounds();
        fixVelocity();
    }

    /**
     * Fixes the velocity if the ball starts moving on the x axis.
     */
    private void fixVelocity() {
        if (getVelocity().asVector().onSameLine(Vector2D.X_UNIT)) {
            setVelocity(new Velocity(Vector2D.Y_UNIT.opposite().multiply(getSpeed())));
        }
    }

    /**
     * Get the speed of this ball.
     * @return the speed
     */
    private double getSpeed() {
        return getVelocity().speed();
    }

    /**
     * Check if this ball is inside the game bounds.
     * If it isn't, return it to the game bounds.
     */
    private void checkInsideBounds() {
        if (getGame() != null) {
            if (!getGame().insideBounds(this.center)) {
                this.center = getGame().getFreePoint();
            }
        }
    }

    private Rectangle rect;

    /**
     * Initialize the hit rectangle for this ball.
     */
    private void initRectangle() {
        this.rect = new Rectangle(this.center, this.radius * 2);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        if (this.rect == null) {
            this.initRectangle();
        }

        return this.rect;
    }

    @Override
    public String toString() {
        final String format = "Ball = {loc : %s, r: %s}";
        return String.format(format, this.center, this.radius);
    }

    @Override
    public Velocity hit(Ball b, Point collisionPoint, Velocity currentVelocity) {
        return currentVelocity;
    }

    @Override
    public Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity, Line collisionLine) {
        Vector2D change = this.center.asVector().subtract(collisionLine.middle().asVector());
        Vector2D vel = getVelocity().asVector();

        /* change the velocity of this ball according to the side of collision */
        if (change.getX() != 0) {
            double sign = Math.signum(change.getX());
            vel.multiplyX(sign);

            setVelocity(new Velocity(vel));
        } else if (change.getY() != 0) {
            double sign = Math.signum(change.getY());
            vel.multiplyY(sign);

            setVelocity(new Velocity(vel));
        }

        // move the vector according to the change
        moveOneStep();

        return currentVelocity;
    }
}
