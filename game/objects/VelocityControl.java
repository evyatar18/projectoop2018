package project.game.objects;

import project.geometry.Line;
import project.geometry.Point;
import project.geometry.Vector2D;
import project.geometry.Velocity;

/**
 * {@link VelocityControl} takes care of managing velocity.
 */
public class VelocityControl {

    private Velocity velocity;

    /**
     * Construct a velocity control for non-moving objects.
     */
    public VelocityControl() {
        this(new Velocity(0, 0));
    }

    /**
     * Construct a new velocity instance.
     * @param initial : the initial velocity per second
     */
    public VelocityControl(Velocity initial) {
        this.velocity = initial;
    }

    /**
     * Set the velocity.
     * @param v : the new velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Get the velocity.
     * @return the current velocity
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Get this velocity as a vector.
     * @return the vector
     */
    public Vector2D direction() {
        return this.velocity.asVector();
    }

    /**
     * Get this velocity applied for a given time difference.
     * @param dt : the time change
     * @return the velocity applied for that time
     */
    public Velocity getVelocityFor(double dt) {
        return new Velocity(this.velocity.asVector().multiply(dt));
    }

    /**
     * Apply this velocity to a point per a given time.
     * @param p : the point to apply to
     * @param dt : the change in time to apply
     * @return a destination point
     */
    public Point applyToPoint(Point p, double dt) {
        return getVelocityFor(dt).applyToPoint(p);
    }

    /**
     * Get the trajectory from a given point per a given time.
     * @param from : the point to start from
     * @param dt : the change in time
     * @return a trajectory line
     */
    public Line calculateTrajectory(Point from, double dt) {
        Point end = applyToPoint(from, dt);
        return new Line(from, end);
    }
}
