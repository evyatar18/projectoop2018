package project.geometry;

/**
 * {@link LineIntersection} defines an intersection between lines.
 */
public class LineIntersection {

    private final Line intersecting;
    private final Line intersected;
    private final Point intersectionPoint;

    /**
     * Construct a new {@link LineIntersection}.
     * @param intersecting : the line which 'intersectionWith' was called from
     * @param intersected : the line which 'intersectionWith' was called on
     * @param intersectionPoint : the point of intersection
     */
    public LineIntersection(Line intersecting, Line intersected, Point intersectionPoint) {
        this.intersecting = intersecting;
        this.intersected = intersected;
        this.intersectionPoint = intersectionPoint;
    }

    /**
     * Get the intersecting line.
     * @return the intersecting line
     */
    public Line intersecting() {
        return this.intersecting;
    }

    /**
     * Get the intersected line.
     * @return the intersected line
     */
    public Line intersected() {
        return this.intersected;
    }

    /**
     * Get the intersection point.
     * @return the intersection point
     */
    public Point intersectionPoint() {
        return this.intersectionPoint;
    }

    /**
     * Get the distance from the intersection point to the beginning of intersecting line.
     * @return the distance
     */
    public double distanceToStartOfIntersectingLine() {
        return intersectionPoint().distance(intersecting().start());
    }

    /**
     * Compare the distances of both intersections from their start of line.
     * @param other : the other line to compare with
     * @return a number greater than 0 if this line is farther away from the beginning
     *         a number equal to 0 if the distances are equal
     *         a number lower than 0 if this line is closer to the beginning
     */
    public double compareDistanceToStartOfIntersecting(LineIntersection other) {
        double currentDistance = distanceToStartOfIntersectingLine();
        double otherDistance = other.distanceToStartOfIntersectingLine();

        return currentDistance - otherDistance;
    }
}
