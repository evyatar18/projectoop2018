package project.misc;
import biuoop.DrawSurface;
import project.geometry.Point;
import project.geometry.Segment;

/**
 * A class representing a frame.
 */
public class Frame {

    private Segment xSegment;
    private Segment ySegment;

    /**
     * Construct a new frame with given borders.
     * @param top : the top border
     * @param bottom : the bottom border
     * @param left : the left border
     * @param right : the right border
     */
    public Frame(int top, int bottom, int left, int right) {
        this.xSegment = new Segment(left, right);
        this.ySegment = new Segment(top, bottom);
    }

    /**
     * Construct a frame from a DrawSurface.
     * @param surface : the surface
     */
    public Frame(DrawSurface surface) {
        this(0, surface.getHeight(), 0, surface.getWidth());
    }

    /**
     * Query whether a point is inside the frame.
     * @param x : the x of the point
     * @param y : the y of the point
     * @return true if it is inside. otherwise false.
     */
    public boolean isInside(int x, int y) {
        // checks whether the point is inside both borders
        return isInsideX(x) && isInsideY(y);
    }

    /**
     * Query whether a point is inside the frame.
     * @param pt : the point
     * @return true if it is inside. otherwise false.
     */
    public boolean isInside(Point pt) {
        return isInside(pt.xFloored(), pt.yFloored());
    }

    /**
     * Query whether a given x value is inside the borders.
     * @param x : the x value
     * @return true if it is inside. otherwise false.
     */
    public boolean isInsideX(int x) {
        return this.xSegment.contains(x);
    }

    /**
     * Query whether a given y value is inside the borders.
     * @param y : the y value
     * @return true if it is inside. otherwise false.
     */
    public boolean isInsideY(int y) {
        return this.ySegment.contains(y);
    }

    /**
     * Query whether a given x value and all the points around a given distance from it are within the borders.
     * @param x : the x value
     * @param radius : the distance from x
     * @return true if everything is within the borders. otherwise false.
     */
    public boolean isInsideX(int x, int radius) {
        return this.xSegment.containsNeighborhood(x, radius);
    }

    /**
     * Query whether a given y value and all the points around a given distance from it are within the borders.
     * @param y : the y value
     * @param radius : the distance from y
     * @return true if everything is within the borders. otherwise false.
     */
    public boolean isInsideY(int y, int radius) {
        return this.ySegment.containsNeighborhood(y, radius);
    }

    /**
     * Get the left border of the frame.
     * @return the x value.
     */
    public int left() {
        return (int) this.xSegment.getLeft();
    }

    /**
     * Get the right border of the frame.
     * @return the x value
     */
    public int right() {
        return (int) this.xSegment.getRight();
    }

    /**
     * Get the top border of the frame.
     * @return the y value.
     */
    public int top() {
        return (int) this.ySegment.getLeft();
    }

    /**
     * Get the bottom border of the frame.
     * @return the y value.
     */
    public int bottom() {
        return (int) this.ySegment.getRight();
    }

    /**
     * Get the width of the frame.
     * @return the width.
     */
    public int width() {
        return (int) this.xSegment.length();
    }

    /**
     * Get the height of the frame.
     * @return the height.
     */
    public int height() {
        return (int) this.ySegment.length();
    }
}