package project.geometry;
/**
 * A class representing a 2D velocity.
 */
public class Velocity {

    private double vx;
    private double vy;

    /**
     * Construct a new velocity.
     * @param dx : velocity in x axis
     * @param dy : velocity in y axis
     */
    public Velocity(double dx, double dy) {
        this.vx = dx;
        this.vy = dy;
    }

    /**
     * Construct a new velocity from a vector.
     * @param v : the given vector
     */
    public Velocity(Vector2D v) {
        this(v.getX(), v.getY());
    }

    /**
     * Construct a velocity using a point on the surface.
     * @param p : the point
     */
    public Velocity(Point p) {
        this(p.getX(), p.getY());
    }

    /**
     * Get next position for a point at this velocity.
     * @param p : the point
     * @return a new point which is the next position
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.vx, p.getY() + this.vy);
    }

    /**
     * Get the velocity on the x-axis.
     * @return the velocity
     */
    public double getVx() {
        return this.vx;
    }

    /**
     * Set the velocity on the x-axis.
     * @param dx : the new velocity
     */
    public void setVx(double dx) {
        this.vx = dx;
    }

    /**
     * Get the velocity on the y-axis.
     * @return the velocity
     */
    public double getVy() {
        return this.vy;
    }

    /**
     * Set the velocity on the y-axis.
     * @param dy : the new velocity
     */
    public void setVy(double dy) {
        this.vy = dy;
    }

    /**
     * Get a {@link Vector2D} instance of this velocity.
     * @return a Vector2D instance.
     */
    public Vector2D asVector() {
        return new Vector2D(this.vx, this.vy);
    }

    /**
     * Construct a velocity from angle and speed.
     * @param angle : the angle relative to the y-axis
     * @param speed : the speed
     * @return a new velocity instance with corresponding angle and speed.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        return new Velocity(Point.fromDistanceAndAngle(speed, angle));
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", this.vx, this.vy);
    }

    /**
     * Get the speed of this velocity.
     * @return the speed
     */
    public double speed() {
        return asVector().length();
    }
}
