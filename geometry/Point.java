package project.geometry;
/**
 * A class used to represent a 2D point.
 */
public class Point {

    // x and y of the point
    private double x, y;

    /**
     * Construct a new point.
     * @param x : the x value for the point
     * @param y : the y value for the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x value of the point.
     * @return the x
     */
    public double getX() {
        return this.x;
    }

    /**
     * Get the y value of the point.
     * @return the y
     */
    public double getY() {
        return this.y;
    }

    /**
     * Get the floored x value.
     * @return the x casted to int.
     */
    public int xFloored() {
        return (int) this.x;
    }

    /**
     * Get the floored y value.
     * @return the y casted to int.
     */
    public int yFloored() {
        return (int) this.y;
    }

    /**
     * Get the distance of this point to other point.
     * @param other : the other point.
     * @return the distance between the points.
     */
    public double distance(Point other) {
        if (other == null) {
            throw new NullPointerException("other can't be null.");
        }

        // calculate x, y differences
        double dx = this.x - other.x;
        double dy = this.y - other.y;

        // calculate the distance squared.
        double distanceSquared = dx * dx + dy * dy;

        // undo the square to get actual distance
        return Math.sqrt(distanceSquared);
    }

    /**
     * Checks whether this point is equal to other point.
     * @param other : the other point
     * @return true if the points are equal. false otherwise.
     */
    public boolean equals(Point other) {
        if (other == null) {
            return false;
        }

        // check if x, y values are the same
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", this.x, this.y);
    }

    /**
     * Get this point as a Vector.
     * @return a Vector2D instance
     */
    public Vector2D asVector() {
        return new Vector2D(this.x, this.y);
    }

    /**
     * Create a point using a distance from the origin and the angle.
     * @param distance : the distance from the origin
     * @param angle : the angle(in degrees)
     * @return a point at the given coordinates
     */
    public static Point fromDistanceAndAngle(double distance, double angle) {
        // convert to radians
        double rads = Math.toRadians(angle);

        // move into angle relative to the given coordinate system.
        rads -= Math.PI / 2;

        // get the velocity in each axis
        double x = distance * Math.cos(rads);
        double y = distance * Math.sin(rads);

        return new Point(x, y);
    }

}