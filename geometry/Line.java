package project.geometry;
import java.util.List;

import project.misc.Utils;

/**
 * Line class.
 * Used to represent a 2D segment.
 */
public class Line {

    private Point start, end;

    /**
     * Construct a new line with given start and end points.
     * @param start : the start point
     * @param end : the end point
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;

        // determinePointPositions();
    }

    /**
     * Construct a new line with a given start and direction.
     * @param start : the start point
     * @param direction : the direction
     */
    public Line(Point start, Vector2D direction) {
        this(start, start.asVector().add(direction).asPoint());
    }

    /**
     * Construct a new line with given start and end points as coordinates.
     * @param x1 : the x for the start point
     * @param y1 : the y for the start point
     * @param x2 : the x for the end point
     * @param y2 : the y for the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    // stores the length of the line.
    private double length = Double.NaN;

    /**
     * Get the length of the line.
     * @return the length of the line.
     */
    public double length() {
        // if the length hasn't been defined.
        if (Double.isNaN(this.length)) {
            this.length = start.distance(end);
        }

        return this.length;
    }

    // stores the middle point of the line.
    private Point middle;

    /**
     * Get the middle point of the segment.
     * @return the middle point
     */
    public Point middle() {
        // if the 'middle' has not been defined.
        if (this.middle == null) {
            // calculate middle x, middle y respectively.
            double midX = (this.start.getX() + this.end.getX()) / 2;
            double midY = (this.start.getY() + this.end.getY()) / 2;

            this.middle = new Point(midX, midY);
        }

        return this.middle;
    }

    /**
     * Get the start point of the segment.
     * @return the start point
     */
    public Point start() {
        return this.start;
    }

    /**
     * Get the end point of the line.
     * @return the end point
     */
    public Point end() {
        return this.end;
    }

    /**
     * Query whether this line is vertical.
     * @return true if the line is vertical. false otherwise.
     */
    public boolean isVertical() {
        return this.start.getX() == this.end.getX();
    }

    /**
     * Query whether this line is horizontal.
     * @return true if the line is horizontal. otherwise false.
     */
    public boolean isHorizontial() {
        return this.start.getY() == this.end.getY();
    }

    /**
     * Get a y for a given x on the line.
     * @param x : the given x.
     * @return the y for the given x.
     */
    public double getY(double x) {
        // if given x is not on the line
        if (!this.getXSegment().contains(x)) {
            throw new IllegalArgumentException("x:" + x + " is not defined for this line.");
        }

        // if this line is vertical
        if (this.isVertical()) {
            throw new Error("can't get y value for vertical line!");
        }

        // get parameters for line equation
        double m = getSlope();
        double x0 = this.start.getX();
        double y0 = this.start.getY();

        // line equation: y=m(x-x0) + y0
        return m * (x - x0) + y0;
    }

    // stores the slope of this line.
    private double slope = Double.NaN;

    /**
     * Get the slope of the line.
     * @return the slope of the line.
     */
    public double getSlope() {
        // if the line is vertical
        if (this.isVertical()) {
            throw new Error("Cannot get slope of vertical line!");
        }

        // if the slope is not yet defined.
        if (Double.isNaN(this.slope)) {
            this.slope = Utils.getSlope(this.start, this.end);
        }

        return this.slope;
    }

    // stores the x segment for this line.
    private Segment xSegment;

    /**
     * Get a Segment representing the x values of this line.
     * @return the x segment.
     */
    private Segment getXSegment() {
        // if the x segment hasn't been defined.
        if (this.xSegment == null) {
            double minX = Math.min(this.start.getX(), this.end.getX());
            double maxX = Math.max(this.start.getX(), this.end.getX());

            this.xSegment = new Segment(minX, maxX);
        }

        return this.xSegment;
    }

    // stores the y segment for this line.
    private Segment ySegment;

    /**
     * Get a Segment representing the y values of this line.
     * @return the y segment.
     */
    private Segment getYSegment() {
        // if the y segment hasn't been defined.
        if (this.ySegment == null) {
            double minY = Math.min(this.start.getY(), this.end.getY());
            double maxY = Math.max(this.start.getY(), this.end.getY());

            this.ySegment = new Segment(minY, maxY);
        }

        return this.ySegment;
    }

    /**
     * Get an intersection point of this line with another line.
     * @param other : the other line
     * @return a {@link Point} there's an intersection. null if there's no intersection.
     */
    public Point intersectionWith(Line other) {
        if (other == null) {
            throw new IllegalArgumentException("other line is null.");
        }

        // if there's no intersection between the x and y segments
        if (!this.getXSegment().intersects(other.getXSegment())
                || !this.getYSegment().intersects(other.getYSegment())) {
            return null;
        }

        /* from this point on, we know there are shared x and y values */

        // if one of the lines is vertical
        if (this.isVertical() || other.isVertical()) {
            // if both lines are vertical
            if (this.isVertical() && other.isVertical()) {
                double x = this.start.getX();
                double y = this.getYSegment().intersection(other.getYSegment()).getValue();

                return new Point(x, y);
                // if one line is not vertical
            } else {
                // the non-vertical line
                Line notVertical = this.isVertical() ? other : this;

                // construct a new point
                double x = this.getXSegment().intersection(other.getXSegment()).getValue();
                double y = notVertical.getY(x);

                // make sure the y we found is not above or below the vertical line
                if (this.getYSegment().contains(y) && other.getYSegment().contains(y)) {
                    return new Point(x, y);
                }
            }

            // if both lines are horizontal
        } else if (this.isHorizontial() && other.isHorizontial()) {
            // choose an x on the shared segment between the lines
            double x = this.getXSegment().intersection(other.getXSegment()).getValue();
            double y = this.getYSegment().getValue();
            return new Point(x, y);

            // if both lines have a defined slope.
        } else {
            /*
             * line 1: y-y1=m1*(x-x1)
             * line 2: y-y2=m2(x-x2)
             * ==> y2-y1=(m1-m2)x + m2x2 - m1x1
             * ==> (y2 - y1 - m2x2 + m1x1)/(m1-m2) = x
             */

            // parameters for *this* line
            double x1 = this.start.getX();
            double y1 = this.start.getY();
            double m1 = this.getSlope();

            // parameters for the other line
            double x2 = other.start.getX();
            double y2 = other.start.getY();
            double m2 = other.getSlope();

            // if both lines have the same slope
            if (m1 == m2) {
                // get the shared values
                double x = this.getXSegment().intersection(other.getXSegment()).getValue();
                double y = this.getYSegment().intersection(other.getYSegment()).getValue();

                return new Point(x, y);

                // if the lines have different slopes.
            } else {
                // calculate the x based on the formula above.
                double x = (y2 - y1 - m2 * x2 + m1 * x1) / (m1 - m2);

                // if both lines contain the calculated x value
                if (this.getXSegment().contains(x) && other.getXSegment().contains(x)) {
                    double y = this.getY(x);
                    return new Point(x, y);
                }
            }
        }

        // if there's no intersection
        return null;
    }

