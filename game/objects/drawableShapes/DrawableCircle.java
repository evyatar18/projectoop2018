package project.game.objects.drawableShapes;

import java.awt.Color;

import biuoop.DrawSurface;
import project.game.objects.Sprite;
import project.geometry.Point;
import project.misc.DrawUtils;

/**
 * {@link DrawableCircle} is a class representing a drawable circle.
 */
public class DrawableCircle implements Sprite {

    private Point center;
    private double radius;

    private boolean isFilled;
    private Color color;

    /**
     * Create a drawable circle.
     * @param center : the center of the circle
     * @param radius : the radius of the circle
     * @param isFilled : specify whether the circle is filled with color or not
     * @param color : specify the color of the circle
     */
    public DrawableCircle(Point center, double radius, boolean isFilled, Color color) {
        this.center = center;
        this.radius = radius;
        this.isFilled = isFilled;
        this.color = color;
    }

    /**
     * Get the radius of the circle.
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Get the center of the circle.
     * @return the center
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Query whether this circle is filled.
     * @return true if it is. false otherwise.
     */
    public boolean isFilled() {
        return isFilled;
    }

    /**
     * Set whether this circle is filled.
     * @param fill : whether to fill or not
     */
    public void setFill(boolean fill) {
        isFilled = fill;
    }

    /**
     * Get the color of this circle.
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Set the color of this circle.
     * @param c : the color
     */
    public void setColor(Color c) {
        color = c;
    }

    @Override
    public void drawOn(DrawSurface surface) {
        DrawUtils utils = new DrawUtils(surface);

        if (isFilled) {
            utils.fillCircle(this.center, (int) this.radius, this.color);
        } else {
            utils.drawCircle(this.center, (int) this.radius, this.color);
        }
    }

    @Override
    public void timePassed(double dt) {
    }

    @Override
    public String toString() {
        String format = "{center: %s, radius: %s, color: %s}";
        return String.format(format, center, radius, color);
    }
}
