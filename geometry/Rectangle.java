package project.geometry;
import java.util.ArrayList;
import java.util.List;

/**
 * Rectangle class.
 * Used to represent a rectangle in a 2D plane.
 */
public class Rectangle {

    private Point upperLeft;
    private int width;
    private int height;

    private Point[] points;
    private Line[] lines;

    /**
     * Construct a new Rectangle instance.
     * @param upperLeft : the upper left corner of the rectangle
     * @param width : the width of the rectangle
     * @param height : the height of the rectangle
     */
    public Rectangle(Point upperLeft, int width, int height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;

        // initialize the bounding points for this rectangle.
        this.initPoints();
        // initialize the bounding lines for this rectangle.
        this.initLines();
    }

    /**
     * Construct a new square.
     * @param center : the center point of the square
     * @param sideLength : the side length
     */
    public Rectangle(Point center, int sideLength) {
        this(new Point(center.getX() - (sideLength / 2), center.getY() - (sideLength / 2)), sideLength, sideLength);
    }

    /**
     * Define a rectangle using its top left position and bottom right position.
     * @param topLeft : the top left position
     * @param bottomRight : the bottom right position
     */
    public Rectangle(Point topLeft, Point bottomRight) {
        // top left, width and height respectively
        this(topLeft, bottomRight.xFloored() - topLeft.xFloored(), bottomRight.yFloored() - topLeft.yFloored());
    }

    public static final int BOUNDING_POINTS = 4;

    public static final int UPPER_LEFT = 0;
    public static final int UPPER_RIGHT = 1;
    public static final int BOTTOM_RIGHT = 2;
    public static final int BOTTOM_LEFT = 3;

    public static final int TOP = 0;
    public static final int RIGHT = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;

    /**
     * Initializes the bounding points array.
     */
    private void initPoints() {
        // 4 bounding points
        this.points = new Point[BOUNDING_POINTS];

        /* initialize and insert bounding points */
        this.points[UPPER_LEFT] = this.upperLeft;
        this.points[UPPER_RIGHT] = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        this.points[BOTTOM_RIGHT] = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height);
        this.points[BOTTOM_LEFT] = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
    }

    /**
     * Get the bounding points for this rectangle.
     * @return the bounding points as {@link Point}[]
     */
    public Point[] getPoints() {
        return this.points.clone();
    }

    public static final int BOUNDING_LINES = 4;

    /**
     * Initializes the bounding lines array.
     */
    private void initLines() {
        this.lines = new Line[BOUNDING_LINES];

        /* initialize and insert bounding lines*/
        for (int i = 0; i < BOUNDING_LINES; i++) {
            // create start and end points for that bounding line
            Point start = this.points[i];
            Point end = this.points[(i + 1) % BOUNDING_LINES];

            // insert the created line
            this.lines[i] = new Line(start, end);
        }
    }

    /**
     * Get the bounding lines for this rectangle.
     * @return the bounding lines as {@link Line}[]
     */
    public Line[] getLines() {
        return this.lines.clone();
    }

    /**
     * Get the width of this rectangle.
     * @return the width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Get the height of this rectangle.
     * @return the height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Get the upper left point for this rectangle.
     * @return the upper left point
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Get a list of the intersection points of a given line with this rectangle.
     * @param line : a given line
     * @return the list of intersection points
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> pts = new ArrayList<>();

        /* go over all the bounding lines of this rectangle */
        for (int i = 0; i < this.lines.length; i++) {
            /* a line can't have more than 2 intersection points with a rectangle.
             *  (unless intersects at both vertices)
             */
            if (pts.size() == 2) {
                break;
            }

            Line boundingLine = this.lines[i];

            // if there is an intersection
            if (boundingLine.isIntersecting(line)) {
                // get intersection point
                Point intersectionPoint = boundingLine.intersectionWith(line);

                // add the point to the list only if it wasn't added before
                if (!pts.contains(intersectionPoint)) {
                    pts.add(intersectionPoint);
                }
            }
        }

        return pts;
    }

    /**
     * Get information about the intersections of this rectangle with a given line.
     * @param line : the line to intersect with
     * @return a list of {@link LineIntersection}s where the given line is always
     *  the intersecting line and the rectangles' lines are always the intersected ones
     */
    public List<LineIntersection> intersections(Line line) {
        List<LineIntersection> intersections = new ArrayList<>();

        /* go over each bounding line of this rectangle */
        for (Line boundingLine : this.lines) {
            // if there is an intersection between the lines
            if (boundingLine.isIntersecting(line)) {
                // get the intersection point
                Point intersectionPoint = line.intersectionWith(boundingLine);

                // add the intersection
                intersections.add(new LineIntersection(line, boundingLine, intersectionPoint));
            }
        }

        return intersections;
    }

    /**
     * If a point is on the rectangle, get the line containing it.
     * @param p : the point
     * @return the line containing the point or null if no line contains it.
     */
    public Line getLineContaining(Point p) {
        for (Line line : this.lines) {
            if (line.contains(p)) {
                return line;
            }
        }

        return null;
    }

    /**
     * Get the distance between a point to a side on the rectangle.
     * @param p : the point
     * @param lineIndex : the side index in the array
     * @return the distance or NaN if the index is wrong
     */
    private double distance(Point p, int lineIndex) {
        switch(lineIndex) {
        case TOP:
        case BOTTOM:
            return Math.abs(this.lines[lineIndex].start().getY() - p.getY());

        case LEFT:
        case RIGHT:
            return Math.abs(this.lines[lineIndex].start().getX() - p.getX());

        default:
            return Double.NaN;

        }
    }

    /**
     * Get the closest line on the rectangle to a given point.
     * @param point : the point
     * @return the closest line on the rectangle
     */
    public Line getClosestLine(Point point) {
        double minimum = Double.POSITIVE_INFINITY;
        Line l = null;

        for (int i = 0; i < this.lines.length; i++) {
            double dis = distance(point, i);

            if (dis < minimum) {
                l = this.lines[i];
                minimum = dis;
            }
        }

        return l;
    }

    /**
     * Query whether a point is a vertex in the rectangle.
     * @param p : the point
     * @return true if is a vertex. false otherwise
     */
    public boolean isVertex(Point p) {
        final double epsilon = 3D;

        for (Point vertex : this.points) {
            if (vertex.distance(p) < epsilon) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Point p : this.points) {
            if (builder.length() > 0) {
                builder.append(", ");
            }

            builder.append(p);
        }

        return builder.toString();
    }


    /**
     * Query whether a point is inside this rectangle.
     * @param point : the point
     * @return true if it is. false otherwise
     */
    public boolean contains(Point point) {
        // get distances
        double dx = point.getX() - this.upperLeft.getX();
        double dy = point.getY() - this.upperLeft.getY();

        return (dx > 0 && dx < this.width) && (dy > 0 && dy < this.height);
    }

    /**
     * Get the center of this rectangle.
     * @return a point
     */
    public Point center() {
        Vector2D mid = new Vector2D(this.getWidth(), this.getHeight()).multiply(0.5);
        return this.getUpperLeft().asVector().add(mid).asPoint();
    }
}
