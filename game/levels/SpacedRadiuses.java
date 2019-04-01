package project.game.levels;

import java.awt.Color;
import java.util.function.Function;

import biuoop.DrawSurface;
import project.game.objects.BaseComplexSprite;
import project.game.objects.drawableShapes.DrawableLine;
import project.geometry.EqualSegmentDivision;
import project.geometry.Line;
import project.geometry.Point;
import project.geometry.Segment;
import project.misc.ConstantFunction;

/**
 * {@link SpacedRadiuses} is a sprite that creates equally spaced radiuses from a given point.
 */
public class SpacedRadiuses extends BaseComplexSprite {

    /**
     * Construct a new spaced radiuses instance.
     * @param center : the center of all radiuses
     * @param radius : the length of all radiuses
     * @param amount : the amount of radiuses
     * @param color : the color of all radiuses
     */
    public SpacedRadiuses(Point center, double radius, int amount, Color color) {
        this(center, radius, new Segment(0, 360), amount, color);
    }

    /**
     * Construct a new spaced radiuses instance.
     * @param center : the center of the radiuses
     * @param radius : the length for all radiuses
     * @param angleRange : the range of angles which the radiuses will be spread around
     * @param amount : the amount of radiuses
     * @param color : the color of the radiuses
     */
    public SpacedRadiuses(Point center, double radius, Segment angleRange, int amount, Color color) {
        this(center, new ConstantFunction<>(radius), angleRange, amount, color);
    }

    /**
     * Construct a new spaced radiuses instance.
     * @param center : the center of all the radiuses
     * @param angleToRadius : a function that maps angles to radius lengths
     * @param angleRange : the range of the angles to pick from
     * @param amount : the amount of radiuses
     * @param color : the color of the lines
     */
    public SpacedRadiuses(Point center, Function<Double, Double> angleToRadius,
            Segment angleRange, int amount, Color color) {

        // divide the angles equally
        EqualSegmentDivision angleDivision = new EqualSegmentDivision(angleRange, amount);

        // add 'amount' lines
        for (int i = 0; i < amount; i++) {
            // get angle and radius
            double angle = angleDivision.leftmostPosition(i);
            double radius = angleToRadius.apply(angle);

            // get relative endpoint
            Point relativeEnd = Point.fromDistanceAndAngle(radius, angle);

            // get calculated endpoint
            Point end = relativeEnd.asVector().add(center.asVector()).asPoint();

            // construct a new line
            Line line = new Line(center, end);

            // make this line drawn
            addSprite(new DrawableLine(line, color));
        }
    }

    @Override
    protected void drawThis(DrawSurface surface) {
    }

    @Override
    protected void timePassThis() {
    }

}
