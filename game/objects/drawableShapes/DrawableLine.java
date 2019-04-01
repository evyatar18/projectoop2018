package project.game.objects.drawableShapes;

import java.awt.Color;

import biuoop.DrawSurface;
import project.game.objects.Sprite;
import project.geometry.Line;
import project.misc.DrawUtils;

/**
 * {@link DrawableLine} is a drawable line.
 */
public class DrawableLine implements Sprite {

    private Line line;
    private Color color;

    /**
     * Construct a new drawable line.
     * @param line : the line to draw
     * @param color : the color of the line
     */
    public DrawableLine(Line line, Color color) {
        this.line = line;
        this.color = color;
    }

    @Override
    public void drawOn(DrawSurface surface) {
        DrawUtils utils = new DrawUtils(surface);
        utils.drawLine(this.line, this.color);
    }

    @Override
    public void timePassed(double dt) {
    }
}