    /**
     * Query whether this line intersects with another line.
     * @param other : the other line
     * @return true if intersects. otherwise false.
     */
    public boolean isIntersecting(Line other) {
        // if there is an intersection point
        return intersectionWith(other) != null;
    }

    /**
     * If this line intersects a given rectangle, get the closest intersection point to the start of this line.
     * @param rect : the given rectangle
     * @return the closest intersection point to the start. or null if there's no intersection.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersections = rect.intersectionPoints(this);

        // initialize variables for holding the closest point and minimum distance
        double minimumDistance = Double.POSITIVE_INFINITY;
        Point closest = null;

        /* go over all the intersections */
        for (Point p : intersections) {
            // get the distance between p and this start
            double currentDistance = p.distance(this.start);

            // if the current distance is less than the previous minimum, switch closest point
            if (currentDistance < minimumDistance) {
                minimumDistance = currentDistance;
                closest = p;
            }
        }

        return closest;
    }

    /**
     * If this line intersects a given rectangle, get the closest intersection point to the start of this line.
     * @param rect : the given rectangle
     * @return the closest {@link LineIntersection} to the start. or null if there's no intersection.
     */
    public LineIntersection closestIntersectionToStartLine(Rectangle rect) {
        // get intersections
        List<LineIntersection> intersections = rect.intersections(this);

        if (intersections.size() == 0) {
            return null;
        }

        // get pseudo-minimum
        LineIntersection minimum = intersections.get(0);

        // go over all the intersections and choose the one closest to the beginning of this line
        for (LineIntersection intersection : intersections) {
            if (intersection.compareDistanceToStartOfIntersecting(minimum) < 0) {
                minimum = intersection;
            }
        }

        return minimum;
    }

    /**
     * Get the direction of this line.
     * @return the direction vector
     */
    public Vector2D direction() {
        return new Vector2D(this.start, this.end);
    }

    /**
     * Get the same line, but from the end to the start.
     * @return a line instance
     */
    public Line reversed() {
        return new Line(this.end, this.start);
    }
    /**
     * Query whether this line is equal to another line.
     * @param other the other line
     * @return true if equals. false otherwise.
     */
    public boolean equals(Line other) {
        if (other == null) {
            return false;
        }

        return this.start.equals(other.start) && this.end.equals(other.end);
    }

    /**
     * Query whether this line containing a given point.
     * @param p : the given point
     * @return true if contains. false otherwise.
     */
    public boolean contains(Point p) {
        // if there's no way the point is in the range of this line
        if (!this.getXSegment().contains(p.getX()) || !this.getYSegment().contains(p.getY())) {
            return false;
        }

        // if the line is vertical, it surely contains the point
        if (this.isVertical()) {
            return true;
            // check if the slopes are equal. if they are, the point must be on the line
        } else {
            return Utils.getSlope(this.start(), p) == this.getSlope();
        }
    }

    /**
     * Get an array containing equally spaced parallel lines to this one(including this one).
     * @param spacing : the spacing between the lines
     * @param pairs : the amount of pairs
     * @return an array(sized 0 if this line doesn't have a direction)
     */
    public Line[] equallySpacedParallelLines(double spacing, int pairs) {
        // initialize array and middle of array
        Line[] lines = new Line[2 * pairs + 1];
        int mid = pairs;

        // the direction of all the lines(should be the same since they are parallel)
        Vector2D direction = direction();

        // if there's no direction to this line, just abort
        if (direction == null || direction.isZero()) {
            return null;
        }

        // the vector which will be added in whole multiplications to the start point
        Vector2D additionVector = direction.perpendicular().multiply(spacing);

        for (int i = 0; i < lines.length; i++) {
            // get how much to move
            int multiplicationFactor = i - mid;
            Vector2D movement = additionVector.multiply(multiplicationFactor);

            // move to that start point
            Point startOfLine = start().asVector().add(movement).asPoint();

            // construct a line based on it
            Line line = new Line(startOfLine, direction);

            // put it
            lines[i] = line;
        }

        return lines;
    }

    /**
     * Get a fraction of this line starting at the same start point.
     * @param fraction : the fraction of the line
     * @return a line with length multiplied by the fraction as well as direction
     */
    public Line fractionOfLine(double fraction) {
        Vector2D newDistance = direction().multiply(fraction);
        return new Line(this.start, newDistance);
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", this.start(), this.end());
    }
}