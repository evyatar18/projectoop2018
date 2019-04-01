package project.game.levels;

import java.awt.Color;
import java.util.function.Function;

import biuoop.DrawSurface;
import project.game.objects.BaseComplexSprite;
import project.game.objects.drawableShapes.DrawableCircle;
import project.geometry.EqualSegmentDivision;
import project.geometry.Point;
import project.geometry.Segment;
import project.misc.ConstantFunction;

/**
 * {@link SurroundingCircles} are circles that surround a given point, equally spaced.
 */
public class SurroundingCircles extends BaseComplexSprite {

    /**
     * Constructs a function that maps every single index to the same color.
     * @param color : the color to map to
     * @return the function
     */
    private static Function<Integer, Color> sameColor(Color color) {
        return new ConstantFunction<Integer, Color>(color);
    }

    /**
     * Create shallow surrounding circles that surround a given point(with the same color).
     * @param center : the center of the circles
     * @param radiusRange : the range of radiuses that the circles are made from
     * @param amount : the amount of the circles
     * @param color : the color of all the circles
     */
    public SurroundingCircles(Point center, Segment radiusRange, int amount, Color color) {
        this(center, radiusRange, amount, false, sameColor(color));
    }

    /**
     * Create surrounding circles according to given parameters.
     * @param center : the center of all the circles
     * @param radiusRange : the range of radiuses that the circles are made from
     * @param amount : the amount of circles
     * @param fill : whether to fill the circles or not
     * @param color : a function that maps a color for a given index from beginning
     */
    public SurroundingCircles(Point center, Segment radiusRange, int amount,
            boolean fill, Function<Integer, Color> color) {
                // create a divisor for the radiuses
        EqualSegmentDivision radiuses = new EqualSegmentDivision(radiusRange, amount);

        // add 'amount' of circles(starting from the last)
        for (int i = amount - 1; i >= 0; i--) {
            // get the radius according to the division
            double radius = radiuses.rightmostPosition(i);

            // create a circle based on that radius
            DrawableCircle circle = new DrawableCircle(center, radius, fill, color.apply(i));

            // add the circle sprite
            addSprite(circle);
        }
    }

    @Override
    protected void drawThis(DrawSurface surface) {
    }

    @Override
    protected void timePassThis() {
    }

}
