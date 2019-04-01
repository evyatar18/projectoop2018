package project.geometry;

/**
 * {@link EqualSegmentDivision} is a utility class that divides to parts equally.
 */
public class EqualSegmentDivision {

    private Segment seg;
    private int parts;

    /**
     * Construct an equal segment division for a segment with a given width and parts.
     * @param width : the width of the segment
     * @param parts : the amount of parts this segment has
     */
    public EqualSegmentDivision(double width, int parts) {
        this(new Segment(0, width), parts);

        if (width <= 0) {
            throw new IllegalArgumentException("the width of a divided segment must be larger than 0!");
        }
    }

    /**
     * Construct an equal segment division for a given segment and amount of parts.
     * @param seg : the segment
     * @param parts : the amount of parts
     */
    public EqualSegmentDivision(Segment seg, int parts) {
        this.seg = seg;
        this.parts = parts;
    }

    /**
     * Get the width of any sub segment(because they are equal).
     * @return the width
     */
    public double subSegmentWidth() {
        return this.seg.length() / this.parts;
    }

    /**
     * Get the index on the segment given the position on the segment.
     * @param pos : the position
     * @return the index
     */
    public int getIndexForPosition(double pos) {
        // get relative position to the beginning of segment
        double relativePosition = pos - this.seg.getLeft();

        // get the width of the segment
        double segLength = this.seg.length();

        // get the proportional position to the segment
        double proportionalPosition = relativePosition / segLength;

        // get the index starting from 0 on the segment
        int index = (int) (proportionalPosition * this.parts);

        return index;
    }

    /**
     * Get the leftist position for a given index.
     * @param index : the index on this equal division
     * @return the position on the segment
     */
    public double leftmostPosition(int index) {
        // get the width of each sub-segment
        double segmentWidth = subSegmentWidth();

        // get the relative position to the beginning of the segment
        double relativePosition = segmentWidth * index;

        // get the actual position on the segment
        double position = this.seg.getLeft() + relativePosition;

        return position;
    }

    /**
     * Get the rightmost position for a given index.
     * @param index : the index
     * @return the rightmost position
     */
    public double rightmostPosition(int index) {
        // get a sub-segment's length
        double subSegmentLength = subSegmentWidth();

        // add that length to the leftmost position to get the rightmost
        return leftmostPosition(index) + subSegmentLength;
    }

    /**
     * Get the middle position for a given index.
     * @param index : the index
     * @return the middle position
     */
    public double middlePosition(int index) {
        // get a sub-segment's length
        double subSegmentLength = subSegmentWidth();

        return leftmostPosition(index) + (subSegmentLength / 2);
    }

    /**
     * Get the segment that's being divided.
     * @return the segment behind this division
     */
    public Segment getSegment() {
        return this.seg;
    }

    /**
     * Get an equally spaced segment with a given midpoint, spacing and amount of sub-segments.
     * @param mid : the midpoint
     * @param spacing : the spacing between each sub-segment
     * @param amount : the amount of sub-segments
     * @return a new {@link EqualSegmentDivision}
     */
    public static EqualSegmentDivision fromCenterSpacingAndAmount(double mid, double spacing, int amount) {
        // calculate width
        double width = spacing * amount;

        return new EqualSegmentDivision(Segment.fromMiddleAndWidth(mid, width), amount);
    }

    /**
     * Construct a new {@link EqualSegmentDivision}.
     * @param leftmost : the leftmost point
     * @param spacing : the spacing between points
     * @param amount : the amount of points
     * @return {@link EqualSegmentDivision} instance
     */
    public static EqualSegmentDivision fromLeftmostSpacingAndAmount(double leftmost, double spacing, int amount) {
        // calculate width
        double width = spacing * amount;

        return new EqualSegmentDivision(Segment.fromStartAndWidth(leftmost, width), amount);
    }
}
