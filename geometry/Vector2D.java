package project.geometry;
/**
 * A 2D-Vector representation.
 */
public class Vector2D {

    public static final Vector2D X_UNIT = new Vector2D(1, 0);
    public static final Vector2D Y_UNIT = new Vector2D(0, 1);

    private double x;
    private double y;

    /**
     * Create a vector at a given location from the origin.
     * @param x : the x of the vector
     * @param y : the y of the vector
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a vector connecting two points.
     * @param start : the starting point
     * @param end : the end point
     */
    public Vector2D(Point start, Point end) {
        this(end.getX() - start.getX(), end.getY() - start.getY());
    }

    /**
     * Get the x coordinate of the vector.
     * @return the x
     */
    public double getX() {
        return this.x;
    }

    /**
     * Get the y coordinate of the vector.
     * @return the y
     */
    public double getY() {
        return this.y;
    }

    /**
     * Multiply only the x of this vector.
     * @param scalar : the scalar to multiply with
     */
    public void multiplyX(double scalar) {
        this.setX(this.x * scalar);
    }

    /**
     * Multiply only the y of this vector.
     * @param scalar : the scalar to multiply with
     */
    public void multiplyY(double scalar) {
        this.setY(this.y * scalar);
    }

    /**
     * Multiply this vector by a scalar and get a new vector which is the result.
     * @param scalar : the scalar
     * @return the multiplication result.
     */
    public Vector2D multiply(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    /**
     * Add to this vector another vector and get a new vector which is the result.
     * @param v : the vector to add
     * @return the addition result.
     */
    public Vector2D add(Vector2D v) {
        return new Vector2D(this.x + v.x, this.y + v.y);
    }

    /**
     * Add to this vector another coordinates to get a new vector.
     * @param xVal : the x value
     * @param yVal : the y value
     * @return a new vector which is the addition result
     */
    public Vector2D add(double xVal, double yVal) {
        return this.add(new Vector2D(xVal, yVal));
    }

    /**
     * Subtract from this vector another vector.
     * @param v : the vector to subtract
     * @return a new vector which is the subtraction result.
     */
    public Vector2D subtract(Vector2D v) {
        return this.add(v.multiply(-1));
    }

    /**
     * Get the angle with the upper positive part of the x axis.
     * @return the angle in degrees
     */
    public double angle() {
        double val = this.x == 0 ? Math.PI / 2 : Math.atan(this.y / this.x);


        if (this.x < 0) {
            // if this is a special case(3rd and 2nd quadrants)
            val += Math.PI;
        } else if (this.x == 0 && this.y < 0) {
            // if on the negative side of the y axis
            val *= -1;
        }

        return Math.toDegrees(val);
    }

    /**
     * Get the length of this vector.
     * @return the length
     */
    public double length() {
        double xSquared = this.x * this.x;
        double ySquared = this.y * this.y;
        return Math.sqrt(xSquared + ySquared);
    }

    /**
     * Query whether a given vector is on the same line as this one.
     * @param v : the other vector
     * @return true if they are. false otherwise.
     */
    public boolean onSameLine(Vector2D v) {
        return sameDirection(v) || opposite().sameDirection(v);
    }
    /**
     * Query whether a given vector and this vector are pointing in the same direction.
     * @param v : the other vector
     * @return true if they are. false otherwise.
     */
    public boolean sameDirection(Vector2D v) {
        return this.angle() == v.angle();
    }

    /**
     * Multiply this vector coordinates by another vector coordinates.
     * @param v : the other vector
     * @return the multiplication result.
     */
    public Vector2D multiply(Vector2D v) {
        return new Vector2D(this.x * v.x, this.y * v.y);
    }

    /**
     * Get a perpendicular unit vector to this one.
     * @return a perpendicular vector.
     */
    public Vector2D perpendicular() {
        if (this.x == 0 && this.y == 0) {
            return null;
        } else {
            double perpendicularAngle = angle() + Math.PI / 2;
            return Point.fromDistanceAndAngle(1, perpendicularAngle).asVector();
        }
    }

    /**
     * Make a vector turn into a unit vector in the same direction.
     * @return the vector
     */
    public Vector2D unitize() {
        if (isZero()) {
            return null;
        } else {
            double angle = angle();
            return Point.fromDistanceAndAngle(1, angle).asVector();
        }
    }

    /**
     * Query whether this vector is the same as other one.
     * @param v : the other vector
     * @return : true if they are the same. false otherwise.
     */
    public boolean equals(Vector2D v) {
        return this.x == v.x && this.y == v.y;
    }

    /**
     * Set the x of the vector.
     * @param val : the new x coordinate
     */
    public void setX(double val) {
        this.x = val;
    }

    /**
     * Set the y of the vector.
     * @param val : the new y coordinate
     */
    public void setY(double val) {
        this.y = val;
    }

    /**
     * Get this vector as a Point.
     * @return a point instance
     */
    public Point asPoint() {
        return new Point(this.x, this.y);
    }

    /**
     * Get the opposite for this vector.
     * @return the opposite vector
     */
    public Vector2D opposite() {
        return new Vector2D(-this.x, -this.y);
    }

    /**
     * Query whether this vector is the zero vector.
     * @return true if it is. false otherwise.
     */
    public boolean isZero() {
        return this.x == 0 && this.y == 0;
    }
}
