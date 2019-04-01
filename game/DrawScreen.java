package project.game;

import project.geometry.Point;
import project.geometry.Segment;

/**
 * {@link DrawScreen} defines the boundaries of a part on the screen that can be drawn on.
 */
public interface DrawScreen {

    /**
     * The height of this screen.
     * @return the height
     */
    int height();

    /**
     * The width of this screen.
     * @return the width
     */
    int width();

    /**
     * Get the position of a point positioned in the First Quadrant sized 1 x 1
     * on this DrawScreen.
     * @param p : the point
     * @return a point on the draw plane
     */
    Point transform(Point p);

    /**
     * Get an x position on the width axis given a proportional position.
     * @param fraction : the proportion relative to the whole axis
     * @return an x position on the axis
     */
    double proportionalX(double fraction);

    /**
     * Get a y position on the height axis given a proportional position.
     * @param fraction : the proportion relative to the whole axis
     * @return a y position on the axis
     */
    double proportionalY(double fraction);

    /**
     * Get the leftmost x position.
     * @return the leftmost x position
     */
    double leftmostX();

    /**
     * Get the rightmost x position.
     * @return the rightmost y position
     */
    double rightmostX();

    /**
     * Get the top y position.
     * @return the top y position
     */
    double topY();

    /**
     * Get the bottom y position.
     * @return the bottom y position
     */
    double bottomY();

    /**
     * Get the width segment for this screen.
     * @return the width segment
     */
    Segment widthSegment();

    /**
     * Get the height segment for this screen.
     * @return the height segment
     */
    Segment heightSegment();

    /**
     * Query whether a given point is inside this screen.
     * @param point : the point to check
     * @return true if it is. false otherwise.
     */
    boolean inside(Point point);
}
