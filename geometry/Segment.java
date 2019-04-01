package project.geometry;
/**
 * Segment class.
 * Used to represent a segment on an axis.
 */
public class Segment {

    // store [a, b] values respectively.
    private final double a, b;

    /**
     * Constructs a segment from the shape of [a, b].
     * @param a - the lowest edge of the segment
     * @param b - the highest edge of the segment
     */
    public Segment(double a, double b) {
        if (a > b) {
            throw new Error("a > b cannot be in a segment!");
        }

        this.a = a;
        this.b = b;
    }

    /**
     * Construct a segment based on a start and a width.
     * @param start : the start position of the segment
     * @param width : the width of the segment
     * @return a segment
     */
    public static Segment fromStartAndWidth(double start, double width) {
        if (width < 0) {
            // if negative width, make special treatment
            return new Segment(start + width, start);
        } else {
            return new Segment(start, start + width);
        }
    }

    /**
     * Construct a new segment based on a middle point and a width.
     * @param mid : the midpoint
     * @param width : the width
     * @return a new segment
     */
    public static Segment fromMiddleAndWidth(double mid, double width) {
        if (width < 0) {
            throw new IllegalArgumentException("width shouldn't be null.");
        }

        return Segment.fromStartAndWidth(mid - (width / 2), width);
    }

    /**
     * Get the leftmost point.
     * @return the lowest/leftmost point
     */
    public double getLeft() {
        return this.a;
    }

    /**
     * Get the rightmost point.
     * @return the highest/rightmost point
     */
    public double getRight() {
        return this.b;
    }

    /**
     * Perform a union with other segment.
     * The other segment must intersect with the current.
     * @param other - the other segment
     * @return a new segment which is the union or null if there's no intersection
     */
    public Segment unite(Segment other) {
        // if don't intersect, can't be united
        if (!this.intersects(other)) {
            return null;
        }

        // get left and right borders
        double leftBorder = Math.min(this.getLeft(), other.getLeft());
        double rightBorder = Math.max(this.getRight(), other.getRight());

        return new Segment(leftBorder, rightBorder);
    }

    /**
     * Query whether this segment intersects with another segment.
     * @param other : the other segment
     * @return true if intersects. false otherwise.
     */
    public boolean intersects(Segment other) {
        return intersection(other) != null;
    }

    /**
     * Get the intersection of this segment with another one.
     * @param other : the other segment
     * @return a new segment of intersection, or null if there's no intersection.
     */
    public Segment intersection(Segment other) {
        double leftBorder = Math.max(this.getLeft(), other.getLeft());
        double rightBorder = Math.min(this.getRight(), other.getRight());

        // no intersection
        if (rightBorder < leftBorder) {
            return null;
        }

        return new Segment(leftBorder, rightBorder);
    }

    /**
     * Get the length of the segment.
     * @return the length of the segment.
     */
    public double length() {
        return this.b - this.a;
    }

    /**
     * Query whether a value is on the segment.
     * @param value - the value to query
     * @return true if the value is on the segment. false otherwise.
     */
    public boolean contains(double value) {
        return this.a <= value && value <= this.b;
    }

    /**
     * Query whether this segment contains a point and its neighborhood.
     * @param value : the value of the point
     * @param distance : the neighborhood of it
     * @return true if the whole neighborhood is contained. otherwise false.
     */
    public boolean containsNeighborhood(double value, double distance) {
        return this.a <= value - distance && value + distance <= this.b;
    }

    /**
     * Get a value on the segment.
     * @return a value on the segment.
     */
    public double getValue() {
        return this.a;
    }

    /**
     * Get a sub-segment which is spaced equally from the edges of this one.
     * @param spacing : the space
     * @return the sub-segment
     */
    public Segment subSegmentSpacedEquallyFromEdges(double spacing) {
        // do input check
        if (2 * spacing > length()) {
            String format = "the spacing %s cannot be contained inside a segment with width of %s.";
            throw new IllegalArgumentException(String.format(format, spacing, length()));
        }

        return new Segment(this.a + spacing, this.b - spacing);
    }
}